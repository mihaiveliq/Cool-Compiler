package cool.visitors;

import cool.ast.*;
import cool.parser.CoolParser;
import cool.parser.CoolParserBaseVisitor;

import java.util.stream.Collectors;

public class AstConstructionVisitor extends CoolParserBaseVisitor<ASTNode> {

    @Override
    public ASTNode visitProgram(CoolParser.ProgramContext ctx) {

        return new Program(ctx,
                ctx.classes
                    .stream()
                    .map(programClass -> (ProgramClass)visitProgramClass(programClass))
                    .collect(Collectors.toList()),
                ctx.getStart());
    }

    @Override
    public ASTNode visitProgramClass(CoolParser.ProgramClassContext ctx) {

        return new ProgramClass(ctx,
                ctx.name,
                ctx.features
                        .stream()
                        .map(feature -> (Feature)visit(feature))
                        .collect(Collectors.toList()),
                ctx.inheritType,
                ctx.getStart());
    }

    @Override
    public ASTNode visitMethod(CoolParser.MethodContext ctx) {
        return new MethodDef(ctx,
                ctx.type,
                ctx.name,
                ctx.formals
                        .stream()
                        .map(formal -> (Formal)visitFormal(formal))
                        .collect(Collectors.toList()),
                (Expression)visit(ctx.body),
                ctx.getStart());
    }

    @Override
    public ASTNode visitAttribute(CoolParser.AttributeContext ctx) {
        var initValue = ctx.init == null ? null : (Expression)visit(ctx.init);

        return new AttributeDef(ctx,
                ctx.type,
                ctx.name,
                initValue,
                ctx.getStart());
    }

    @Override
    public ASTNode visitFormal(CoolParser.FormalContext ctx) {
        return new Formal(ctx, ctx.type, ctx.name, ctx.getStart());
    }

    @Override
    public ASTNode visitNew(CoolParser.NewContext ctx) {
        return new NewObject(ctx, ctx.getStart(), ctx.type);
    }

    @Override
    public ASTNode visitPlusMinus(CoolParser.PlusMinusContext ctx) {
        return new PlusMinus(ctx,
                (Expression)visit(ctx.left),
                (Expression)visit(ctx.right),
                ctx.op);
    }

    @Override
    public ASTNode visitIsvoid(CoolParser.IsvoidContext ctx) {
        return new CoolIsvoid(ctx,
                (Expression)visit(ctx.e),
                ctx.getStart());
    }

    @Override
    public ASTNode visitCoolInt(CoolParser.CoolIntContext ctx) {
        return new CoolInt(ctx, ctx.getStart());
    }

    @Override
    public ASTNode visitCoolString(CoolParser.CoolStringContext ctx) {
        return new CoolString(ctx, ctx.getStart());
    }

    @Override
    public ASTNode visitWhile(CoolParser.WhileContext ctx) {
        return new CoolWhile(ctx,
                (Expression)visit(ctx.cond),
                (Expression)visit(ctx.body),
                ctx.getStart());
    }

    @Override
    public ASTNode visitBoolNot(CoolParser.BoolNotContext ctx) {
        return new BoolNot(ctx,
                (Expression)visit(ctx.e),
                ctx.getStart());
    }

    @Override
    public ASTNode visitParen(CoolParser.ParenContext ctx) {
        return new Paren(ctx,
                (Expression)visit(ctx.e),
                ctx.getStart());
    }

    @Override
    public ASTNode visitNegSign(CoolParser.NegSignContext ctx) {
        return new NegSign(ctx,
                (Expression)visit(ctx.e),
                ctx.getStart());
    }

    @Override
    public ASTNode visitMultDiv(CoolParser.MultDivContext ctx) {
        return new MultDiv(ctx,
                (Expression)visit(ctx.left),
                (Expression)visit(ctx.right),
                ctx.op);
    }

    @Override
    public ASTNode visitBlock(CoolParser.BlockContext ctx) {
        return new Block(ctx,
                ctx.instructions
                    .stream()
                    .map(instruction -> (Expression)visit(instruction))
                    .collect(Collectors.toList()),
                ctx.getStart());
    }

    @Override
    public ASTNode visitLet(CoolParser.LetContext ctx) {
        return new Let(ctx,
                (Expression)visit(ctx.body),
                ctx.vars
                        .stream()
                        .map(local -> (Local)visitLocal(local))
                        .collect(Collectors.toList()),
                ctx.getStart());
    }

    @Override
    public ASTNode visitCoolFalse(CoolParser.CoolFalseContext ctx) {
        return new CoolFalse(ctx, ctx.getStart());
    }

    @Override
    public ASTNode visitRelational(CoolParser.RelationalContext ctx) {
        return new Relational(ctx,
                (Expression)visit(ctx.left),
                (Expression)visit(ctx.right),
                ctx.op);
    }

    @Override
    public ASTNode visitClassMethodCall(CoolParser.ClassMethodCallContext ctx) {
        return new ClassMethodCall(ctx,
                ctx.getStart(),
                ctx.name,
                ctx.args
                        .stream()
                        .map(arg -> (Expression)visit(arg))
                        .collect(Collectors.toList()));
    }

    @Override
    public ASTNode visitId(CoolParser.IdContext ctx) {
        return new Id(ctx, ctx.getStart());
    }

    @Override
    public ASTNode visitCoolTrue(CoolParser.CoolTrueContext ctx) {
        return new CoolTrue(ctx, ctx.getStart());
    }

    @Override
    public ASTNode visitIf(CoolParser.IfContext ctx) {
        return new CoolIf(ctx,
                (Expression)visit(ctx.cond),
                (Expression)visit(ctx.leftBr),
                (Expression)visit(ctx.rightBr),
                ctx.getStart());
    }

    @Override
    public ASTNode visitCase(CoolParser.CaseContext ctx) {
        return new CoolCase(ctx,
                (Expression)visit(ctx.var),
                ctx.branches
                        .stream()
                        .map(branch -> (CaseBranch)visitCaseBranch(branch))
                        .collect(Collectors.toList()),
                ctx.getStart());
    }

    @Override
    public ASTNode visitAssign(CoolParser.AssignContext ctx) {
        return new Assign(ctx,
                ctx.name,
                (Expression)visit(ctx.e),
                ctx.getStart());
    }

    @Override
    public ASTNode visitMethodCall(CoolParser.MethodCallContext ctx) {
        return new MethodCall(ctx,
                ctx.getStart(),
                ctx.dispType,
                ctx.name,
                (Expression) visit(ctx.caller),
                ctx.args
                        .stream()
                        .map(arg -> (Expression)visit(arg))
                        .collect(Collectors.toList()));
    }

    @Override
    public ASTNode visitLocal(CoolParser.LocalContext ctx) {
        var initValue = ctx.init == null ? null : (Expression)visit(ctx.init);

        return new Local(ctx,
                ctx.type,
                ctx.name,
                initValue);
    }

    @Override
    public ASTNode visitCaseBranch(CoolParser.CaseBranchContext ctx) {
        return new CaseBranch(ctx,
                ctx.type,
                ctx.name,
                (Expression)visit(ctx.assignment));
    }
}