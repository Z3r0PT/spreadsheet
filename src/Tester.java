import org.junit.Test;

import java.util.ArrayList;
import java.util.Stack;
import java.util.TreeMap;

import static junit.framework.TestCase.assertEquals;


public class Tester {
    @Test
    public void testBinary(){
        ArrayList<String> a = new ArrayList<>();
        TreeMap<String, ArrayList<String>> t = new TreeMap<>();
        Binary b = new Binary(a, "1", "2", t);
        assertEquals("3", b.sum("1", "2", t));
        assertEquals("9", b.sum("4", "5", t));
        assertEquals("5.0", b.sum("3.0", "2.0", t));
        assertEquals("16.0", b.sum("10.0", "6.0", t));
        assertEquals("23.0", b.sum("20.0", "3.0", t));
        assertEquals("23.4", b.sum("20.1", "3.3", t));
    }

    @Test
    public void testUnary(){
        ArrayList<String> a = new ArrayList<>();
        TreeMap<String, ArrayList<String>> t = new TreeMap<>();
        Unary u = new Unary(a, "test", t);
        a.add("A1");
        a.add("=SUM");
        a.add("1");
        a.add("2");
        a.add("3");
        t.put("A1", a);
        a.clear();
        a.add("A2");
        a.add("2");
        a.add("2");
        t.put("A2", a);
        a.clear();
        a.add("B1");
        a.add("=SUM");
        a.add("3.0");
        a.add("4");
        a.add("7.0");
        t.put("B1", a);
        a.clear();
        a.add("C1");
        a.add("=SUM");
        a.add("A1");
        a.add("A2");
        a.add("5");
        t.put("C1", a);
        a.clear();
        assertEquals("15.0", u.lineSum("1", t));
        assertEquals("5", u.lineSum("A", t));
        assertEquals("2", u.lineSum("2", t));
        assertEquals("7.0", u.lineSum("B", t));
    }

    @Test
    public void testSplitString(){
        ArrayList<String> s = new ArrayList<>();
        Matrix m = new Matrix(s);
        s.add("A1");
        s.add("=SUM");
        s.add("1");
        s.add("2");
        assertEquals(s, m.splitString("A1 =SUM 1 2"));
        s.clear();
        s.add("A2");
        s.add("=SUM");
        s.add("3");
        s.add("6");
        assertEquals(s, m.splitString("A2 =SUM 3 6"));
        s.clear();
        s.add("A2");
        s.add("=SUM");
        s.add("4.0");
        s.add("5.0");
        assertEquals(s, m.splitString("A2 =SUM 4.0 5.0"));
        s.clear();
        s.add("A2");
        s.add("=SUM");
        s.add("A1");
        s.add("6");
        assertEquals(s, m.splitString("A2 =SUM A1 6"));
        s.clear();
        s.add("A2");
        s.add("=SUM");
        s.add("A2");
        s.add("A3");
        assertEquals(s, m.splitString("A2 =SUM A2 A3"));
        s.clear();
        s.add("A2");
        s.add("=SUM");
        s.add("C1");
        s.add("3.0");
        assertEquals(s, m.splitString("A2 =SUM C1 3.0"));
        s.clear();
    }
}
