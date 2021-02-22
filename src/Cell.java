import java.util.ArrayList;
import java.util.Arrays;


public class Cell {
    private String row;
    private String col;
    private String operation;
    private String value1;
    private String value2;
    private String result;

    public Cell(String s){

    }

    public ArrayList<String> splitString(String s){
        String[] splited = s.split(" ");
        ArrayList<String> a =  new ArrayList<>(Arrays.asList(splited));
        return a;
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public String getCol() {
        return col;
    }

    public void setCol(String col) {
        this.col = col;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
