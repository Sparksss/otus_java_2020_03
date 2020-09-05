import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by ilya on Aug, 2020
 */
public class Test {

    private final int MAX = 10;

    private int number = 0;

    private boolean isIncrement = true;

    private boolean isNeedCalculate = true;

    private String lastThreadName = "Thread-2";

    final private static Logger logger = LoggerFactory.getLogger(Test.class);


    public synchronized void calculate(String threadName) {
        for (int i = 0; i < 19; i++) {

            while (this.lastThreadName.equals(threadName)) {
                try {
                    this.wait();
                    break;
                } catch (InterruptedException exception) {
                    logger.error(exception.getMessage());
                }
            }

            if (this.isNeedCalculate) {
                if (this.number < this.MAX && this.isIncrement) {
                    increment();
                } else {
                    this.isIncrement = false;
                    decrement();
                }
            }
            showNumber();
            this.lastThreadName = threadName;
            this.isNeedCalculate = !this.isNeedCalculate;
            this.notifyAll();
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
