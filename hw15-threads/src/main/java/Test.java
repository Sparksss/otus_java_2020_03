/**
 * Created by ilya on Aug, 2020
 */


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Test implements Runnable {

    private final int MAX = 10;

    private int number = 0;

    private boolean isIncrement = true;

    private boolean isNeedCalculate = true;

    final private static Logger logger = LoggerFactory.getLogger(Test.class);

    @Override
    public void run() {
        for(int i = 0; i < 20; i++) {
            calculate();
        }
    }

    private synchronized void calculate() {
        if(this.isNeedCalculate) {
            if(this.number < this.MAX && this.isIncrement) {
                increment();
            } else {
                this.isIncrement = false;
                decrement();
            }
        }
        this.isNeedCalculate = !this.isNeedCalculate;
        this.notifyAll();
        if(!this.isIncrement && this.number == 0) return;
        showNumber();
            while (true) {
                try {
                    this.wait();
                    break;
                } catch (InterruptedException exception) {
                    logger.error(exception.getMessage());
                }
        }
    }


    private void increment() {
        ++this.number;
    }

    private void decrement() {
        --this.number;
    }

    private void showNumber () {
        logger.info(String.valueOf(this.number));
    }
}
