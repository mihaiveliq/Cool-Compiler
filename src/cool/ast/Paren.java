package cool.ast;

import cool.visitors.ASTVisitor;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

public class Paren extends Expression {
    public Expression expr;

    public Paren(ParserRuleContext ctx, Expression expr, Token start) {
        super(ctx, start);
        this.expr = expr;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
