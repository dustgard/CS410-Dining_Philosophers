public class Philosopher implements Runnable {
    private Thread thread;
    private volatile boolean running = true;
    private ChopStick chopStickRight, chopStickLeft;
    private int eatCount = 0;
    private float totalEatTime = 0;
    private float totalThinkTime = 0;

    public Philosopher() {
    }

    public Philosopher(String name) {
        thread = new Thread(this, name);
        thread.start();
    }

    private float delay(String errMsg) {
        int max = 200;
        int min = 20;
        int range = max - min + 1;
        double sleepTime = (Math.random() * range) + min;
        try {
            Thread.sleep((long) sleepTime);
        } catch (InterruptedException e) {
            System.err.println(errMsg);
        }
        return (float) sleepTime;
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
    public boolean grabChopsticks() {
        if (!chopStickLeft.isLocked() && !chopStickRight.isLocked()) {
            chopStickLeft.acquire();
            if (!chopStickRight.isLocked()) {
                chopStickRight.acquire();
                chopStickRight.setChopStickPickUpCount();
                chopStickLeft.setChopStickPickUpCount();
                return true;
            } else {
                chopStickLeft.release();
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * Pauses the thread for 500 ms to simulate a philosopher in the "eating" process.
     */
    public void eatRice() {
        float eatTime = delay("Thread got interrupted while eating");
        totalEatTime += eatTime;
        chopStickRight.release();
        chopStickLeft.release();
        eatCount++;
    }

    public void think() {
        float thinkTime = delay("Thinking error");
        totalThinkTime += thinkTime;
    }

    public void stopRunning() {
        running = false;
    }

    @Override
    public void run() {
        while (running) {
            think();
            if (grabChopsticks()) {
                eatRice();
            }
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

    public int getEatCount() {
        return eatCount;
    }

    public ChopStick getChopStickLeft() {
        return chopStickLeft;
    }

    public ChopStick getChopStickRight() {
        return chopStickRight;
    }

    public float getTotalEatTime() {
        return totalEatTime;
    }

    public float getTotalThinkTime() {
        return totalThinkTime;
    }
}
