/**
 * Created by ilya on Aug, 2020
 */


public class Test implements Runnable {

    private final int MAX = 10;

    private int number = 0;

    private boolean isIncrement = true;

    @Override
    public void run() {
        for(int i = 0; i < 10; i++) {
            calculate();
        }
    }

    private synchronized void calculate() {
        if(this.number < this.MAX && this.isIncrement) {
            increment();
        } else {
            this.isIncrement = false;
            decrement();
        }
        this.notifyAll();
        if(!this.isIncrement && this.number == 0) return;
        showNumber(Thread.currentThread().getName());
            while (true) {
                try {
                    this.wait();
                    break;
                } catch (InterruptedException exception) {
                    System.out.println(exception.getMessage());
                }
        }
    }


    private void increment() {
        ++this.number;
    }

    private void decrement() {
        --this.number;
    }

    private void showNumber (String threadName) {
        System.out.println(threadName + " -> " + number);
    }
}
