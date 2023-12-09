public class Main {
    static  int MAXVALUE = 20;
    private static Object lock = new Object();
    private static boolean isEvenTurn = true;
    public static void main(String[] args) {

        Thread evenThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 2; i <= MAXVALUE;i+=2){
                    synchronized (lock) {
                        while (!isEvenTurn){
                            try{
                                lock.wait();
                            } catch (InterruptedException e){
                                throw new RuntimeException(e);
                            }
                        }
                        System.out.println("Первый поток: "+i);
                        isEvenTurn = false;
                        lock.notify();
                    }
                }
            }
        });
        Thread oddThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 1; i <= MAXVALUE;i+=2){
                    synchronized (lock) {
                        while (isEvenTurn){
                            try{
                                lock.wait();
                            } catch (InterruptedException e){
                                throw new RuntimeException(e);
                            }
                        }
                        System.out.println("Второй поток: "+i);
                        isEvenTurn = true;
                        lock.notify();
                    }
                }

            }
        });
        evenThread.start();
        oddThread.start();
    }
}