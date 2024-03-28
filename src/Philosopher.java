public class Philosopher implements Runnable {

    private String name;
    private Thread thread;
    private volatile boolean running = true;
    private ChopStick chopStickRight, chopStickLeft;

    public Philosopher(){

    }

    public Philosopher(String name) {
        this.name = name;
        thread = new Thread(this, name);
        thread.start();
    }

    public void assignChopSticks(ChopStick chopStickRight, ChopStick chopStickLeft){
        this.chopStickLeft = chopStickLeft;
        this.chopStickRight = chopStickRight;
    }

    public void eatRice(){

    }

    public void takeChopStick(){

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

    public Thread getThread() {
        return thread;
    }
}
