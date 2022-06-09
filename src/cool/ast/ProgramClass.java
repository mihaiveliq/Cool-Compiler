package cool.ast;

import cool.structures.TypeSymbol;
import cool.visitors.ASTVisitor;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

import java.util.List;

public class ProgramClass extends ASTNode {
    public Token keyword;
    public Token name;
    public Token inheritedType;
    public List<Feature> features;
    public TypeSymbol typeSymbol;

    public ProgramClass(ParserRuleContext ctx,
                        Token name,
                        List<Feature> features,
                        Token inheritedType,
                        Token keyword) {
        this.ctx = ctx;
        this.keyword = keyword;
        this.name = name;
        this.features = features;
        this.inheritedType = inheritedType;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
