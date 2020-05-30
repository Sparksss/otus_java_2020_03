import ATM_department.Command.Collector;
import ATM_department.Command.Command;
import ATM_department.chain.*;
import ATM_department.observer.Consumer;
import ATM_department.observer.Listener;
import ATM_department.observer.Producer;

/**
 * Created by Ilya Rogatkin, May 2020
 */

public class Main {
    public static void main(String[] args) {
        ATMDepartment department = new ATMDepartment();

        Handler initializer = new ATMInitializer();
        Handler loader = new ATMLoader();
        Handler checkCash = new ATMChecker();

        initializer.setNext(loader);
        loader.setNext(checkCash);

        initializer.process(department);

        Command collector = new Collector(department);
        Listener consumer = new Consumer();
        Producer producer = new Producer();
        producer.addListener(consumer);
        producer.run(collector);

    }
}
