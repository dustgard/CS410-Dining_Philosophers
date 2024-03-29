public class ChopStick {
    private boolean lock;

    public ChopStick() {
        lock = false;
    }

    public synchronized void acquire() {
        // If another thread has the lock acquired, then wait until released
        while (lock) {
            try {
                this.wait();
            } catch (InterruptedException ignored) {
            }
        }
        lock = true;
    }

    public synchronized void release() {
        lock = false;
        this.notifyAll();
    }

    public synchronized boolean isAvailable() {
        if (!lock) {
            return true;
        } else {
            return false;
        }
    }
}
