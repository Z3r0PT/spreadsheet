import java.util.regex.Pattern;

/**
 * Classe Verifier
 * @author Tiago Matos, 52888
 */
public class Verifier {

    /**
     * Construtor da classe Verifier, vazio pois nao existe vantagem em executar os metodos no construtor
     */
    public Verifier(){

    }

    /**
     * Metodo que verifica se uma string e um numero com parte decimal(Double)
     * @param s String a verificar
     * @return True se for Double, False se nao for
     */
    public boolean isDouble(String s){
        String decimalPattern = "([0-9]*)\\.([0-9]*)";
        boolean match = Pattern.matches(decimalPattern, s);
        if(match == true){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Metodo que verifica se uma string e um numero interio(Integer)
     * @param s String a verificar
     * @return True se for Integer, False se nao for
     */
    public boolean isInteger(String s){
        if(s.matches("-?\\d+")){
            return true;
        }else{
            return false;
        }
    }
}
