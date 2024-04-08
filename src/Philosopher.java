public class Philosopher implements Runnable {
    private String name;
    private Thread thread;
    private volatile boolean running = true;
    private ChopStick chopStickRight, chopStickLeft;
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
        int max = 500;
        int min = 400;
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
     *
     * @param chopStickRight ChopStick object placed on the philosopher's right-hand side
     * @param chopStickLeft  ChopStick object placed on the philosopher's left-hand side
     */
    public void assignChopSticks(ChopStick chopStickRight, ChopStick chopStickLeft) {
        this.chopStickLeft = chopStickLeft;
        this.chopStickRight = chopStickRight;
    }

    /**
     * Grabs both the left and right chopsticks once they are both available. Until both chopsticks are available, the
     * philosopher thread waits to eat.
     */
    public void grabChopSticks() throws InterruptedException {
        if (!chopStickRight.pickedUp() && !chopStickLeft.pickedUp()) {
            chopStickRight.acquire();
            if (!chopStickLeft.pickedUp()) {
                chopStickLeft.acquire();
                System.out.println(Thread.currentThread().getName() + " Success");
            } else {
                System.out.println("Possible Deadlock " + Thread.currentThread().getName());
                chopStickLeft.release();
                chopStickRight.release();
                think();
            }
        }
        else {
            System.out.println(Thread.currentThread().getName() + " Chopsticks are taken can not eat back to thinking");
            chopStickRight.release();
            chopStickLeft.release();
            think();
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
    }

    /**
     *
     */
    @Override
    public void run() {
        while (running) {
            think();
            try {
                grabChopSticks();
            } catch (InterruptedException e) {
            }
                eatRice();
        }
        System.out.println("Philosopher " + Thread.currentThread().getName() + " is no longer hungry");
    }

    /**
     * Returns the thread on which the philosopher is running.
     *
     * @return Thread object on which this philosopher running
     */
    public Thread getThread() {
        return thread;
    }

    public int getEatCount() {
        return eatCount;
    }

    public ChopStick getChopStickRight() {
        return chopStickRight;
    }

    public ChopStick getChopStickLeft() {
        return chopStickLeft;
    }

    public String getName() {
        return name;
    }
}
