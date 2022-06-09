package cool.visitors;

import cool.ast.*;
import cool.structures.*;

public class AstClassHierarchyVisitor implements ASTVisitor<Void> {
    Scope scope;

    @Override
    public Void visit(Program program) {
        program.programClasses.forEach(this::visit);
        return null;
    }

    private void setHierarchy(ProgramClass node) {
        var type = node.typeSymbol;
        Symbol parentType;

        while (type != TypeSymbol.OBJECT) {
            parentType = SymbolTable.globals.lookup(type.getParentName());
            if (parentType == null) {
                SymbolTable.error(
                        node.ctx,
                        node.inheritedType,
                        "Class " + node.name.getText() + " has undefined parent " + type.getParentName()
                );
                return;
            }

            type.setParent((TypeSymbol)parentType);
            type = (TypeSymbol)type.getParent();

            if (type == node.typeSymbol) {
                SymbolTable.error(
                        node.ctx,
                        node.name,
                        "Inheritance cycle for class " + node.name.getText()
                );
                return;
            }
        }
    }

    @Override
    public Void visit(ProgramClass programClass) {

        if (programClass.typeSymbol == null) {
            return null;
        }

        setHierarchy(programClass);

        scope = programClass.typeSymbol;
        programClass.features.forEach(feature -> feature.accept(this));

        return null;
    }

    @Override
    public Void visit(Formal formal) {
        var formalId = formal.idSymbol;
        if (formalId == null) {
            return null;
        }

        var methodScope = (MethodSymbol)scope;
        var classScope = (TypeSymbol)methodScope.getParent();

        var formalType = SymbolTable.globals.lookup(formal.type.getText());
        if (formalType == null) {
            SymbolTable.error(
                    formal.ctx,
                    formal.type,
                    "Method " + methodScope.getName() + " of class " + classScope.getName()
                            + " has formal parameter " + formal.name.getText() + " with undefined type "
                            + formal.type.getText()
            );
            return null;
        }

        formalId.setType((TypeSymbol)formalType);

        return null;
    }

    @Override
    public Void visit(MethodDef methodDef) {
        var methodSymbol = methodDef.methodSymbol;
        if (methodSymbol == null) {
            return null;
        }

        var methodName = methodDef.name.getText();
        var retType = methodDef.returnType.getText();

        var retSymbol = SymbolTable.globals.lookup(retType);
        if (retSymbol == null) {
            SymbolTable.error(
                    methodDef.ctx,
                    methodDef.returnType,
                    "Class " + scope + " has method " + methodName + " with undefined return type " + retType
            );
            return null;
        }
        methodSymbol.setReturnType((TypeSymbol)retSymbol);

        scope = methodSymbol;
        methodDef.formals.forEach(node -> node.accept(this));
        scope = methodSymbol.getParent();

        return null;
    }

    @Override
    public Void visit(AttributeDef attributeDef) {
        var symbol = attributeDef.idSymbol;
        if (symbol == null) {
            return null;
        }

        var attribName = attributeDef.name.getText();
        var typeName = attributeDef.type.getText();

        var parentScope = scope.getParent();
        if (parentScope.lookup(attribName) != null) {
            SymbolTable.error(
                    attributeDef.ctx,
                    attributeDef.name,
                    "Class " + ((TypeSymbol)scope).getName()
                            + " redefines inherited attribute " + attribName
            );
            attributeDef.idSymbol = null;

            return null;
        }

        var typeSymbol = SymbolTable.globals.lookup(typeName);
        if (typeSymbol == null) {
            SymbolTable.error(
                    attributeDef.ctx,
                    attributeDef.type,
                    "Class " + ((TypeSymbol)scope).getName() + " has attribute " + attribName
                            + " with undefined type " + typeName
            );
            attributeDef.idSymbol = null;

            return null;
        }
        attributeDef.idSymbol.setType((TypeSymbol)typeSymbol);

        return null;
    }

    @Override
    public Void visit(MethodCall methodCall) {
        return null;
    }

    @Override
    public Void visit(ClassMethodCall classMethodCall) {
        return null;
    }

    @Override
    public Void visit(CoolIf coolIf) {
        return null;
    }

    @Override
    public Void visit(CoolWhile coolWhile) {
        return null;
    }

    @Override
    public Void visit(Block block) {
        return null;
    }

    @Override
    public Void visit(Local local) {
        return null;
    }

    @Override
    public Void visit(Let let) {
        return null;
    }

    @Override
    public Void visit(CaseBranch caseBranch) {
        return null;
    }

    @Override
    public Void visit(CoolCase coolCase) {
        return null;
    }

    @Override
    public Void visit(NewObject newObject) {
        return null;
    }

    @Override
    public Void visit(NegSign negSign) {
        return null;
    }

    @Override
    public Void visit(CoolIsvoid coolIsvoid) {
        return null;
    }

    @Override
    public Void visit(MultDiv multDiv) {
        return null;
    }

    @Override
    public Void visit(PlusMinus plusMinus) {
        return null;
    }

    @Override
    public Void visit(Relational relational) {
        return null;
    }

    @Override
    public Void visit(BoolNot boolNot) {
        return null;
    }

    @Override
    public Void visit(Paren paren) {
        return null;
    }

    @Override
    public Void visit(Id id) {
        return null;
    }

    @Override
    public Void visit(CoolInt coolInt) {
        return null;
    }

    @Override
    public Void visit(CoolString coolString) {
        return null;
    }

    @Override
    public Void visit(CoolTrue coolTrue) {
        return null;
    }

    @Override
    public Void visit(CoolFalse coolFalse) {
        return null;
    }

    @Override
    public Void visit(Assign assign) {
        return null;
    }
}
