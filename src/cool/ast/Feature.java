package cool.ast;

import org.antlr.v4.runtime.ParserRuleContext;

public abstract class Feature extends ASTNode {
    public Feature(ParserRuleContext ctx) {
        this.ctx = ctx;
    }
}
