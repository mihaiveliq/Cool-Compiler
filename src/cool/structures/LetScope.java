package cool.structures;

import java.util.HashMap;

public class LetScope implements Scope {
    private final HashMap<String, IdSymbol> vars;
    private final Scope parent;

    public LetScope(Scope parent) {
        this.parent = parent;
        vars = new HashMap<>();
    }

    @Override
    public boolean add(Symbol sym) {
        vars.put(sym.getName(), (IdSymbol)sym);
        return true;
    }

    @Override
    public Symbol lookup(String str) {
        Symbol sym = vars.get(str);
        if (sym == null) {
            return getParent().lookup(str);
        }

        return sym;
    }

    @Override
    public Scope getParent() {
        return parent;
    }
}
