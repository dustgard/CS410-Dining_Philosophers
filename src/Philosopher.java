public class Philosopher implements Runnable {
    public static final long THINKING_TIME = 50;
    public static final long EATING_TIME = 50;
    private String name;
    private Thread thread;
    private volatile boolean running = true;
    private ChopStick chopStickRight, chopStickLeft;

    public Philosopher() {
    }

    public Philosopher(String name) {
        this.name = name;
        thread = new Thread(this, name);
        thread.start();
    }

    private static void delay(long time, String errMsg) {
        long sleepTime = Math.max(1, time);
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
            System.err.println(errMsg);
        }
    }

    /**
     * Assigns which chopstick objects are at the philosopher's left and right-hand sides. Both of these chopsticks must
     * be acquired for a philosopher to eat.
     *
     * @param chopStickRight ChopStick object placed on the philosopher's right-hand side
     * @param chopStickLeft  ChopStick object placed on the philosopher's right-hand side
     */
    public void assignChopSticks(ChopStick chopStickRight, ChopStick chopStickLeft) {
        this.chopStickLeft = chopStickLeft;
        this.chopStickRight = chopStickRight;
    }

    /**
     * Grabs both the left and right chopsticks once they are both available. Until both chopsticks are available, the
     * philosopher thread waits to eat.
     */
    public synchronized void grabChopSticks() {
        while (!chopStickLeft.isAvailable() || !chopStickRight.isAvailable()) {
            try {
                wait();
            } catch (InterruptedException e) {
                System.err.println("Waiting interrupted while waiting for right chop stick to become available");
            }
        }
        chopStickRight.acquire();
        chopStickLeft.acquire();
        notify();
    }

    /**
     * Pauses the thread for 500 ms to simulate a philosopher in the "eating" process.
     */
    public void eatRice() {
        System.out.printf("Philosopher %s eating rice\n", name);
        delay(EATING_TIME, "Thread got interrupted while sleeping");
        chopStickRight.release();
        chopStickLeft.release();
        System.out.printf("Philosopher %s finished eating\n", name);
    }

    public void think() {
        System.out.printf("Philosopher %s thinking\n", name);
        delay(THINKING_TIME, "Thinking error");
    }

    public void stopRunning() {
        running = false;
        synchronized (this) {
            notify();
        }
    }

    @Override
    public void run() {
        while (running) {
            System.out.println("Running");
            think();
            System.out.printf("Philosopher %s done thinking", name);
            grabChopSticks();
            System.out.printf("Philosopher %s picked up chopsticks", name);
            eatRice();
            System.out.printf("Philosopher %s done eating", name);
        }
        System.out.printf("Philosopher %s stopped", name);
    }

    /**
     * Returns the thread on which the philosopher is running.
     *
     * @return Thread object on which this philosopher running
     */
    public Thread getThread() {
        return thread;
    }
}
