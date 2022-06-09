package cool.ast;

import cool.structures.LetScope;
import cool.visitors.ASTVisitor;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

import java.util.List;

public class Let extends Expression {

    public Expression body;
    public List<Local> vars;
    public LetScope letScope;

    public Let(ParserRuleContext ctx, Expression body, List<Local> vars, Token start) {
        super(ctx, start);
        this.body = body;
        this.vars = vars;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
