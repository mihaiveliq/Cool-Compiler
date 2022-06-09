package cool.structures;

import java.util.*;

//O functie este atât simbol, cât și domeniu de vizibilitate pentru parametrii
//săi formali.

public class MethodSymbol extends IdSymbol implements Scope {

    private final LinkedHashMap<String, IdSymbol> formals;
    private TypeSymbol parent;
    private TypeSymbol returnType;

    public MethodSymbol(String name) {
        super(name);

        formals = new LinkedHashMap<>();
    }

    public ArrayList<IdSymbol> getFormals() {
        return new ArrayList<>(formals.values());
    }

    public void setParent(TypeSymbol parent) {
        this.parent = parent;
    }

    public TypeSymbol getReturnType() {
        return returnType;
    }

    public void setReturnType(TypeSymbol returnType) {
        this.returnType = returnType;
    }

    @Override
    public boolean add(Symbol sym) {
        if (!(sym instanceof IdSymbol)) {
            return false;
        }

        String symbolName = sym.getName();
        if (formals.containsKey(symbolName)) {
            return false;
        }

        formals.put(symbolName, (IdSymbol)sym);

        return true;
    }

    @Override
    public Symbol lookup(String str) {
        IdSymbol symbol = formals.get(str);
        if (symbol != null) {
            return symbol;
        }

        return parent.lookup(str);
    }

    @Override
    public Scope getParent() {
        return parent;
    }

}