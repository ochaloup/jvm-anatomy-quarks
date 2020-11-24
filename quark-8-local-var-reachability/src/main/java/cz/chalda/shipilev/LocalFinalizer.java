package cz.chalda.shipilev;

/**
 * https://shipilev.net/jvm/anatomy-quarks/8-local-var-reachability/
 */
public class LocalFinalizer {
    private static volatile boolean flag;

    public static void main(String... args) throws InterruptedException {
        System.out.println("Pass 1");
        arm();
        flag = true;
        pass();

        System.out.println("Wait for pass 1 finalization");
        Thread.sleep(10000);

        System.out.println("Pass 2");
        flag = true;
        pass();
    }

    public static void arm() {
        new Thread(() -> {
            try {
                Thread.sleep(5000);
                flag = false;
            } catch (Throwable t) {}
        }).start();
    }

    public static void pass() {
        MyHook h1 = new MyHook();
        MyHook h2 = new MyHook();

        while (flag) {
            // spin
        }

        h1.log();
    }

    public static class MyHook {
        public MyHook() {
            System.out.println("Created " + this);
        }

        public void log() {
            System.out.println("Alive " + this);
        }

        @Override
        protected void finalize() throws Throwable {
            System.out.println("Finalized " + this);
        }
    }
}