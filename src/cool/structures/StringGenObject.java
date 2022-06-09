package cool.structures;

public class StringGenObject {
    public String labelName;
    public int tag;
    public String entryDimension;
    public String dispatchTable;
    public String stringNameDimension;
    public String stringName;
    public String align;

    public StringGenObject(int tag, String stringName, int number) {
        var len = stringName.length();

        //0.label
        labelName = "str_const" + number;
        //1.tag
        this.tag = tag;
        //2.entry dimension in bytes
        entryDimension = "" + (len / 4) + 5;
        //3.dispatch table
        dispatchTable = "String_dispTab";
        //4.string name dimension
        stringNameDimension = "" + len;;
        //5.string name
        this.stringName = stringName;
        //6.align
        align = "2";
    }

    public String stringyLabel() {
        return labelName + ":";
    }

    public String stringyTag() {
        return "\t.word " + tag;
    }

    public String stringyEntryDimension() {
        return "\t.word " + entryDimension;
    }

    public String stringyDispatchTable() {
        return "\t.word String_dispTab";
    }

    public String stringyStringNameDimension() {
        return stringNameDimension;
    }

    public String stringyStringName() {
        return "\t.asciiz \"" + stringName + "\"";
    }

    public String stringyAlign() {
        return "\t.align 2";
    }

    public String stringySth(String sth) {
        return "\t.word " + sth;
    }
}
