package cool.ast;

import cool.visitors.ASTVisitor;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

public class CaseBranch extends ASTNode {
    public String keyword;
    public Token type;
    public Token name;
    public Expression assignment;

    public CaseBranch(ParserRuleContext ctx,
                      Token type,
                      Token name,
                      Expression assignment) {
        this.ctx = ctx;
        this.keyword = "case branch";
        this.type = type;
        this.name = name;
        this.assignment = assignment;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
