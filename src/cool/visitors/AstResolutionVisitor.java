package cool.visitors;

import cool.ast.*;
import cool.parser.CoolParser;
import cool.structures.*;

import java.util.Objects;
import java.util.stream.Collectors;

public class AstResolutionVisitor implements ASTVisitor<TypeSymbol> {
    Scope scope;

    @Override
    public TypeSymbol visit(Program program) {
        program.programClasses.forEach(this::visit);
        return null;
    }

    @Override
    public TypeSymbol visit(ProgramClass programClass) {
        if (programClass.typeSymbol == null) {
            return null;
        }

        scope = programClass.typeSymbol;
        programClass.features.forEach(feature -> feature.accept(this));

        return null;
    }

    @Override
    public TypeSymbol visit(Formal formal) {
        return null;
    }

    @Override
    public TypeSymbol visit(MethodDef methodDef) {
        var methodSymbol = methodDef.methodSymbol;
        if (methodSymbol == null) {
            return null;
        }

        var methodName = methodDef.name.getText();
        var classScope = (TypeSymbol)scope;
        var retRawType = methodSymbol.getReturnType();

        var overriddenMethod = ((TypeSymbol)classScope.getParent()).lookupMethod(methodName);
        if (overriddenMethod != null) {
            var overriddenType = overriddenMethod.getReturnType();
            if (retRawType != overriddenType) {
                SymbolTable.error(
                        methodDef.ctx,
                        methodDef.returnType,
                        "Class " + classScope.getName() + " overrides method " + methodName
                                + " but changes return type from " + overriddenType + " to " + retRawType
                );
                return null;
            }

            var formals = methodDef.methodSymbol.getFormals();
            var overriddenFormals = overriddenMethod.getFormals();
            if (formals.size() != overriddenFormals.size()) {
                SymbolTable.error(
                        methodDef.ctx,
                        methodDef.name,
                        "Class " + classScope.getName() + " overrides method " + methodName
                                + " with different number of formal parameters"
                );
                return null;
            }

            TypeSymbol formalSym, overriddenSym;
            for (int i = 0; i != formals.size(); ++i) {
                formalSym = formals.get(i).getType();
                overriddenSym = overriddenFormals.get(i).getType();

                if (formalSym == null || overriddenSym == null) {
                    continue;
                }

                if (!formalSym.inherits(overriddenSym)) {
                    SymbolTable.error(
                            methodDef.ctx,
                            ((CoolParser.MethodContext)methodDef.ctx).formals.get(i).stop,
                            "Class " + classScope.getName() + " overrides method " + methodName
                                    + " but changes type of formal parameter " + formals.get(i).getName() + " from "
                                    + overriddenSym.getName() + " to " + formalSym.getName()
                    );
                    return null;
                }
            }
        }

        scope = methodSymbol;
        var body = methodDef.body;
        var bodyType = methodDef.body.accept(this);
        if (bodyType != null) {
            if (!bodyType.getName().equals(retRawType.getName())) {
                bodyType = getActualType(bodyType.getName());
                if (!bodyType.inherits(retRawType)) {
                    SymbolTable.error(
                            methodDef.ctx,
                            body.ctx.start,
                            "Type " + bodyType + " of the body of method " + methodName
                                    + " is incompatible with declared return type " + retRawType
                    );
                }
            }
        }
        scope = classScope;

        return null;
    }

    @Override
    public TypeSymbol visit(AttributeDef attributeDef) {
        var symbol = attributeDef.idSymbol;
        if (symbol == null) {
            return null;
        }

        var typeSymbol= getActualType(symbol.getType().getName());
        var value = attributeDef.init;
        if (value != null) {
            var valueRawType = value.accept(this);
            var valueType = getActualType(valueRawType.getName());

            if (!valueType.inherits(typeSymbol)) {
                SymbolTable.error(
                        attributeDef.ctx,
                        value.ctx.start,
                        "Type " + valueRawType +  " of initialization expression of attribute "  + symbol
                                + " is incompatible with declared type " + typeSymbol
                );
            }
        }

        return typeSymbol;
    }

