package cool.visitors;

import cool.ast.*;

public class PrintVisitor implements ASTVisitor<Void> {

    int indent = 0;

    @Override
    public Void visit(Program program) {

        // print keyword program
        printIndent("program");

         ++indent;

         // print each of the current program's class
         program.programClasses.forEach(programClass -> programClass.accept(this));

         --indent;

         return null;
    }

    @Override
    public Void visit(ProgramClass programClass) {

        // print keyword class
        printIndent("class");

        ++indent;

        // print the name of the class
        printIndent(programClass.name.getText());

        // print the name of the inherited class if it does inherit one
        if (programClass.inheritedType != null) {
            printIndent(programClass.inheritedType.getText());
        }

        // print each of the current class' feature
        programClass.features.forEach(feature -> feature.accept(this));

        --indent;

        return null;
    }

    @Override
    public Void visit(Formal formal) {

        // print formal label
        printIndent("formal");

        ++indent;

        // print formal parameter's name
        printIndent(formal.name.getText());

        //print formal parameter's type
        printIndent(formal.type.getText());

        --indent;

        return null;
    }

    @Override
    public Void visit(MethodDef methodDef) {

        // print methode label
        printIndent("method");

        ++indent;

        // print method's name
        printIndent(methodDef.name.getText());

        // print formal parameters
        methodDef.formals.forEach(formal -> formal.accept(this));

        // print method's return type
        printIndent(methodDef.returnType.getText());

        // print method's body
        methodDef.body.accept(this);

        --indent;

        return null;
    }

    @Override
    public Void visit(AttributeDef attributeDef) {

        // print attribute label
        printIndent("attribute");

        ++indent;

         // print attribute's name
         printIndent(attributeDef.name.getText());

         // print attribute's type
         printIndent(attributeDef.type.getText());

         // print attribute's assignation if it has some
         if (attributeDef.init != null) {
             attributeDef.init.accept(this);
         }

         --indent;

         return null;
    }

    @Override
    public Void visit(MethodCall methodCall) {

        // print dispatch symbol
        printIndent(".");

        ++indent;

        // print caller object of the method
        methodCall.callerObject.accept(this);

        // print the dispatch type of the method
        if (methodCall.dispatchType != null) {
            printIndent(methodCall.dispatchType.getText());
        }

        // print functions name
        printIndent(methodCall.methodName.getText());

        // print method's arguments
        methodCall.args.forEach(arg -> arg.accept(this));

        --indent;

        return null;
    }

    @Override
    public Void visit(ClassMethodCall classMethodCall) {

        // print implicit dispatch label
        printIndent("implicit dispatch");

        ++indent;

        // print method's name
        printIndent(classMethodCall.methodName.getText());

        // print method's arguments
        classMethodCall.args.forEach(arg -> arg.accept(this));

        --indent;

        return null;
    }

    @Override
    public Void visit(CoolIf coolIf) {

        // print keyword if
        printIndent("if");

        ++indent;

        // print condition
        coolIf.cond.accept(this);

        // print then branch
        coolIf.thenBranch.accept(this);

        // print else branch
        coolIf.elseBranch.accept(this);

        --indent;

        return null;
    }

    @Override
    public Void visit(CoolWhile coolWhile) {

        // print keyword while
        printIndent("while");

        ++indent;

        // print condition
        coolWhile.cond.accept(this);

        // print body
        coolWhile.body.accept(this);

        --indent;

        return null;
    }

    @Override
    public Void visit(Block block) {

        // print block label
        printIndent("block");

        ++indent;

        // print each instruction in the block
        block.instructions.forEach(instruction -> instruction.accept(this));

        --indent;

        return null;
    }

    @Override
    public Void visit(Local local) {

        // print keyword local
        printIndent("local");

        ++indent;

        // print the name of the local variable
        printIndent(local.name.getText());

        // print the type of the local variable
        printIndent(local.type.getText());

        // print the value of the local variable
        if (local.init != null) {
            local.init.accept(this);
        }

        --indent;

        return null;
    }

    @Override
    public Void visit(Let let) {

        // print let keyword
        printIndent("let");

        ++indent;

        // print the local variables
        let.vars.forEach(local -> local.accept(this));

        // print let's body
        let.body.accept(this);

        --indent;

        return null;
    }

