import java.util.*;

/**
 * Classe que executa as funcionalidades da spreadsheet
 * @author Tiago Matos, 52888
 */
public class Matrix {

    private LinkedHashMap<String, String> order = new LinkedHashMap<>(); //Guarda ordem pela qual as celulas com =SUM são introduzidas
    private TreeMap<String, ArrayList<String>> sheet = new TreeMap<>(); //Estrutura da spreadsheet
    private ArrayList<String> elements = new ArrayList<String>(); //Elementos de uma string, convertidos para uma lista de strings
    private String sumValue; //Valor resultante da operacao de soma
    private ArrayList<String> cellstoPrint = new ArrayList<String>(); //Celulas a fazer print, gravadas por ordem de insercao
    private ArrayList<String> addtoCell = new ArrayList<String>(); //Celulas a adicionar, gravadas por ordem de insercao
    private ArrayList<String> deletes = new ArrayList<String>(); //Linhas, colunas e celulas a executar pelo comando delete
    private ArrayList<String> Prints = new ArrayList<String>(); //Celulas a fazer print, em que os valores todos de uma celula estao convertidos numa string
    private ArrayList<String> Commands = new ArrayList<String>(); //Lista de todos os comandos a ser executados pela sheet, desde adicionar a apagar

    /**
     * Construtor da classe Matrix
     * @param a Lista de todos os elementos de uma linha de input
     */
    public Matrix(ArrayList<String> a){
        for(int i = 0; i < a.size(); i++) {
            elements = splitString(a.get(i));
            Reference r = new Reference(elements.get(0));
            if(r.getCommand().equals("d*")){
                Commands.add("d*");
                ArrayList<String> b = new ArrayList<>();
                b.add(elements.get(0));
                b.add(elements.get(1));
                deletes.add(converttoString(b));
            }else if((!r.getCommand().equals("d*") && !r.getCommand().equals("p*")) && elements.size() == 1){
                Commands.add("s*");
                cellstoPrint.add(elements.get(0));
            }else if(r.getCommand().equals("p*")) {
                Commands.add("p*");
            }else if((!r.getCommand().equals("d*") && !r.getCommand().equals("p*")) && elements.size() == 2) {
                    Commands.add("a*");
                    addtoCell.add(a.get(i));
                    if (elements.contains("=SUM")){
                        order.put(elements.get(0), elements.get(0));
                    }
            }
            else{
                Commands.add("a*");
                addtoCell.add(a.get(i));
                if (elements.contains("=SUM")){
                    order.put(elements.get(0), elements.get(0));
                }
            }
        }
    }

    /**
     * Metodo que separa uma string numa lista de strings, separando as pelo caracter "space"
     * @param s String a separar
     * @return Lista de todas separadas pelo espaço
     */
    public ArrayList<String> splitString(String s){
        String[] splited = s.split(" ");
        ArrayList<String> a =  new ArrayList<>(Arrays.asList(splited));
        return a;
    }

    /**
     * Metodo que adiciona celulas à spreadsheet e atualiza celulas existentes
     * @param a Linha com a referencia e o conteudo a adicionar, em que o primeiro elemento da lista e a referencia de celula
     */
    private void addCell(ArrayList<String> a){
        Verifier v = new Verifier();
        ArrayList<String> b = splitString(a.get(0));
        if(!b.contains("=SUM")){
            if(!v.isInteger(b.get(1)) && !v.isDouble(b.get(1))){
                ArrayList<String> h = new ArrayList<>();
                h.addAll(b);
                h.remove(1);
                h.addAll(sheet.get(b.get(1)));
                h.remove((h.size() - 1));
                sheet.put(b.get(0), h);
                a.remove(0);
            }else {
                ArrayList<String> beta = new ArrayList<>();
                beta.addAll(b);
                beta.add(b.get(1));
                sheet.put(b.get(0), beta);
                a.remove(0);
            }
        }else {
            sumValue = operation(b);
            if (sumValue.equals("0.0")) {
                sumValue = Integer.toString(0);
            }
            b.add(sumValue);
            sheet.put(b.get(0), b);
            a.remove(0);
        }
    }

