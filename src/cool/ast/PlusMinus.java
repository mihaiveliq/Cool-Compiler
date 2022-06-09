package cool.ast;

import cool.visitors.ASTVisitor;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

public class PlusMinus extends Expression {
    public Expression leftExpr;
    public Expression rightExpr;

    public PlusMinus(ParserRuleContext ctx,
                     Expression leftExpr,
                     Expression rightExpr,
                     Token op) {

        super(ctx, op);
        this.leftExpr = leftExpr;
        this.rightExpr = rightExpr;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
