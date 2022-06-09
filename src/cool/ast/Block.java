package cool.ast;

import cool.visitors.ASTVisitor;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

import java.util.List;

public class Block extends Expression {

    public List<Expression> instructions;

    public Block(ParserRuleContext ctx, List<Expression> instructions, Token start) {
        super(ctx, start);
        this.instructions = instructions;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
