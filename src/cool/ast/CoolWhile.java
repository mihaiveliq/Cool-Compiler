package cool.ast;

import cool.visitors.ASTVisitor;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

public class CoolWhile extends Expression {

    public Expression cond;
    public Expression body;

    public CoolWhile(ParserRuleContext ctx,
                     Expression cond,
                     Expression body,
                     Token start) {

        super(ctx, start);
        this.cond = cond;
        this.body = body;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
