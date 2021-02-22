import java.util.ArrayList;
import java.util.Arrays;

/**
 * Classe que permite identificar a linha e coluna de uma celula ou o comando e onde o executar
 * @author Tiago Matos, 52888
 */
public class Reference {
    private String col; //coluna
    private String row; //linha
    private String designated; //onde executar o comando
    private String command; //comando a executar

    /**
     * Contrutor da classe Reference
     * @param s String referente a uma celula ou comando a executar
     */
    public Reference(String s){
        selector(s);
    }


    /**
     * Metodo que divide uma referencia de celula em linha(row) e coluna(coluna)
     * @param s Referencia de celula
     */
    public void locator(String s){
        ArrayList<String> a = new ArrayList<>();
        String[] part = s.split("(?<=\\D)(?=\\d)");
        col = part[0];
        row = part[1];
        a.addAll(Arrays.asList(part));
        //return a;
    }

    /**
     * Metodo que divide um comando de apagar, localizando assim onde o comando deve ser executado
     * @param s Comando completo (ex: d* 2)
     */
    public void deleteLocator(String s){
        ArrayList<String> a = new ArrayList<>();
        String[] part = s.split(" ");
        command = part[0];
        designated = part[1];
        a.addAll(Arrays.asList(part));
        //return a;
    }

    /**
     * Metodo que seleciona se é uma referencia de celula ou um comando
     * @param s String referente ao que se quer distinguir
     * @throws ArrayIndexOutOfBoundsException Lança excecao se nao for um comando de apagar
     */
    public void selector(String s) throws ArrayIndexOutOfBoundsException{
        try {
            try {
                deleteLocator(s);
            } catch (ArrayIndexOutOfBoundsException e) {
                locator(s);
            }
        }catch (ArrayIndexOutOfBoundsException g){

        }
    }

    /**
     * Metodo que retorna a localização da coluna
     * @return Localizacão da coluna
     */
    public String getCol(){
        return col;
    }

    /**
     * Metodo que retorna a localização da linha
     * @return Localização da linha
     */
    public String getRow(){
        return row;
    }

    /**
     * Metodo que retorna onde se deve executar um comando
     * @return Localizacão de onde um comando deve ser utilizado
     */
    public String getDesignated() {
        return designated;
    }

    /**
     * Metodo que retorna o comando
     * @return Tipo de comando a ser executado
     */
    public String getCommand() {
        return command;
    }

}
