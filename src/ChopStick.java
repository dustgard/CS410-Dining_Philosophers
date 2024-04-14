/**
 * Class for representing the chopsticks required for a philosopher to eat. Two chopsticks are assigned to each philosopher,
 * one on the left and another on the right of their person. In order to eat, the philosopher must gain access to both
 * of these chopsticks, which may only be used by a single philosopher thread at a time.
 * <p>
 * Author: Ryan Johnson, Dustin Gardner
 */
public class ChopStick {
    private final int name;
    private volatile boolean lock;
    private int chopStickPickUpCount;
    private String chopStickOwner;

    /**
     * Initializes the chopstick, ensuring the chopstick is unlocked and setting the name of the chopstick.
     */
    public ChopStick(int name) {
        this.lock = false;
        this.name = name;
        this.chopStickPickUpCount = 0;
        this.chopStickOwner = "";
    }

    /**
     * Changes the lock boolean to true to simulate a philosopher picking up the chopstick. This boolean will be changed
     * back to false once the philosopher has finished eating.
     */
    public synchronized void acquire() {
        if (lock) {
            System.err.printf("Attempt to acquire Chopstick %s, which is already held by a philosopher", name);
        }
        lock = true;
        chopStickOwner = Thread.currentThread().getName();
    }

    /**
     * The release method is used to change the lock boolean to false. This simulates the chopstick being put back down
     * on the table, which allows another philosopher to pick it up.
     */
    public synchronized void release() {
        if (!chopStickOwner.equals(Thread.currentThread().getName())) {
            System.err.printf("Philosopher attempted to release chopstick they don't possess (Chopstick %s)", name);
        }
        if (!lock) {
            System.err.printf("Attempt to release a free chopstick lock (Chopstick %s)", name);
        }
        lock = false;
    }

    /**
     * Returns the number of times the chopstick has been picked up.
     * @return int detailing how many times the chopstick is picked up.
     */
    public int getChopStickPickUpCount() {
        return chopStickPickUpCount;
    }

    /**
     * Increments the number of times the chopstick has been picked up. It is increased every time the chopstick is acquired,
     * regardless of whether the philosopher eats or not.
     */
    public void setChopStickPickUpCount() {
        this.chopStickPickUpCount++;
    }

    /**
     * Returns the current status of the boolean lock for the chopstick. The lock is true if the chopstick is currently
     * held by a philosopher and false if on the table.
     * @return boolean describing whether the chopstick is currently held by a philosopher
     */
    public boolean isLocked() {
        return lock;
    }

    /**
     * Returns the number of the chopstick, used for identification purposes. Each chopstick is assigned a unique name.
     * @return int representing the name of the chopstick, distinguishing it from other chopsticks
     */
    public int getName() {
        return name;
    }
}
