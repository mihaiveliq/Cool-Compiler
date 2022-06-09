package cool.visitors;

import cool.ast.*;
import cool.structures.*;

import java.util.HashSet;

public class AstDefinitionVisitor implements ASTVisitor<Void> {
    private Scope scope;
	private final HashSet<String> illegalParents;
    private int tagCounter = 5;

	public AstDefinitionVisitor() {
		illegalParents = new HashSet<>();
		illegalParents.add(TypeSymbol.INT.getName());
		illegalParents.add(TypeSymbol.BOOL.getName());
		illegalParents.add(TypeSymbol.STRING.getName());
		illegalParents.add("SELF_TYPE");
	}

    @Override
    public Void visit(Program program) {
        program.programClasses.forEach(this::visit);
	    return null;
    }

    @Override
    public Void visit(ProgramClass programClass) {
        var className = programClass.name.getText();
        if (className.equals("SELF_TYPE")) {
            SymbolTable.error(
                    programClass.ctx,
                    programClass.name,
                    "Class has illegal name " + className
            );
            return null;
        }

        var parentToken = programClass.inheritedType;
        var parentName = parentToken == null ? "Object" : parentToken.getText();

        var type = new TypeSymbol(className, parentName);
        if (!SymbolTable.globals.add(type)) {
            SymbolTable.error(
                    programClass.ctx,
                    programClass.name,
                    "Class " + className + " is redefined"
            );
            return null;
        }
        if (illegalParents.contains(parentName)) {
            SymbolTable.error(
                    programClass.ctx,
                    programClass.inheritedType,
                    "Class " + className + " has illegal parent " + parentName
            );
            return null;
        }

        type.tag = tagCounter;
        tagCounter++;

        programClass.typeSymbol = type;

        scope = type;

        programClass.features.forEach(feature -> feature.accept(this));

        return null;
    }

    @Override
    public Void visit(Formal formal) {
        var formalName = formal.name.getText();
        var methodScope = (MethodSymbol)scope;
        var classScope = (TypeSymbol)scope.getParent();

        if (formalName.equals("self")) {
            SymbolTable.error(
                    formal.ctx,
                    formal.name,
                    "Method " + methodScope.getName() + " of class "  + classScope.getName()
                            + " has formal parameter with illegal name " + formalName
            );
            return null;
        }

        var formalType = formal.type.getText();
        if (formalType.equals("SELF_TYPE")) {
            SymbolTable.error(
                    formal.ctx,
                    formal.type,
                    "Method " + methodScope.getName() + " of class "  + classScope.getName()
                            + " has formal parameter " + formalName + " with illegal type " + formalType
            );
            return null;
        }

        var formalSymbol = new IdSymbol(formalName);
        if (!scope.add(formalSymbol)) {
            SymbolTable.error(
                    formal.ctx,
                    formal.name,
                    "Method " + methodScope + " of class " + classScope
                            + " redefines formal parameter " + formalName
            );
            return null;
        }

        formal.idSymbol = formalSymbol;

        return null;
    }

    @Override
    public Void visit(MethodDef methodDef) {

        var methodName = methodDef.name.getText();
        var classScope = (TypeSymbol)scope;
        var methodSymbol = new MethodSymbol(methodName);
        methodSymbol.setParent(classScope);

        if (!classScope.addMethod(methodSymbol)) {
            SymbolTable.error(
                    methodDef.ctx,
                    methodDef.name,
                    "Class " + classScope.getName()  + " redefines method " + methodName
            );
            return null;
        }

        methodDef.methodSymbol = methodSymbol;

        scope = methodSymbol;
        methodDef.formals.forEach(node -> node.accept(this));
        methodDef.body.accept(this);
        scope = scope.getParent();

        return null;
    }

    @Override
    public Void visit(AttributeDef attributeDef) {
        var attribName = attributeDef.name.getText();

        if (attribName.equals("self")) {
            SymbolTable.error(
                    attributeDef.ctx,
                    attributeDef.name,
                    "Class " + ((TypeSymbol)scope).getName()  + " has attribute with illegal name " + attribName
            );
            return null;
        }

        var symbol = new IdSymbol(attribName);
        if (!scope.add(symbol)) {
            SymbolTable.error(
                    attributeDef.ctx,
                    attributeDef.name,
                    "Class " + ((TypeSymbol)scope).getName()  + " redefines attribute " + attribName
            );
            return null;
        }

        attributeDef.idSymbol = symbol;

        var value = attributeDef.init;
        if (value != null) {
            value.accept(this);
        }

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

        coolIf.thenBranch.accept(this);
        coolIf.elseBranch.accept(this);
	    return null;
    }

    @Override
    public Void visit(CoolWhile coolWhile) {
        coolWhile.body.accept(this);
	    return null;
    }

    @Override
    public Void visit(Block block) {
        block.instructions.forEach(expr -> expr.accept(this));
	    return null;
    }

    @Override
    public Void visit(Local local) {
        var varName = local.name.getText();
        if (varName.equals("self")) {
            SymbolTable.error(
                    local.ctx,
                    local.name,
                    "Let variable has illegal name " + varName
            );
            return null;
        }

        var varSym = new IdSymbol(varName);
        local.idSymbol = varSym;

        var value = local.init;
        if (value != null) {
            value.accept(this);
        }

        return null;
    }

    @Override
    public Void visit(Let let) {
        let.letScope = new LetScope(scope);

        scope = let.letScope;
        let.vars.forEach(local -> local.accept(this));
        let.body.accept(this);
        scope = scope.getParent();

        return null;
    }

    @Override
    public Void visit(CaseBranch caseBranch) {
        var idName = caseBranch.name.getText();
        if (idName.equals("self")) {
            SymbolTable.error(
                    caseBranch.ctx,
                    caseBranch.name,
                    "Case variable has illegal name " + idName
            );
            return null;
        }

        var typeName = caseBranch.type.getText();
        if (typeName.equals("SELF_TYPE")) {
            SymbolTable.error(
                    caseBranch.ctx,
                    caseBranch.type,
                    "Case variable " + idName + " has illegal type " + typeName
            );
            return null;
        }

        caseBranch.assignment.accept(this);

        return null;
    }

    @Override
    public Void visit(CoolCase coolCase) {
        coolCase.branches.forEach(br -> br.accept(this));
	    return null;
    }

    @Override
    public Void visit(NewObject newObject) {
	    return null;
    }

    @Override
    public Void visit(NegSign negSign) {
        negSign.expr.accept(this);
	    return null;
    }

    @Override
    public Void visit(CoolIsvoid coolIsvoid) {
        coolIsvoid.expr.accept(this);
	    return null;
    }

    @Override
    public Void visit(MultDiv multDiv) {
        multDiv.leftExpr.accept(this);
        multDiv.rightExpr.accept(this);
	    return null;
    }

    @Override
    public Void visit(PlusMinus plusMinus) {

        plusMinus.leftExpr.accept(this);
        plusMinus.rightExpr.accept(this);
	    return null;
    }

    @Override
    public Void visit(Relational relational) {
        relational.leftExpr.accept(this);
        relational.rightExpr.accept(this);
	    return null;
    }

    @Override
    public Void visit(BoolNot boolNot) {
        boolNot.expr.accept(this);
	    return null;
    }

    @Override
    public Void visit(Paren paren) {
        paren.expr.accept(this);
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

        assign.expr.accept(this);
	    return null;
    }
}
