package cool.ast;

import cool.structures.IdSymbol;
import cool.visitors.ASTVisitor;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

public class Local extends ASTNode {
    public Token type;
    public Token name;
    public Expression init;
    public IdSymbol idSymbol;

    public Local(ParserRuleContext ctx, Token type, Token name, Expression init) {
        this.ctx = ctx;
        this.type = type;
        this.name = name;
        this.init = init;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
