package cz.chalda.shipilev;

import java.util.ArrayList;
import java.util.List;

/**
 * https://shipilev.net/jvm/anatomy-quarks/3-gc-design-and-pauses/
 */
public class App 
{
    static List<Object> l;
    public static void main(String... args) {
        l = new ArrayList<>();
        for (int c = 0; c < 100_000_000; c++) {
            l.add(new Object());
            if(c % 10_000_000 == 0) System.out.println("Processing with c count: " + c);
        }
    }
}
