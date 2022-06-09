package cool.ast;

import cool.visitors.ASTVisitor;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

public class CoolIf extends Expression {

    public Expression cond;
    public Expression thenBranch;
    public Expression elseBranch;

    public CoolIf(ParserRuleContext ctx,
                  Expression cond,
                  Expression thenBranch,
                  Expression elseBranch,
                  Token start) {

        super(ctx, start);
        this.cond = cond;
        this.thenBranch = thenBranch;
        this.elseBranch = elseBranch;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