    @Override
    public Void visit(CaseBranch caseBranch) {

        // print case branch label
        printIndent("case branch");

        ++indent;

        // print the variable to be assigned
        printIndent(caseBranch.name.getText());

        // print the type of the variable to verify
        printIndent(caseBranch.type.getText());

        // print the assignation value
        caseBranch.assignment.accept(this);

        --indent;

        return null;
    }

    @Override
    public Void visit(CoolCase coolCase) {

        // print the keyword case
        printIndent(coolCase.token.getText());

        ++indent;

        // print the variable to be verified
        coolCase.var.accept(this);

        // print each case branch
        coolCase.branches.forEach(caseBranch -> caseBranch.accept(this));

        --indent;

        return null;
    }

    @Override
    public Void visit(NewObject newObject) {

        // print the keyword NEW
        printIndent(newObject.token.getText());

        ++indent;

        // print the type of the new instance
        printIndent(newObject.type.getText());

        --indent;

        return null;
    }

    @Override
    public Void visit(NegSign negSign) {

        // print the arithmetic negation symbol
        printIndent(negSign.token.getText());

        ++indent;

        // print the negative number
        negSign.expr.accept(this);

        --indent;

        return null;
    }

    @Override
    public Void visit(CoolIsvoid coolIsvoid) {

        // print ISVOID keyword
        printIndent(coolIsvoid.token.getText());

        ++indent;

        // print the expression to be checked if is void
        coolIsvoid.expr.accept(this);

        --indent;

        return null;
    }

    @Override
    public Void visit(MultDiv multDiv) {

        // print the arithmetic operator (* | /)
        printIndent(multDiv.token.getText());

        ++indent;

        // print the left side of the operator
        multDiv.leftExpr.accept(this);

        // print the right side of the operator
        multDiv.rightExpr.accept(this);

        --indent;

        return null;
    }

    @Override
    public Void visit(PlusMinus plusMinus) {

        // print the arithmetic operator (+ | -)
        printIndent(plusMinus.token.getText());

        ++indent;

        // print the left side of the operator
        plusMinus.leftExpr.accept(this);

        // print the right side of the operator
        plusMinus.rightExpr.accept(this);

        --indent;

        return null;
    }

    @Override
    public Void visit(Relational relational) {

        // print the relational operator
        printIndent(relational.token.getText());

        ++indent;

        // print the left side of the operator
        relational.leftExpr.accept(this);

        // print the right side of the operator
        relational.rightExpr.accept(this);

        --indent;

        return null;
    }

    @Override
    public Void visit(BoolNot boolNot) {

        // print the boolean operator
        printIndent(boolNot.token.getText());

        ++indent;

        // print the expression to be negated
        boolNot.expr.accept(this);

        --indent;

        return null;
    }

    @Override
    public Void visit(Paren paren) {

        // print the expression between the parentheses
        paren.expr.accept(this);

        return null;
    }

    @Override
    public Void visit(Id id) {

        // print the id
        printIndent(id.token.getText());
        return null;
    }

    @Override
    public Void visit(CoolInt coolInt) {

        // print the int
        printIndent(coolInt.token.getText());
        return null;
    }

    @Override
    public Void visit(CoolString coolString) {

        // print the string
        printIndent(coolString.token.getText());
        return null;
    }

    @Override
    public Void visit(CoolTrue coolTrue) {

        // print true
        printIndent(coolTrue.token.getText());
        return null;
    }

    @Override
    public Void visit(CoolFalse coolFalse) {

        // print false
        printIndent(coolFalse.token.getText());
        return null;
    }

    @Override
    public Void visit(Assign assign) {

        // print the assignment operator
        printIndent("<-");

        ++indent;

        // print variable's name
        printIndent(assign.name.getText());

        // print the expression to be assigned to the variable
        assign.expr.accept(this);

        --indent;

        return null;
    }

    // function that prints a string with the proper indentation
    void printIndent(String tokenName) {
        for (int i = 0; i < indent; ++i) {
            System.out.print("  ");
        }
        System.out.println(tokenName);
    }
}