    @Override
    public TypeSymbol visit(MethodCall methodCall) {
        var caller = methodCall.callerObject;
        var actualCallerType = caller != null ?
                caller.accept(this)
                : getActualType("SELF_TYPE");
        var callerType = caller != null ?
                getActualType(caller.accept(this).getName())
                : getActualType("SELF_TYPE");

        var actualObj = methodCall.dispatchType;
        if (actualObj != null) {
            var actualTypeName = actualObj.getText();
            if (actualTypeName.equals("SELF_TYPE")) {
                SymbolTable.error(
                        methodCall.ctx,
                        methodCall.dispatchType,
                        "Type of static dispatch cannot be SELF_TYPE"
                );
                return TypeSymbol.OBJECT;
            }

            var actualType = (TypeSymbol)SymbolTable.globals.lookup(actualTypeName);
            if (actualType == null) {
                SymbolTable.error(
                        methodCall.ctx,
                        methodCall.dispatchType,
                        "Type " + actualObj.getText() + " of static dispatch is undefined"
                );
                return TypeSymbol.OBJECT;
            }

            if (!callerType.inherits(actualType)) {
                SymbolTable.error(
                        methodCall.ctx,
                        methodCall.dispatchType,
                        "Type " + actualType + " of static dispatch is not a superclass of type " + callerType
                );
                return TypeSymbol.OBJECT;
            }

            callerType = actualType;
        }

        var methodName = methodCall.methodName.getText();
        var methodSymb = callerType.lookupMethod(methodName);
        if (methodSymb == null) {
            SymbolTable.error(
                    methodCall.ctx,
                    methodCall.methodName,
                    "Undefined method " + methodName + " in class " + callerType
            );
            return TypeSymbol.OBJECT;
        }

        var actualTypes = methodCall
                .args
                .stream()
                .map(expr -> expr.accept(this))
                .collect(Collectors.toList());
        var formalIds = methodSymb.getFormals();
        if (actualTypes.size() != formalIds.size()) {
            SymbolTable.error(
                    methodCall.ctx,
                    methodCall.methodName,
                    "Method " + methodName + " of class " + callerType
                            + " is applied to wrong number of arguments"
            );
            return TypeSymbol.OBJECT;
        }

        for (int i = 0; i != actualTypes.size(); ++i) {
            if (!actualTypes.get(i).inherits(formalIds.get(i).getType())) {
                SymbolTable.error(
                        methodCall.ctx,
                        ((CoolParser.MethodCallContext)methodCall.ctx).args.get(i).start,
                        "In call to method " + methodName + " of class " + callerType + ", actual type "
                                + actualTypes.get(i) + " of formal parameter " + formalIds.get(i)
                                + " is incompatible with declared type " + formalIds.get(i).getType()
                );
            }
        }

        if (methodSymb.getReturnType() == TypeSymbol.SELF_TYPE) {
            return actualCallerType;
        }

        return methodSymb.getReturnType();
    }

    @Override
    public TypeSymbol visit(ClassMethodCall classMethodCall) {

        var actualCallerType = getActualType("SELF_TYPE");
        var callerType = getActualType("SELF_TYPE");

        var methodName = classMethodCall.methodName.getText();
        var methodSymb = callerType.lookupMethod(methodName);
        if (methodSymb == null) {
            SymbolTable.error(
                    classMethodCall.ctx,
                    classMethodCall.methodName,
                    "Undefined method " + methodName + " in class " + callerType
            );
            return TypeSymbol.OBJECT;
        }

        var actualTypes = classMethodCall
                .args
                .stream()
                .map(expr -> expr.accept(this))
                .collect(Collectors.toList());
        var formalIds = methodSymb.getFormals();
        if (actualTypes.size() != formalIds.size()) {
            SymbolTable.error(
                    classMethodCall.ctx,
                    classMethodCall.methodName,
                    "Method " + methodName + " of class " + callerType
                            + " is applied to wrong number of arguments"
            );
            return TypeSymbol.OBJECT;
        }

        for (int i = 0; i != actualTypes.size(); ++i) {
            if (!actualTypes.get(i).inherits(formalIds.get(i).getType())) {
                SymbolTable.error(
                        classMethodCall.ctx,
                        ((CoolParser.ClassMethodCallContext)classMethodCall.ctx).args.get(i).start,
                        "In call to method " + methodName + " of class " + callerType + ", actual type "
                                + actualTypes.get(i) + " of formal parameter " + formalIds.get(i)
                                + " is incompatible with declared type " + formalIds.get(i).getType()
                );
            }
        }

        if (methodSymb.getReturnType() == TypeSymbol.SELF_TYPE) {
            return actualCallerType;
        }

        return methodSymb.getReturnType();
    }

