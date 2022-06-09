package cool.ast;

import cool.structures.IdSymbol;
import cool.visitors.ASTVisitor;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

public class Formal extends ASTNode {
    public Token keyword;
    public Token type;
    public Token name;
    public IdSymbol idSymbol;

    public Formal(ParserRuleContext ctx, Token type, Token name, Token keyword) {
        this.ctx = ctx;
        this.keyword = keyword;
        this.type = type;
        this.name = name;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
