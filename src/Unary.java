import java.util.*;

/**
 * Classe referente à soma unaria
 * @author Tiago Matos, 52888
 */
public class Unary extends Matrix{

    private String result; /**Valor resultante da soma unaria*/

    /**
     * Construtor da classe Unary
     * @param a Lista de strings de input
     * @param s String referente a linha ou coluna na qual se quer somar todas as celulas
     * @param tree Spreadsheet
     */
    public Unary(ArrayList<String> a, String s, TreeMap<String, ArrayList<String>> tree) {
        super(a);
        result = lineSum(s, tree);
    }

    /**
     * Metodo que soma todos os valores numericos de uma lista
     * @param a Lista a somar
     * @param tree Spreadsheet
     * @return Valor referente a soma de todos os valores da lista
     */
    protected String comutativeSum(ArrayList<String> a, TreeMap<String, ArrayList<String>> tree){
        ArrayList<String> divided = new ArrayList<String>();
        ArrayList<Integer> ints = new ArrayList<Integer>();
        ArrayList<Double> doubles = new ArrayList<Double>();
        Verifier v = new Verifier();
        ArrayList<String> s = new ArrayList<String>();
        s.addAll(a);
        int result_i = 0;
        double result_d = 0;
        for (int i = 0; i < s.size(); i++){
            if(!s.get(i).equals("=SUM")){
                divided.add(s.get(i));
            }
        }

        for (int j = 0; j < divided.size(); j++){
            if(v.isInteger(divided.get(j))){
                ints.add(Integer.parseInt(divided.get(j)));
            }
            if(v.isDouble(divided.get(j))){
                doubles.add(Double.parseDouble(divided.get(j)));
            }
            if(!v.isInteger(divided.get(j)) && !v.isDouble(divided.get(j))){
                ArrayList<String> h = tree.get(divided.get(j));
                if(v.isInteger(h.get(h.size()-1))){
                    ints.add(Integer.parseInt(h.get(h.size()-1)));
                }
                if(v.isDouble(h.get(h.size()-1))){
                    doubles.add(Double.parseDouble(h.get(h.size()-1)));
                }
            }
        }

        if(ints.size() != 0 && doubles.size() == 0){
            for (int g = 0; g < ints.size(); g++){
                result_i += ints.get(g);
            }
            return Integer.toString(result_i);
        }else if(ints.size() == 0 && doubles.size() != 0){
            for (int k = 0; k < doubles.size(); k++){
                result_d += doubles.get(k);
            }
            return Double.toString(result_d);
        }else{
            for (int g = 0; g < ints.size(); g++){
                result_i += ints.get(g);
            }
            for (int k = 0; k < doubles.size(); k++){
                result_d += doubles.get(k);
            }
            double result2 = (double) result_i;
            double total = result_d + result2;
            return Double.toString(total);
        }
    }

    /**
     * Metodo que soma todos os valores de uma linha ou coluna da spreadsheet
     * @param s String referente à linha ou coluna na qual se quer somar todos os valores
     * @param tree Spreadsheet
     * @return Valor referente a soma de todos os valores da linha ou coluna dada em s
     */
    protected String lineSum(String s, TreeMap<String, ArrayList<String>> tree){
        Set set = tree.entrySet();
        ArrayList<String> allofThem = new ArrayList<String>();
        Iterator i = set.iterator();
        while(i.hasNext()) {
            Map.Entry me = (Map.Entry) i.next();
            Reference r = new Reference(me.getKey().toString());
            r.locator(me.getKey().toString());
            String key = me.getKey().toString();
            if (r.getRow().equals(s) || r.getCol().equals(s)) {
                ArrayList<String> b = new ArrayList<String>();
                b.addAll(tree.get(key));
                allofThem.add(b.get(b.size() - 1));
                b.clear();
            }
        }
        return comutativeSum(allofThem, tree);
    }

    /**
     * Metodo que retorna o resultado da soma de uma linha ou coluna
     * @return Valor referente ao resultado da soma de uma linha ou coluna
     */
    public String getResult() {
        return result;
    }
}
