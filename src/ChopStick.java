/**
 * ChopStick mutex class for the Philosophers class which are acquired in a set before use.
 * The chopsticks are used to simulated eating utensils for each of the philosophers.The class, which is based off of
 * a simple mutex, ensures that there is only one thread at a time that can access the methods.
 * Author: Ryan Johnson, Dustin Gardner
 */
public class ChopStick {
    private volatile boolean lock;
    private int name;
    private int chopStickPickUpCount = 0;
    private String chopStickOwner = "";

    /**
     * Initializes the ChopStick and setting boolean lock to false allowing the first thread to access the methods.
     */
    public ChopStick() {
        lock = false;
    }

    public ChopStick(int name) {
        this.name = name;
        lock = false;
    }

    /**
     * The acquire method is a thread safe method used to change the lock boolean to true effectively causing any other
     * thread trying to acquire as well to enter a wait() state. This simulates that the Chopstick is being held
     * by a Philosopher until released.
     */
    public synchronized void acquire() {
        lock = true;
        chopStickOwner = Thread.currentThread().getName();
    }

    /**
     * The release method is a thread safe method used to change the lock boolean to false,
     * effectively causing any other thread trying to acquire as well to enter a wait() state.
     * This simulates that the Chopstick is being put back down on the table and allowing another
     * Philosopher to pick it up.Once the ChopStick is released, it wakes up the other thread waiting to
     * acquire the ChopStick in an attempt to eat with it.
     */
    public synchronized void release() {
        if (chopStickOwner.equals(Thread.currentThread().getName())) {
            lock = false;
        }
    }

    /**
     * The method is used to keep data to supply user with statistic on how many times the ChopStick is
     * picked up.
     *
     * @return and int of how many times the ChopStick is picked up. It is increased everytime regardless if the
     * Philosopher eats or not.
     */
    public int getChopStickPickUpCount() {
        return chopStickPickUpCount;
    }

    public void setChopStickPickUpCount() {
        this.chopStickPickUpCount++;
    }

    public boolean isLocked() {
        return lock;
    }

    public int getName() {
        return name;
    }
}
