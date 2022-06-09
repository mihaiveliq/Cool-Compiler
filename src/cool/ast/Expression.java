package cool.ast;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

public abstract class Expression extends ASTNode {

    public Token token;

    public Expression(ParserRuleContext ctx, Token token) {
        this.ctx = ctx;
        this.token = token;
    }
}
