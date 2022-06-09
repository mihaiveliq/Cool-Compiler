package cool.ast;

import cool.structures.MethodSymbol;
import cool.visitors.ASTVisitor;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

import java.util.List;

public class MethodDef extends Feature {
    public Token keyword;
    public Token returnType;
    public Token name;
    public List<Formal> formals;
    public Expression body;
    public MethodSymbol methodSymbol;

    public MethodDef(ParserRuleContext ctx,
                     Token returnType,
                     Token name,
                     List<Formal> formals,
                     Expression body,
                     Token keyword) {
        super(ctx);
        this.keyword = keyword;
        this.returnType = returnType;
        this.name = name;
        this.formals = formals;
        this.body = body;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
