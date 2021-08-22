package HW4;

public class Main {
    private final Object waitObj = new Object();
    private volatile char currentLetter = 'A';

    /**
     * Создать три потока, каждый из которых выводит определенную букву (A, B и C) 5 раз (порядок – ABСABСABС). Используйте wait/notify/notifyAll.
     *
     * @param args
     */
    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            Main watNotify = new Main();
            new Thread(watNotify::printA).start();
            new Thread(watNotify::printB).start();
            new Thread(watNotify::printC).start();
        }
    }

    public void printA() {
        synchronized (waitObj) {
            try {
                while (currentLetter != 'A' && currentLetter != 'B') {
                    waitObj.wait();
                }
                System.out.print("A");
                currentLetter = 'B';
                waitObj.notify();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void printB() {
        synchronized (waitObj) {
            try {
                while (currentLetter != 'B' && currentLetter != 'C') {
                    waitObj.wait();
                }
                System.out.print("B");
                currentLetter = 'C';
                waitObj.notify();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void printC() {
        synchronized (waitObj) {
            try {
                while (currentLetter != 'C' && currentLetter != 'A') {
                    waitObj.wait();
                }
                System.out.print("C");
                currentLetter = 'A';
                waitObj.notify();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
