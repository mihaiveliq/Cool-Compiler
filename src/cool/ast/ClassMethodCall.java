package cool.ast;

import cool.visitors.ASTVisitor;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

import java.util.List;

public class ClassMethodCall extends Expression {
    public String dispatch;
    public Token methodName;
    public List<Expression> args;

    public ClassMethodCall(ParserRuleContext ctx,
                           Token start,
                           Token methodName,
                           List<Expression> args) {

        super(ctx, start);
        this.dispatch = "implicit dispatch";
        this.methodName = methodName;
        this.args = args;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
