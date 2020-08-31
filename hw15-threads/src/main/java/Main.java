import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newFixedThreadPool;

/**
 * Created by ilya on Aug, 2020
 */
public class Main {

    final private static Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws InterruptedException {
        Test test = new Test();
        new Thread(() -> test.calculate("Thread-1")).start();
        new Thread(() -> test.calculate("Thread-2")).start();
    }
}
