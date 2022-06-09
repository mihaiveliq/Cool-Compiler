package cool.ast;

import cool.visitors.ASTVisitor;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

public class Assign extends Expression {
    public Token name;
    public Expression expr;

    public Assign(ParserRuleContext ctx, Token name, Expression expr, Token token) {
        super(ctx, token);
        this.name = name;
        this.expr = expr;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
