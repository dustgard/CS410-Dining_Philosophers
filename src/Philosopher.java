import java.util.concurrent.Semaphore;

public class Philosopher implements Runnable {
    private String name;
    private Thread thread;
    private volatile boolean running = true;
    private Semaphore chopStickRight, chopStickLeft;
    private int chopStickRightNum = 0;
    private int chopStickLeftNum = 0;
    private int chopStickRightCount = 0;
    private int chopStickLeftCount = 0;
    private int eatCount = 0;

    /**
     *
     */

    public Philosopher() {
    }

    /**
     * @param name
     */
    public Philosopher(String name) {
        this.name = name;
        thread = new Thread(this, name);
        thread.start();
    }

    /**
     * @param errMsg
     */
    private static void delay(String errMsg) {
        int max = 100;
        int min = 40;
        int range = max - min + 1;
        double sleepTime = (Math.random() * range) + min;
        try {
            Thread.sleep((long) sleepTime);
        } catch (InterruptedException e) {
            System.err.println(errMsg);
        }
    }

    /**
     * Assigns which chopstick objects are at the philosopher's left and right-hand sides. Both of these chopsticks must
     * be acquired for a philosopher to eat.
     * @param chopStickRight ChopStick object placed on the philosopher's right-hand side
     * @param chopStickLeft  ChopStick object placed on the philosopher's right-hand side
     */
    public void assignChopSticks(Semaphore chopStickRight,int chopStickRightNum, Semaphore chopStickLeft, int chopStickLeftNum) {
        this.chopStickLeft = chopStickLeft;
        this.chopStickRight = chopStickRight;
        this.chopStickRightNum = chopStickRightNum;
        this.chopStickLeftNum = chopStickLeftNum;
    }

    /**
     * Grabs both the left and right chopsticks once they are both available. Until both chopsticks are available, the
     * philosopher thread waits to eat.
     */
    public void grabChopSticks() {
        try {
            chopStickRight.acquire();
            chopStickRightCount++;
            chopStickLeft.acquire();
            chopStickLeftCount++;
        } catch (InterruptedException e) {
        }

    }

    /**
     * Pauses the thread for 500 ms to simulate a philosopher in the "eating" process.
     */
    public void eatRice() {
        eatCount++;
        delay("Thread got interrupted while sleeping");
        chopStickRight.release();
        chopStickLeft.release();
        System.out.println("Philosopher " + Thread.currentThread().getName() + " is done eating");
    }

    /**
     *
     */
    public void think() {
        delay("Thinking error");
        System.out.println("Philosopher " + Thread.currentThread().getName() + " done thinking");
    }

    /**
     *
     */
    public void stopRunning() {
        running = false;
        synchronized (this) {
           chopStickRight.release();
           chopStickLeft.release();
        }
    }

    /**
     *
     */
    @Override
    public void run() {
        while (running) {
            think();
            grabChopSticks();
            eatRice();
        }
        System.out.println("Philosopher " + Thread.currentThread().getName() + " is no longer hungry");
    }

    /**
     * Returns the thread on which the philosopher is running.
     * @return Thread object on which this philosopher running
     */
    public Thread getThread() {
        return thread;
    }

    public int getEatCount() {
        return eatCount;
    }

    public int getChopStickRightCount() {
        return chopStickRightCount;
    }

    public int getChopStickLeftCount() {
        return chopStickLeftCount;
    }

    public int getChopStickRightNum() {
        return chopStickRightNum;
    }

    public int getChopStickLeftNum() {
        return chopStickLeftNum;
    }
}