    @Override
    public TypeSymbol visit(CoolIf coolIf) {
        var condType = coolIf.cond.accept(this);
        if (condType != TypeSymbol.BOOL) {
            SymbolTable.error(
                    coolIf.ctx,
                    ((CoolParser.IfContext)coolIf.ctx).cond.start,
                    "If condition has type " + condType + " instead of Bool"
            );
        }

        return TypeSymbol.getLCA(
                coolIf.thenBranch.accept(this),
                coolIf.elseBranch.accept(this)
        );
    }

    @Override
    public TypeSymbol visit(CoolWhile coolWhile) {
        var condType = coolWhile.cond.accept(this);
        if (condType != TypeSymbol.BOOL) {
            SymbolTable.error(
                    coolWhile.ctx,
                    ((CoolParser.WhileContext)coolWhile.ctx).cond.start,
                    "While condition has type " + condType + " instead of Bool"
            );
        }

        return TypeSymbol.OBJECT;
    }

    @Override
    public TypeSymbol visit(Block block) {
        return block
                .instructions
                .stream()
                .map(expr -> expr.accept(this))
                .filter(Objects::nonNull)
                .reduce((first, second) -> second).orElse(null);
    }

    @Override
    public TypeSymbol visit(Local local) {
        if (local.idSymbol == null) {
            return null;
        }

        var varName = local.name.getText();
        var varType = local.type.getText();
        var varRawType = (TypeSymbol)SymbolTable.globals.lookup(varType);

        var varTypeSymbol = getActualType(varType);
        if (varTypeSymbol == null) {
            SymbolTable.error(
                    local.ctx,
                    local.type,
                    "Let variable " + varName + " has undefined type " + varType
            );
            local.idSymbol = null;

            return null;
        }

        var value = local.init;
        if (value != null) {
            var valueType = value.accept(this);
            if (valueType != null && !valueType.inherits(varTypeSymbol)) {
                SymbolTable.error(
                        local.ctx,
                        value.ctx.start,
                        "Type " + valueType + " of initialization expression of identifier " + varName
                                + " is incompatible with declared type " + varTypeSymbol
                );
            }
        }

        local.idSymbol.setType(varRawType);

        return null;
    }

    @Override
    public TypeSymbol visit(Let let) {
        scope = let.letScope;
        IdSymbol localSymbol;

        for (var localVar : let.vars) {
            localVar.accept(this);

            localSymbol = localVar.idSymbol;
            if (localSymbol != null) {
                scope.add(localSymbol);
            }
        }

        var retValue = let.body.accept(this);
        scope = scope.getParent();

        return retValue;
    }

    @Override
    public TypeSymbol visit(CaseBranch caseBranch) {
        var typeName = caseBranch.type.getText();
        if (typeName.equals("SELF_TYPE")) {
            return null;
        }

        var caseType = getActualType(typeName);
        if (caseType == null) {
            SymbolTable.error(
                    caseBranch.ctx,
                    caseBranch.type,
                    "Case variable " + caseBranch.name.getText() + " has undefined type " + typeName
            );
            return null;
        }

        var caseScope = new CaseScope(scope);
        var id = new IdSymbol(caseBranch.name.getText());
        id.setType(caseType);
        caseScope.add(id);

        scope = caseScope;
        var retType = caseBranch.assignment.accept(this);
        scope = caseScope.getParent();

        return retType;
    }

