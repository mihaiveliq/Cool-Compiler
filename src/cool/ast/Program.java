package cool.ast;

import cool.visitors.ASTVisitor;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

import java.util.List;

public class Program extends ASTNode {
    public Token keyword;
    public List<ProgramClass> programClasses;

    public Program(ParserRuleContext ctx, List<ProgramClass> programClasses, Token keyword) {
        this.ctx = ctx;
        this.keyword = keyword;
        this.programClasses = programClasses;
    }

    @Override
    public <T> T accept(ASTVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
