import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.stream.IntStream;

public class Car implements Runnable {
    private static int CARS_COUNT;
    private final CyclicBarrier waitReady;
    private final CyclicBarrier finish;
    private final Semaphore tunnel;

    static {
        CARS_COUNT = 0;
    }

    private final Race race;
    private final int speed;
    private final String name;

    public Car(Race race, int speed, CyclicBarrier waitReady, CyclicBarrier finish, Semaphore tunnel) {
        this.race = race;
        this.waitReady = waitReady;
        this.finish = finish;
        this.tunnel = tunnel;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int) (Math.random() * 800));
            System.out.println(this.name + " готов");
            waitReady.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        IntStream.range(0, race.getStages().size())
                .forEach(i -> race.getStages().get(i).go(this, tunnel));
        try {
            finish.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
    }
}