    @Override
    public TypeSymbol visit(CoolCase coolCase) {
        return coolCase
                .branches
                .stream()
                .map(br -> br.accept(this))
                .filter(Objects::nonNull)
                .reduce(TypeSymbol::getLCA).orElse(null);
    }

    @Override
    public TypeSymbol visit(NewObject newObject) {
        var typeName = newObject.type.getText();
        var rawType = (TypeSymbol)SymbolTable.globals.lookup(typeName);
        var type = getActualType(typeName);
        if (type == null) {
            SymbolTable.error(
                    newObject.ctx,
                    newObject.type,
                    "new is used with undefined type " + typeName
            );
            return null;
        }

        return rawType;
    }

    @Override
    public TypeSymbol visit(NegSign negSign) {
        TypeSymbol opType = negSign.expr.accept(this);
        if (opType == null) {
            return null;
        }

        if (opType != TypeSymbol.INT) {
            SymbolTable.error(
                    negSign.ctx,
                    negSign.ctx.stop,
                    "Operand of " + negSign.token.getText() + " has type " + opType.getName()
                            + " instead of Int"
            );
            return TypeSymbol.INT;
        }

        return opType;
    }

    @Override
    public TypeSymbol visit(CoolIsvoid coolIsvoid) {
        TypeSymbol opType = coolIsvoid.expr.accept(this);
        if (opType == null) {
            return null;
        }

        return TypeSymbol.BOOL;
    }

    @Override
    public TypeSymbol visit(MultDiv multDiv) {

        var leftSymb = multDiv.leftExpr.accept(this);
        var rightSymb = multDiv.rightExpr.accept(this);

        if (leftSymb == null || rightSymb == null) {
            return TypeSymbol.INT;
        }

        var leftName = leftSymb.getName();
        var rightName = rightSymb.getName();

        if (leftSymb != TypeSymbol.INT && rightSymb == TypeSymbol.INT) {
            SymbolTable.error(
                    multDiv.ctx,
                    multDiv.ctx.start,
                    "Operand of " + multDiv.token.getText() + " has type " + leftSymb
                            + " instead of Int"
            );
        }
        if (leftSymb == TypeSymbol.INT && rightSymb != TypeSymbol.INT) {
            SymbolTable.error(
                    multDiv.ctx,
                    multDiv.ctx.stop,
                    "Operand of " + multDiv.token.getText() + " has type " + rightSymb
                            + " instead of Int"
            );
        }


        return TypeSymbol.INT;
    }

    @Override
    public TypeSymbol visit(PlusMinus plusMinus) {
        var leftSymb = plusMinus.leftExpr.accept(this);
        var rightSymb = plusMinus.rightExpr.accept(this);

        if (leftSymb == null || rightSymb == null) {
            return TypeSymbol.INT;
        }

        var leftName = leftSymb.getName();
        var rightName = rightSymb.getName();

        if (leftSymb != TypeSymbol.INT && rightSymb == TypeSymbol.INT) {
            SymbolTable.error(
                    plusMinus.ctx,
                    plusMinus.ctx.start,
                    "Operand of " + plusMinus.token.getText() + " has type " + leftSymb
                            + " instead of Int"
            );
        }
        if (leftSymb == TypeSymbol.INT && rightSymb != TypeSymbol.INT) {
            SymbolTable.error(
                    plusMinus.ctx,
                    plusMinus.ctx.stop,
                    "Operand of " + plusMinus.token.getText() + " has type " + rightSymb
                            + " instead of Int"
            );
        }


        return TypeSymbol.INT;
    }

