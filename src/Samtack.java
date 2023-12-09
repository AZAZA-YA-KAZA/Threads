public class Samtack {
    static int MAXVALUE = 20;
    private static Object lock = new Object();
    private static boolean isEvenTurn = true;

    public static void main(String[] args) {
        Thread oneThread = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    if (!isEvenTurn) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    System.out.println("1 поток: Эхх");
                    isEvenTurn = false;
                    lock.notify();
                }
            }
        });
        Thread twoThread = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (lock) {
                    if (isEvenTurn) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            System.out.println("2 поток: Ура");
                            throw new RuntimeException(e);
                        }
                    }
                    isEvenTurn = true;
                    lock.notify();
                }
            }
        });
        oneThread.start();
        twoThread.start();
    }
}
