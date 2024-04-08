import java.time.LocalTime;

/**
 * Serves as the controller class for the DiningPhilosophers program. A dining table stores each of the philosophers and
 * their designated chopsticks, starts/stops the eating/thinking process, and displays the stats after the program has
 * finished.
 * <p>
 * Author: Ryan Johnson, Dustin Gardner
 */
public class DiningTable {

    private final int NUM_PHILOSOPHERS = 5;
    private final int NUM_CHOPSTICKS = NUM_PHILOSOPHERS;
    private final ChopStick[] chopsticksList = new ChopStick[NUM_CHOPSTICKS];
    private final int SECONDS_TO_EAT = 3;
    private final Philosopher[] philosophersList = new Philosopher[NUM_PHILOSOPHERS];
    private final LocalTime stoppingTime = LocalTime.now().plusSeconds(SECONDS_TO_EAT);
    private boolean running = false;

    /**
     * Initializes a dining table by creating each of the philosophers at the table.
     */
    public DiningTable() {
        createPhilosophers();
    }

    public static void main(String[] args) {
        DiningTable table = new DiningTable();
        table.eat();
        table.displayStats();
    }

    /**
     * Creates the specified number of philosophers to eat/think at the table. Each of these philosophers, stored in an
     * array, runs on a separate thread.
     */
    public void createPhilosophers() {
        // Create chopsticks
        for (int i = 0; i < NUM_CHOPSTICKS; i++) {
            chopsticksList[i] = new ChopStick(i);
        }
        // Create philosophers and assign chopsticks to their left and right-hand sides
        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            philosophersList[i] = new Philosopher(String.valueOf(i));
            if (i == (NUM_PHILOSOPHERS - 1)) {
                // For the philosopher left of the starting philosopher (the final philosopher), it's right chopstick
                // should be the same chopstick as the first philosopher's left chopstick
                philosophersList[i].assignChopSticks(chopsticksList[0], chopsticksList[i]);
            } else {
                philosophersList[i].assignChopSticks(chopsticksList[i + 1], chopsticksList[i]);
            }
        }
    }

    /**
     * Allows the philosophers to start eating/thinking for the specified time period. The threads are stopped after this
     * designated time period.
     */
    public void eat() {
        startEating();
        // Run the program until the specified amount of time has passed
        while (running) {
            if (LocalTime.now().isAfter(stoppingTime)) {
                stopEating();
            }
        }
    }

    /**
     * Allows the philosophers to begin eating/thinking by setting the running boolean to true.
     */
    public void startEating() {
        running = true;
    }

    /**
     * Signals each of the philosopher threads to stop and waits for them to end.
     */
    public void stopEating() {
        running = false;
        try {
            // Stop the philosophers from eating/thinking after the designated time period
            for (Philosopher p : philosophersList) {
                p.stopRunning();
            }
            for (Philosopher p : philosophersList) {
                p.getThread().join();
            }
        } catch (InterruptedException e) {
            System.err.println("Error while stopping philosophers");
        }
    }

    public void displayStats() {
        System.out.println("---------------------------------------------------");
        System.out.println("Displaying Stats: ");
        System.out.println("---------------------------------------------------");
        int totalPickup0 = 0;
        int totalPickup1 = 0;
        int totalPickup2 = 0;
        int totalPickup3 = 0;
        int totalPickup4 = 0;

        for (Philosopher p : philosophersList) {
            System.out.println("Philosopher: " + p.getThread().getName() + " was able to eat " + p.getEatCount() + " times");
            System.out.println("");
            System.out.println("He used the ChopSticks: ");
            System.out.println("");
            System.out.println("Right chopstick number " + p.getChopStickRightNum());
            System.out.println("Left chopstick number " + p.getChopStickLeftNum());
            System.out.println("");
            System.out.println("----------------------------------------------------");
            int chopRightNumber = p.getChopStickRightNum();
            switch(chopRightNumber){
                case 0:
                    totalPickup0+= p.getChopStickRightCount();
                    break;
                case 1:
                    totalPickup1+= p.getChopStickRightCount();
                    break;
                case 2:
                    totalPickup2+= p.getChopStickRightCount();
                    break;
                case 3:
                    totalPickup3+= p.getChopStickRightCount();
                    break;
                case 4:
                    totalPickup4+= p.getChopStickRightCount();
                    break;
            }
            int chopLeftNumber = p.getChopStickLeftNum();
            switch(chopLeftNumber){
                case 0:
                    totalPickup0+= p.getChopStickLeftCount();
                    break;
                case 1:
                    totalPickup1+= p.getChopStickLeftCount();
                    break;
                case 2:
                    totalPickup2+= p.getChopStickLeftCount();
                    break;
                case 3:
                    totalPickup3+= p.getChopStickLeftCount();
                    break;
                case 4:
                    totalPickup4+= p.getChopStickLeftCount();
                    break;
            }
        }
        System.out.println("---------------------------------------------------");
        System.out.println("Displaying Chopstick Stats: ");
        System.out.println("Chopstick 0 was picked up " + totalPickup0 + " times" );
        System.out.println("Chopstick 1 was picked up " + totalPickup1 + " times" );
        System.out.println("Chopstick 2 was picked up " + totalPickup2 + " times" );
        System.out.println("Chopstick 3 was picked up " + totalPickup3 + " times" );
        System.out.println("Chopstick 4 was picked up " + totalPickup4 + " times" );
        System.out.println("---------------------------------------------------");
    }
}