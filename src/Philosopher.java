public class Philosopher implements Runnable {
    private String name;
    private Thread thread;
    private volatile boolean running = true;
    private ChopStick chopStickRight, chopStickLeft;
    private int eatCount = 0;
    private int eatPriority = 5;

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
    private static void eatDelay(String errMsg) {
        int max = 200;
        int min = 150;
        int range = max - min + 1;
        double sleepTime = (Math.random() * range) + min;
        try {
            Thread.sleep((long) sleepTime);
        } catch (InterruptedException e) {
            System.err.println(errMsg);
        }
    }

    private static void thinkDelay(String errMsg) {
        int max = 100;
        int min = 50;
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

    public synchronized void grabChopSticks() {
        while (running && (!chopStickLeft.isAvailable() || !chopStickRight.isAvailable())) {
            try {
                if(thread.getPriority()<8) {
                    eatPriority+=5;
                    thread.setPriority(eatPriority);
                    System.out.println(thread.getPriority());
                }
                System.out.println(thread.getPriority());
                wait();
            } catch (InterruptedException e) {
                System.err.println("Waiting interrupted while waiting for right chop stick to become available");
            }
        }
        chopStickRight.acquire();
        chopStickLeft.acquire();
        if(thread.getPriority()>9){
            eatPriority-=5;
            thread.setPriority(eatPriority);
            System.out.println(thread.getPriority());
        }
        System.out.println(thread.getPriority());
    }

    /**
     * Pauses the thread for 500 ms to simulate a philosopher in the "eating" process.
     */
    public void eatRice() {
        eatCount++;
        eatDelay("Thread got interrupted while sleeping");
        chopStickRight.release();
        chopStickLeft.release();
        System.out.println("Philosopher " + Thread.currentThread().getName() + " is done eating");
    }

    /**
     *
     */
    public void think() {
        thinkDelay("Thinking error");
        System.out.println("Philosopher " + Thread.currentThread().getName() + " done thinking");
    }

    /**
     *
     */
    public void stopRunning() {
        running = false;
        synchronized (this) {
            notify();
        }
    }

    /**
     *
     */
    @Override
    public void run() {
        while (running) {
            think();
            System.out.println("Philosopher " + name + " done thinking");
            grabChopSticks();
            System.out.println("Philosopher " + name + " picked up chopsticks");
            eatRice();
            System.out.println("Philosopher " + name + " done eating");
        }
        System.out.println("Philosopher " + name + " stopped eating");
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
