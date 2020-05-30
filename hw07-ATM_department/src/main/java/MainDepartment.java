import ATM_department.Command.Collector;
import ATM_department.Command.Command;
import ATM_department.chain.*;
import ATM_department.memento.Originator;
import ATM_department.memento.State;
import ATM_department.observer.Consumer;
import ATM_department.observer.Listener;
import ATM_department.observer.Producer;

/**
 * Created by Ilya Rogatkin, May 2020
 */

public class MainDepartment {
    public static void main(String[] args) {
        ATMDepartment department = new ATMDepartment();
        Originator originator = new Originator();

        Handler initializer = new ATMInitializer();
        Handler loader = new ATMLoader();
        Handler checkCash = new ATMChecker();

        initializer.setNext(loader);
        loader.setNext(checkCash);

        initializer.process(department);

        State state = new State(department.copy());
        originator.saveStateATM(state);

        Command collector = new Collector(department);
        Listener consumer = new Consumer(collector);

        Producer producer = new Producer();
        producer.addListener(consumer);
        producer.run();
    }
}
