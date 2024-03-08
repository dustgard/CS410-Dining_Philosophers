public class Philosopher implements Runnable {

    private String name;
    private Thread thread;
    private ChopStick chopStickRight, chopStickLeft;

    public Philosopher(){

    }

    public Philosopher(String name) {
        this.name = name;
        thread = new Thread(this, name);
    }

    public void assignChopSticks(ChopStick chopStickRight, ChopStick chopStickLeft){
        this.chopStickLeft = chopStickLeft;
        this.chopStickRight = chopStickRight;
    }

    public void eatRice(){

    }

    public void takeChopStick(){

    }

    @Override
    public void run() {

    }
}
