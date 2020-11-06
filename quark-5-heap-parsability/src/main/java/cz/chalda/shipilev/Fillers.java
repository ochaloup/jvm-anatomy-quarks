package cz.chalda.shipilev;

import java.util.*;
import java.util.concurrent.*;

/**
 * From https://shipilev.net/jvm/anatomy-quarks/5-tlabs-and-heap-parsability/.
 */
public class Fillers {
    public static void main(String... args) throws Exception {
        final int TRAKTORISTI = 300;
        CountDownLatch cdl = new CountDownLatch(TRAKTORISTI);
        for (int t = 0 ; t < TRAKTORISTI; t++) {
            new Thread(() -> allocateAndWait(cdl)).start();
        }
        cdl.await();
        List<Object> l = new ArrayList<>();
        new Thread(() -> allocateAndDie(l)).start();
    }

    public static void allocateAndWait(CountDownLatch cdl) {
        Object o = new Object();  // Request a TLAB
        cdl.countDown();
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                break;
            }
        }
        System.out.println(o); // Use the object
    }

    public static void allocateAndDie(Collection<Object> c) {
        while (true) {
            c.add(new Object());
        }
    }
}