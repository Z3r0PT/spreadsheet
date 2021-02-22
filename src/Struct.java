import java.util.ArrayList;

public class Struct {
    private String S;
    private String O;
    private String V1;
    private String V2;

    public Struct(String s, String o, String v1, String v2){
        S = s;
        O = o;
        V1 = v1;
        V2 = v2;
    }

    public String getS(){
        return S;
    }

    public String getO(){
        return O;
    }

    public String getV1() {
        return V1;
    }

    public String getV2() {
        return V2;
    }
}
