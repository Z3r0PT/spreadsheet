import java.util.ArrayList;
import java.util.TreeMap;

/**
 * Classe referente à soma binaria
 * @author Tiago Matos, 52888
 */
public class Binary extends Matrix{

    private String result; /**Valor resultante da soma binaria*/

    /**
     * Contrutor da classe Binary
     * @param a Lista de strings de input
     * @param x String referente ao primeiro valor a somar
     * @param y String referente ao segundo valor a somar
     * @param tree Spreadsheet
     */
    public Binary(ArrayList<String> a, String x, String y, TreeMap<String, ArrayList<String>> tree) {
        super(a);
        result = sum(x, y, tree);
    }

    /**
     * Metodo que soma dois valores
     * @param x Primeiro valor a somar
     * @param y Segundo valor a somar
     * @param tree Speadsheet
     * @return Valor resultante da soma de dois valores
     */
    protected String sum (String x, String y, TreeMap<String, ArrayList<String>> tree){
        Verifier v = new Verifier();
        if (v.isInteger(x) && v.isInteger(y)) {
            int a = (Integer.parseInt(x) + Integer.parseInt(y));
            return Integer.toString(a);
        } else if (v.isDouble(x) && v.isDouble(y)) {
            double b = (Double.parseDouble(x) + Double.parseDouble(y));
            return Double.toString(b);

        } else if (v.isInteger(x) && v.isDouble(y)) {
            double a = (Integer.parseInt(x) + Double.parseDouble(y));
            return Double.toString(a);
        } else if (v.isDouble(x) && v.isInteger(y)) {
            double a = (Double.parseDouble(x) + Integer.parseInt(y));
            return Double.toString(a);
        } else {
            if (v.isInteger(y) && (!v.isInteger(x) && !v.isDouble(x))) {
                ArrayList<String> A = tree.get(x);
                int a = (Integer.parseInt(A.get(A.size() - 1)) + Integer.parseInt(y));
                return Integer.toString(a);
            } else if (v.isInteger(x) && (!v.isInteger(y) && !v.isDouble(y))) {
                ArrayList<String> B = tree.get(y);
                int a = (Integer.parseInt(x) + Integer.parseInt(B.get(B.size() - 1)));
                return Integer.toString(a);
            } else if (v.isDouble(y) && (!v.isInteger(x) && !v.isDouble(x))) {
                ArrayList<String> A = tree.get(x);
                double a = (Double.parseDouble(A.get(A.size() - 1)) + Double.parseDouble(y));
                return Double.toString(a);
            } else if (v.isDouble(x) && (!v.isInteger(y) && !v.isDouble(y))) {
                ArrayList<String> B = tree.get(y);
                double a = (Double.parseDouble(B.get(B.size() - 1)) + Double.parseDouble(x));
                return Double.toString(a);
            } else {
                ArrayList<String> A = tree.get(x);
                ArrayList<String> B = tree.get(y);
                if (v.isInteger(A.get(A.size() - 1)) && v.isInteger(B.get(B.size() - 1))){
                    int a = (Integer.parseInt(A.get(A.size() - 1)) + Integer.parseInt(B.get(B.size() - 1)));
                    return Integer.toString(a);
                } else if(v.isDouble(A.get(A.size() - 1)) && v.isDouble(B.get(B.size() - 1))){
                    double b = (Double.parseDouble(A.get(A.size() - 1)) + Double.parseDouble(B.get(B.size() - 1)));
                    return Double.toString(b);
                }else if(v.isInteger(A.get(A.size() - 1)) && v.isDouble(B.get(B.size() - 1))){
                    double b = (Integer.parseInt(A.get(A.size() - 1)) + Double.parseDouble(B.get(B.size() - 1)));
                    return Double.toString(b);
                }else{
                    double b = (Double.parseDouble(A.get(A.size() - 1)) + Integer.parseInt(B.get(B.size() - 1)));
                    return Double.toString(b);
                }
            }
        }
    }

    /**
     * Metodo que retorna o resultado da soma de dois valores
     * @return Valor resultante da soma de dois valores
     */
    public String getResult() {
        return result;
    }
}
