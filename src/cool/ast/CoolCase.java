package cool.ast;

import cool.visitors.ASTVisitor;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

import java.util.List;

public class CoolCase extends Expression {

    public Expression var;
    public List<CaseBranch> branches;

    public CoolCase(ParserRuleContext ctx,
                    Expression var,
                    List<CaseBranch> branches,
                    Token start) {

        super(ctx, start);
        this.var = var;
        this.branches = branches;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
