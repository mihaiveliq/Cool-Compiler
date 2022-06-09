package cool.visitors;

import cool.ast.*;
import cool.structures.*;
import org.stringtemplate.v4.*;
import org.stringtemplate.v4.STGroupFile;

import java.util.HashSet;
import java.util.LinkedHashMap;

public class AstCodeGenVisitor implements ASTVisitor<ST> {
    private final LinkedHashMap<String, StringGenObject> stringConsts;
    private final LinkedHashMap<String, IntGenObject> intConsts;
    private int stringConstsCounter = 1;
    private int intConstsCounter = 1;

    private final STGroupFile group;

    public AstCodeGenVisitor() {
        stringConsts = new LinkedHashMap<>();
        intConsts = new LinkedHashMap<>();
        group = new STGroupFile("cgen.stg");
    }

    @Override
    public ST visit(Program program) {
        //final program
        var program_st = group.getInstanceOf("sequence");

        //init
        var program_init = group.getInstanceOf("program_init");
        program_st.add("e", program_init);

        //accepting
        for(var programClass : program.programClasses) {

        }

        //preparing to add string constants
        var str_consts = group.getInstanceOf("sequence");

        //adding the default
        var defaultStringGenObject = new StringGenObject(TypeSymbol.STRING.tag, "", 0);
        str_consts
                .add("e", defaultStringGenObject.stringyLabel())
                .add("e", defaultStringGenObject.stringyTag())
                .add("e", defaultStringGenObject.stringyEntryDimension())
                .add("e", defaultStringGenObject.stringyDispatchTable())
                .add("e", defaultStringGenObject.stringySth("int_const0"))
                .add("e", defaultStringGenObject.stringyStringName())
                .add("e", defaultStringGenObject.stringyAlign());

        //adding the rest
        for (var str_const : stringConsts.values()) {
            str_consts
                    .add("e", str_const.stringyLabel())
                    .add("e", str_const.stringyTag())
                    .add("e", str_const.stringyEntryDimension())
                    .add("e", str_const.stringyDispatchTable())
                    .add("e", str_const.stringySth(intConsts.get(str_const.stringNameDimension).labelName))
                    .add("e", str_const.stringyStringName())
                    .add("e", str_const.stringyAlign());
        }

        //adding all the string constants to the program
        program_st.add("e", str_consts);

        //preparing to add int constants
        var int_consts = group.getInstanceOf("sequence");

        //adding the default
        var defaultIntGenObject = new IntGenObject(TypeSymbol.INT.tag, "0", 0);
        int_consts
                .add("e", defaultIntGenObject.stringyLabel())
                .add("e", defaultIntGenObject.stringyTag())
                .add("e", defaultIntGenObject.stringyEntryDimension())
                .add("e", defaultIntGenObject.stringyDispatchTable())
                .add("e", defaultIntGenObject.stringyActualNumber());

        // adding the rest
        for (var int_const : intConsts.values()) {
            int_consts
                    .add("e", int_const.stringyLabel())
                    .add("e", int_const.stringyTag())
                    .add("e", int_const.stringyEntryDimension())
                    .add("e", int_const.stringyDispatchTable())
                    .add("e", int_const.stringyActualNumber());
        }

        //adding all the int constants to the program
        program_st.add("e", int_consts);

        //adding bool constants
        var bool_consts = group.getInstanceOf("bool_consts");
        program_st.add("e", bool_consts);

        //adding the name tab
        var name_tab = group.getInstanceOf("sequence");

        name_tab.add("e", "class_nameTab:");

        for (var str_const : stringConsts.values()) {
            name_tab.add("e", "\t.word " + str_const.labelName);
        }

        program_st.add("e", name_tab);

        //adding the obj tab
        var obj_tab = group.getInstanceOf("sequence");

        name_tab.add("e", "class_objTab:");




        return program_st;
    }

    @Override
    public ST visit(ProgramClass programClass) {
        //creating a string constant for the class name
        var stringGenObject = new StringGenObject(programClass.typeSymbol.tag,programClass.name.getText(),stringConstsCounter);
        stringConstsCounter++;
        stringConsts.put(programClass.name.getText(), stringGenObject);
        //



        return null;
    }

    @Override
    public ST visit(Formal formal) {
        return null;
    }

    @Override
    public ST visit(MethodDef methodDef) {
        return null;
    }

    @Override
    public ST visit(AttributeDef attributeDef) {
        return null;
    }

    @Override
    public ST visit(MethodCall methodCall) {
        return null;
    }

    @Override
    public ST visit(ClassMethodCall classMethodCall) {
        return null;
    }

    @Override
    public ST visit(CoolIf coolIf) {
        return null;
    }

    @Override
    public ST visit(CoolWhile coolWhile) {
        return null;
    }

    @Override
    public ST visit(Block block) {
        var seq = group.getInstanceOf("sequence");
        for (var instr : block.instructions) {
            seq.add("e", instr.accept(this));
        }
        return seq;
    }

    @Override
    public ST visit(Local local) {
        return null;
    }

    @Override
    public ST visit(Let let) {
        return null;
    }

    @Override
    public ST visit(CaseBranch caseBranch) {
        return null;
    }

    @Override
    public ST visit(CoolCase coolCase) {
        return null;
    }

    @Override
    public ST visit(NewObject newObject) {
        return null;
    }

    @Override
    public ST visit(NegSign negSign) {
        return null;
    }

    @Override
    public ST visit(CoolIsvoid coolIsvoid) {
        return null;
    }

    @Override
    public ST visit(MultDiv multDiv) {
        return null;
    }

    @Override
    public ST visit(PlusMinus plusMinus) {
        if (plusMinus.token.getText().equals("+")) {
            return group.getInstanceOf("plus")
                    .add("e1", plusMinus.leftExpr.accept(this))
                    .add("e2", plusMinus.rightExpr.accept(this));
        }

        return group.getInstanceOf("minus")
                .add("e1", plusMinus.leftExpr.accept(this))
                .add("e2", plusMinus.rightExpr.accept(this));
    }

    @Override
    public ST visit(Relational relational) {
        return null;
    }

    @Override
    public ST visit(BoolNot boolNot) {
        return null;
    }

    @Override
    public ST visit(Paren paren) {
        return null;
    }

    @Override
    public ST visit(Id id) {
        return null;
    }

    @Override
    public ST visit(CoolInt coolInt) {
        var intGenObject = new IntGenObject(TypeSymbol.INT.tag, coolInt.token.getText(),intConstsCounter);
        intConstsCounter++;
        intConsts.put(coolInt.token.getText(),intGenObject);
        return group.getInstanceOf("literal")
                .add("value", coolInt.token.getText());
    }

    @Override
    public ST visit(CoolString coolString) {
        return null;
    }

    @Override
    public ST visit(CoolTrue coolTrue) {
        return null;
    }

    @Override
    public ST visit(CoolFalse coolFalse) {
        return null;
    }

    @Override
    public ST visit(Assign assign) {
        return null;
    }
}