    /**
     * Metodo que verifica se é uma soma binaria, verificando em 2 strings se existe um =SUM e assumindo que as strings sao introduzidas por ordem
     * @param a Primeira string a verificar
     * @param b Segunda string a verificar
     * @return true se for uma soma binaria, false se nao for binaria
     */
    private  boolean isBinary(String a, String b){
        if(!a.equals("=SUM") && !b.equals("=SUM")){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Metodo que executa as somas
     * @param a Lista correspondente a uma linha de input
     * @return Valor referente a soma
     */
    private String operation(ArrayList<String> a){
        ArrayList<String> numbers = new ArrayList<String>();
        ArrayList<Integer> positions = new ArrayList<Integer>();
        ArrayList<String> sumer = new ArrayList<String>();
        for (int i = 1; i < a.size(); i++){
            if(a.get(i).equals("=SUM")){
                positions.add(i);
            }else{
                numbers.add(Integer.toString(i));
            }
        }
        for (int j = 0; j < positions.size(); j++){
            ArrayList<String> values = new ArrayList<String>();
            if(positions.get(j) + 2 > a.size() - 1){
                Unary u = new Unary(a, a.get(positions.get(j) + 1), sheet);
                String s = u.getResult();
                values.add(s);
            }
            else if(isBinary(a.get(positions.get(j) + 1), a.get(positions.get(j) + 2))){
                Binary b = new Binary(a, a.get(positions.get(j) + 1), a.get(positions.get(j) + 2), sheet);
                String s = b.getResult();
                values.add(s);
            }else{
                Unary u = new Unary(a, a.get(positions.get(j) + 1), sheet);
                String s = u.getResult();
                values.add(s);
            }
            for (int i = 0; i < values.size(); i++){
                if(!values.get(i).equals("0.0") && !values.get(i).equals("0")){
                    sumer.add(values.get(i));
                }
            }
        }
        Unary u = new Unary(a, "0", sheet);
        return u.comutativeSum(sumer, sheet);
    }

    /**
     * Metodo que apaga celulas da spreadsheet
     * @param l Elemento a apagar, este pode ser uma linha, uma coluna ou uma celula
     */
    private void delete(String l){
        ArrayList<String> c = new ArrayList<>();
        ArrayList<String> toRemove = new ArrayList<>();
        ArrayList<String> allZero = new ArrayList<>();
        ArrayList<String> swiper = new ArrayList<>();
        Set set = sheet.entrySet();

        Iterator i = set.iterator();
        while(i.hasNext()) {
            Map.Entry me = (Map.Entry) i.next();
            Reference r = new Reference(me.getKey().toString());
            Reference r1 = new Reference(l);
            r.locator(me.getKey().toString());
            if (r.getRow().equals(r1.getRow()) && r.getCol().equals(r1.getCol())) {
                Set set1 = sheet.entrySet();

                Iterator i1 = set1.iterator();
                while (i1.hasNext()) {
                    Map.Entry me1 = (Map.Entry) i1.next();
                    if(me.getKey().toString().equals(l)){
                        i1.next();
                    }
                    ArrayList<String> b = new ArrayList<>();
                    b.addAll(sheet.get(me1.getKey().toString()));
                    b.remove(0);
                    boolean check = false;
                    for (int g = 0; g < b.size(); g++){
                        if(b.get(g).equals(l)){
                            check = true;
                        }
                    }
                        if (!check) {
                            sheet.remove(l);
                        } else {
                            if (b.size() == 2) {
                                ArrayList<String> f = new ArrayList<String>();
                                f.add(me.getKey().toString());
                                f.add("0");
                                sheet.put(me.getKey().toString(), f);
                                b.clear();
                            } else {
                                ArrayList<String> f = new ArrayList<String>();
                                f.add(me.getKey().toString());
                                f.add("0");
                                f.add("0");
                                sheet.put(me.getKey().toString(), f);
                                b.clear();
                            }
                        }
                }
            } else {
                if (r.getRow().equals(l) || r.getCol().equals(l)) {
                    ArrayList<String> b = new ArrayList<>();
                    String key = me.getKey().toString();
                    b.addAll(sheet.get(key));
                    if (b.size() == 2) {
                        ArrayList<String> f = new ArrayList<String>();
                        f.add(me.getKey().toString());
                        f.add("0");
                        sheet.put(me.getKey().toString(), f);
                    } else {
                        ArrayList<String> f = new ArrayList<String>();
                        f.add(me.getKey().toString());
                        f.add("0");
                        f.add("0");
                        sheet.put(me.getKey().toString(), f);
                    }
                }
            }
            Set set2 = sheet.entrySet();
            Iterator i2 = set2.iterator();
            while(i2.hasNext()) {

                Map.Entry me2 = (Map.Entry) i2.next();
                ArrayList<String> f = new ArrayList<>();
                ArrayList<String> f1 = new ArrayList<>();
                String key = me2.getKey().toString();
                f.addAll(sheet.get(key));
                f.remove(0);
                f1 = removeDuplicates(f);
                    if(f1.size() == 1){
                        if(f.get(0).equals("0")){
                            allZero.add(key);
                        }
                    }
            }
        }
        c = removeDuplicates(allZero);
        for (int g = 0; g < c.size(); g++){
            Set set3 = sheet.entrySet();
            String key = c.get(g);
            Iterator i3 = set3.iterator();
            while (i3.hasNext()) {
                Map.Entry me5 = (Map.Entry) i3.next();

                ArrayList<String> b = new ArrayList<>();
                b.addAll(sheet.get(me5.getKey().toString()));
                boolean check = false;
                for (int k = 1; k < b.size(); k++) {
                    if (b.get(k).equals(key)){
                        check = true;
                        swiper.add(key);
                    }
                }
                b.clear();
                if (!check) {
                    toRemove.add(key);
                }
            }
        }
        ArrayList<String> removal = removeDuplicates(toRemove);
        ArrayList<String> mopUp = removeDuplicates(swiper);
        for (int f = 0; f < mopUp.size(); f++){
            for (int m = 0; m < removal.size(); m++){
                if(removal.get(m).equals(mopUp.get(f))){
                    removal.remove(mopUp.get(f));
                }
            }
        }
        for (int r = 0; r < removal.size(); r++){
            sheet.remove(removal.get(r));
        }
        }

    /**
     * Metodo que atualiza os valores da spreadsheet
     */
    private void updateSheet() {
        ArrayList<ArrayList<String>> All = new ArrayList<>();
        Set set1 = order.entrySet();
        Iterator i1 = set1.iterator();
        while (i1.hasNext()) {
            Map.Entry me = (Map.Entry) i1.next();
            String key = me.getKey().toString();
            ArrayList<String> f = new ArrayList<>();
            if (sheet.containsKey(key)) {
                f.addAll(sheet.get(key));
                All.add(f);
            }
        }
        for (int i = 0; i < All.size(); i++){
            ArrayList<String> val = new ArrayList<>();
            if(All.get(i).size() == 3){
                val.addAll(All.get(i));
                sheet.put(val.get(0), val);
            }else {
                val.addAll(All.get(i));
                val.remove(val.size() - 1);
                String s = operation(val);
                if(s.equals("0.0")){
                    s = "0";
                }
                val.add(s);
                sheet.put(val.get(0), val);
            }
        }
    }

    /**
     * Metodo que executa os comandos através da lista de comandos (Commands)
     */
    public void printSheet(){
        for (int j = 0; j < Commands.size(); j++){
            if (Commands.get(j).equals("p*")){
               updateSheet();
                printGrid();
            }else if(Commands.get(j).equals("d*")){
                deleteCommand();
            }else if(Commands.get(j).equals("s*")){
                updateSheet();
                prints(cellstoPrint.get(0));
                System.out.println(Prints.get(0));
                cellstoPrint.remove(0);
                Prints.clear();
            }else if(Commands.get(j).equals("a*")) {
                addCell(addtoCell);
            }
        }
    }

    /**
     * Metodo que executa o comando apagar e remove os comandos de apagar ja utilizados
     */
    private void deleteCommand(){
        Reference r = new Reference(deletes.get(0));
        delete(r.getDesignated());
        deletes.remove(0);
    }

    /**
     * Metodo que imprime todos os valores de uma unica celula
     * @param s Celula a imprimir
     */
    private void prints(String s){
        ArrayList<String> a = new ArrayList<String>();
        a = sheet.get(s);
        StringBuilder sb = new StringBuilder();
        for (String printer : a)
        {
            sb.append(printer);
            sb.append(" ");
        }
        Prints.add(sb.toString().trim());
    }

    /**
     * Metodo que imprime toda a spreadsheet ordenada por numero de linha e cada linha por ordem de numero de letras seguido de ordem alfabetica
     */
    private void printGrid(){
        ArrayList<String> sorted = new ArrayList<>();
        Verifier v = new Verifier();
        Set set = sheet.entrySet();
        ArrayList<String> elm = new ArrayList<String>();
        ArrayList<Integer> numbers = new ArrayList<Integer>();
        Iterator i = set.iterator();
        while(i.hasNext()){
            Map.Entry me = (Map.Entry) i.next();
            Reference r = new Reference(me.getKey().toString());
            numbers.add(Integer.parseInt(r.getRow()));
        }
        int stop = Collections.max(numbers);

        for (int k = 1; k < (stop + 1); k++) {
            Set set1 = sheet.entrySet();
            Iterator i1 = set1.iterator();
            while (i1.hasNext()) {
                Map.Entry me = (Map.Entry) i1.next();
                String key = me.getKey().toString();
                Reference r = new Reference(key);
                int ref = Integer.parseInt(r.getRow());
                if (ref == k) {
                    ArrayList<String> f = new ArrayList<String>();
                    f.add(key);
                    elm.addAll(f);
                }
            }
            Collections.sort(elm, Comparator.comparing(String::length));
            ArrayList<String> lines = new ArrayList<>();
            for (int j = 0; j < elm.size(); j++){
                String s = converttoString(sheet.get(elm.get(j))).trim();
                lines.add(s);
            }

            String s = converttoString(lines);
            if (!isEmpty(s)) {
                sorted.add(s);
            }
            elm.clear();
            }

        for (int f = 0; f < sorted.size(); f++){
            System.out.println(sorted.get(f).trim());
        }
    }

    /**
     * Metodo que verifica se uma string e vazia
     * @param s String a verificar
     * @return True se a string for vazia, false se nao for
     */
    private boolean isEmpty(String s){
        if(s.length() == 0){
            return true;
        }else{
            return false;
        }
    }

    /**
     * Metodo que transforma uma lista de strings numa unica string
     * @param a Lista a transformar numa string
     * @return String resultante da transformacao da lista
     */
    private String converttoString(ArrayList<String> a){
        StringBuilder sb = new StringBuilder();
        for (String printer : a)
        {
            sb.append(printer);
            sb.append(" ");
        }
        return sb.toString();
    }

    /**
     * Metodo que remove as string duplicadas numa lista
     * @param a Lista a verificar
     * @return Lista sem strings duplicadas
     */
   private ArrayList<String> removeDuplicates(ArrayList<String> a){
       ArrayList<String> al = new ArrayList<>();
       Set<String> hs = new HashSet<>();
       hs.addAll(a);
       al.addAll(hs);
       return al;
   }
}
