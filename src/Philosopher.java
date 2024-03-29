public class Philosopher implements Runnable {

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
        while (!chopStickRight.isAvailable() || !chopStickRight.isAvailable()) {
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
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            System.err.println("Thread got interrupted while sleeping");
        }
    }

    public void think() {
    }

    public void stopRunning() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            System.out.println("Running thread" + thread);
        }
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