    @Override
    public TypeSymbol visit(Relational relational) {
        var leftSymb = relational.leftExpr.accept(this);
        var rightSymb = relational.rightExpr.accept(this);

        if (leftSymb == null || rightSymb == null) {
            return TypeSymbol.BOOL;
        }

        var leftName = leftSymb.getName();
        var rightName = rightSymb.getName();

        if (relational.token.getText().equals("=")) {
            if (TypeSymbol.notEqCompatible(leftSymb, rightSymb)) {
                SymbolTable.error(
                        relational.ctx,
                        ((CoolParser.RelationalContext)relational.ctx).op,
                        "Cannot compare " + leftName + " with " + rightName
                );
            }
        } else {
            if (leftSymb != TypeSymbol.INT && rightSymb == TypeSymbol.INT) {
                SymbolTable.error(
                        relational.ctx,
                        relational.ctx.start,
                        "Operand of " + relational.token.getText() + " has type " + leftSymb
                                + " instead of Int"
                );
            }
            if (leftSymb == TypeSymbol.INT && rightSymb != TypeSymbol.INT) {
                SymbolTable.error(
                        relational.ctx,
                        relational.ctx.stop,
                        "Operand of " + relational.token.getText() + " has type " + rightSymb
                                + " instead of Int"
                );
            }
        }

        return TypeSymbol.BOOL;
    }

    @Override
    public TypeSymbol visit(BoolNot boolNot) {
        TypeSymbol opType = boolNot.expr.accept(this);
        if (opType == null) {
            return null;
        }

        if (opType != TypeSymbol.BOOL) {
            SymbolTable.error(
                    boolNot.ctx,
                    boolNot.ctx.stop,
                    "Operand of " + boolNot.token.getText() + " has type " + opType.getName()
                            + " instead of Bool"
            );
            return TypeSymbol.BOOL;
        }

        return opType;
    }

    @Override
    public TypeSymbol visit(Paren paren) {

        return paren.expr.accept(this);
    }

    @Override
    public TypeSymbol visit(Id id) {
        var idName = id.token.getText();

        var idSymbol = (IdSymbol)scope.lookup(idName);
        if (idSymbol == null) {
            SymbolTable.error(
                    id.ctx,
                    id.ctx.getStop(),
                    "Undefined identifier " + idName
            );
            return null;
        }

        return idSymbol.getType();
    }

    @Override
    public TypeSymbol visit(CoolInt coolInt) {

        return TypeSymbol.INT;
    }

    @Override
    public TypeSymbol visit(CoolString coolString) {

        return TypeSymbol.STRING;
    }

    @Override
    public TypeSymbol visit(CoolTrue coolTrue) {

        return TypeSymbol.BOOL;
    }

    @Override
    public TypeSymbol visit(CoolFalse coolFalse) {

        return TypeSymbol.BOOL;
    }

    @Override
    public TypeSymbol visit(Assign assign) {
        var idName = assign.name.getText();
        if (idName.equals("self")) {
            SymbolTable.error(assign.ctx, assign.name, "Cannot assign to self");
            return null;
        }

        var idSymbol = (IdSymbol)scope.lookup(idName);
        if (idSymbol == null) {
            return null;
        }

        var idType = getActualType(idSymbol.getType().getName());
        var value = assign.expr;
        var exprRawType = value.accept(this);

        if (idType == null || exprRawType == null) {
            return null;
        }

        var exprType = getActualType(exprRawType.getName());
        if (!exprType.inherits(idType)) {
            SymbolTable.error(
                    assign.ctx,
                    assign.expr.ctx.start,
                    "Type " + exprRawType + " of assigned expression is incompatible with declared type "
                            + idSymbol.getType() + " of identifier " + idName
            );
        }

        return exprRawType;
    }

    private TypeSymbol getActualType(String typeName) {
        if (!typeName.equals("SELF_TYPE")) {
            return (TypeSymbol)SymbolTable.globals.lookup(typeName);
        }

        Scope currentScope = scope;
        while (!(currentScope instanceof TypeSymbol)) {
            currentScope = currentScope.getParent();
        }

        return (TypeSymbol)currentScope;
    }
}
