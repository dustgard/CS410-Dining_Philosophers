import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

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
    private final Semaphore[] chopsticksList = new Semaphore[NUM_CHOPSTICKS];
    private final Map<Integer, Semaphore> chopStickMap = new HashMap<>();
    private final int SECONDS_TO_EAT = 5;
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
            Semaphore sem = new Semaphore(1);
            chopStickMap.put(i, sem);
        }
        // Create philosophers and assign chopsticks to their left and right-hand sides
        for (int i = 0; i < NUM_PHILOSOPHERS; i++) {
            philosophersList[i] = new Philosopher(String.valueOf(i));
            if (i == (0)) {
                // For the philosopher left of the starting philosopher (the final philosopher), it's right chopstick
                // should be the same chopstick as the first philosopher's left chopstick
                philosophersList[i].assignChopSticks(chopStickMap.get(chopStickMap.size()-1), chopStickMap.size()-1 , chopStickMap.get(i), i);
            }
            else if (i == (philosophersList.length - 1)) {
                philosophersList[i].assignChopSticks(chopStickMap.get(i-1), i-1, chopStickMap.get(chopsticksList.length - 1), chopsticksList.length - 1);
            }
            else {
                philosophersList[i].assignChopSticks(chopStickMap.get(i-1), i-1, chopStickMap.get(i), i);
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
        for (Philosopher p : philosophersList) {
            System.out.println("Philosopher: " + p.getThread().getName() + " was able to eat " + p.getEatCount() + " times");
            System.out.println("");
            System.out.println("He used the ChopSticks: ");
            System.out.println("");
            System.out.println("Right " + p.getChopStickRightNum() + " was picked up " + p.getChopStickRightCount() + " times");
            System.out.println("Left " + p.getChopStickLeftNum() + " was picked up " + p.getChopStickLeftCount() + " times");
            System.out.println("");
            System.out.println("----------------------------------------------------");
        }
    }
}