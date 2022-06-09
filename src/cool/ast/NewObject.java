package cool.ast;

import cool.visitors.ASTVisitor;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

public class NewObject extends Expression {
    public Token type;

    public NewObject(ParserRuleContext ctx, Token token, Token type) {
        super(ctx, token);
        this.type = type;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
