package cool.ast;

import cool.structures.Scope;
import cool.structures.Symbol;
import cool.structures.TypeSymbol;
import cool.visitors.ASTVisitor;
import org.antlr.v4.runtime.ParserRuleContext;

public abstract class ASTNode {
    public ParserRuleContext ctx;
    public <T> T accept(ASTVisitor<T> visitor) {
        return null;
    }
}
