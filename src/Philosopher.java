/**
 * Class for representing a philosopher in the Dining Philosophers Solution. Each philosopher is run on a separate thread,
 * which is started immediately upon the philosopher's initialization. Each philosopher is assigned both a left and right
 * chopstick, both of which must be acquired in order for the philosopher to eat. The philosopher starts by thinking for
 * a randomly chosen amount of time, simulated by having the thread sleep for the designated amount of time. Once the
 * philosopher has finished thinking, it attempts to pick up both chopsticks to eat. If either is unavailable, any acquired
 * chopstick is released before the philosopher returns to a thinking state before attempting to eat again.
 * <p>
 * Author: Dustin Gardner, Ryan Johnson
 */
public class Philosopher implements Runnable {
    private final Thread thread;
    private volatile boolean running;
    private ChopStick chopStickRight, chopStickLeft;
    private int eatCount;
    private float totalEatTime;
    private float totalThinkTime;

    /**
     * Initializes the philosopher, creating and starting a new thread for the philosopher to run on.
     * @param name String containing the name assigned to the new philosopher thread
     */
    public Philosopher(String name) {
        thread = new Thread(this, name);
        this.running = true;
        this.eatCount = 0;
        this.totalEatTime = 0;
        this.totalThinkTime = 0;
        thread.start();
    }

    /**
     * Makes the philosopher thread sleep for a randomly selected amount of time between 20-200 ms. This is used to
     * simulate the time it takes for a philosopher to think and eat. Each time a philosopher thinks or eats, a random
     * time is assigned to the process.
     * @param errMsg String containing the message displayed if there is an error while the thread is sleeping
     * @return float specifying the number of milliseconds the thread sleeps for
     */
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
     * @param chopStickRight ChopStick object placed on the philosopher's right-hand side
     * @param chopStickLeft  ChopStick object placed on the philosopher's left-hand side
     */
    public void assignChopSticks(ChopStick chopStickRight, ChopStick chopStickLeft) {
        this.chopStickLeft = chopStickLeft;
        this.chopStickRight = chopStickRight;
    }

    /**
     * Attempts to grab both the left and right chopsticks. If both chopsticks are available, the philosopher acquires
     * them and enters the eating process. If either chopstick is already acquired by another philosopher, the philosopher
     * releases any chopsticks just acquired to prevent deadlock, and then returns to the think state.
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
     * Pauses the thread for a random amount of time between 20-200 ms to simulate a philosopher in the "eating" process.
     * Once the philosopher has finished eating, both chopsticks are released.
     */
    public void eatRice() {
        float eatTime = delay("Thread got interrupted while eating");
        chopStickRight.release();
        chopStickLeft.release();
        totalEatTime += eatTime;
        eatCount++;
    }

    /**
     * Pauses the thread for a random amount of time between 20-200 ms to simulate a philosopher in the "thinking" process.
     */
    public void think() {
        float thinkTime = delay("Thinking error");
        totalThinkTime += thinkTime;
    }

    /**
     * Signals that the thread should begin to shut down.
     */
    public void stopRunning() {
        running = false;
    }

    /**
     * Instructs the thread to continue the thinking/grabbing/eating process until the shutdown boolean is switched.
     */
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
     * @return Thread object on which this philosopher running
     */
    public Thread getThread() {
        return thread;
    }

    /**
     * Returns the number of times the philosopher has been allowed to eat.
     * @return int specifying the number of times the philosopher has been allowed to eat
     */
    public int getEatCount() {
        return eatCount;
    }

    /**
     * Returns the chopstick on the philosopher's left-hand side.
     * @return ChopStick object on the philosopher's left-hand side
     */
    public ChopStick getChopStickLeft() {
        return chopStickLeft;
    }

    /**
     * Returns the chopstick on the philosopher's right-hand side.
     * @return ChopStick object on the philosopher's right-hand side
     */
    public ChopStick getChopStickRight() {
        return chopStickRight;
    }

    /**
     * Returns the total number of milliseconds the philosopher has been in the "eating" process. This is used when
     * displaying statistics after all philosopher threads have stopped running.
     * @return float specifying the total number of milliseconds the philosopher has been in the "eating" process
     */
    public float getTotalEatTime() {
        return totalEatTime;
    }

    /**
     * Returns the total number of milliseconds the philosopher has been in the "thinking" process. This is used when
     * displaying statistics after all philosopher threads have stopped running.
     * @return float specifying the total number of milliseconds the philosopher has been in the "eating" process
     */
    public float getTotalThinkTime() {
        return totalThinkTime;
    }
}
