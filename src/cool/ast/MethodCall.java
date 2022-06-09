package cool.ast;

import cool.visitors.ASTVisitor;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

import java.util.List;

public class MethodCall extends Expression {
    public String dispatch;
    public Token dispatchType;
    public Token methodName;
    public Expression callerObject;
    public List<Expression> args;

    public MethodCall(ParserRuleContext ctx,
                      Token start,
                      Token dispatchType,
                      Token methodName,
                      Expression callerObject,
                      List<Expression> args) {

        super(ctx, start);
        this.dispatch = ".";
        this.dispatchType = dispatchType;
        this.callerObject = callerObject;
        this.methodName = methodName;
        this.args = args;
    }

    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
