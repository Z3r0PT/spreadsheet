import java.util.ArrayList;
import java.util.Scanner;

/**
 * Classe Main
 * @author Tiago Matos, 52888
 */
public class Main {
    public static void main(String[] args) {
        ArrayList<String> a = new ArrayList<>();
        Scanner sc = new Scanner(System.in);
        while(sc.hasNext()){
            String s = sc.nextLine();
            a.add(s);
        }
        sc.close();
        Matrix m = new Matrix(a);
        m.printSheet();
    }
}
