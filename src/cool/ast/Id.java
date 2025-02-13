package cool.ast;

import cool.structures.IdSymbol;
import cool.visitors.ASTVisitor;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

public class Id extends Expression {

    public Id(ParserRuleContext ctx, Token token) {
        super(ctx, token);
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
