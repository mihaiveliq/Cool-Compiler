package cool.structures;

public class IntGenObject {
    public String labelName;
    public int tag;
    public int entryDimension;
    public String dispatchTable;
    public String actualNumber;

    public IntGenObject(int tag, String actualNumber, int number) {
        //0.label
        labelName = "int_const" + number;
        //1.tag
        this.tag = tag;
        //2.entry dimension in bytes
        entryDimension = 4;
        //3.dispatch table
        dispatchTable = "Int_dispTab";
        //4.actual number
        this.actualNumber = actualNumber;

    }

    public String stringyLabel() {
        return labelName + ":";
    }

    public String stringyTag() {
        return "\t.word " + tag;
    }

    public String stringyEntryDimension() {
        return "\t.word 4";
    }

    public String stringyDispatchTable() {
        return "\t.word Int_dispTab";
    }

    public String stringyActualNumber() {
        return "\t.word " + actualNumber;
    }
}
