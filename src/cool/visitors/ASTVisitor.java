package cool.visitors;

import cool.ast.*;

public interface ASTVisitor<T> {
    T visit(Program program);
    T visit(ProgramClass programClass);
    T visit(Formal formal);
    T visit(MethodDef methodDef);
    T visit(AttributeDef attributeDef);
    T visit(MethodCall methodCall);
    T visit(ClassMethodCall classMethodCall);
    T visit(CoolIf coolIf);
    T visit(CoolWhile coolWhile);
    T visit(Block block);
    T visit(Local local);
    T visit(Let let);
    T visit(CaseBranch caseBranch);
    T visit(CoolCase coolCase);
    T visit(NewObject newObject);
    T visit(NegSign negSign);
    T visit(CoolIsvoid coolIsvoid);
    T visit(MultDiv multDiv);
    T visit(PlusMinus plusMinus);
    T visit(Relational relational);
    T visit(BoolNot boolNot);
    T visit(Paren paren);
    T visit(Id id);
    T visit(CoolInt coolInt);
    T visit(CoolString coolString);
    T visit(CoolTrue coolTrue);
    T visit(CoolFalse coolFalse);
    T visit(Assign assign);
}
