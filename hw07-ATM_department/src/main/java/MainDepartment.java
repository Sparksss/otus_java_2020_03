import ATMDepartment.Command.Collector;
import ATMDepartment.Command.Command;
import ATMDepartment.chain.*;
import ATMDepartment.memento.Originator;
import ATMDepartment.memento.State;
import ATMDepartment.observer.Consumer;
import ATMDepartment.observer.Listener;
import ATMDepartment.observer.Producer;

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

        try {
            State state = new State(department.copy());
            originator.saveStateATM(state);
        } catch (Exception err) {
            System.out.println(err.getMessage());
        }

        Command collector = new Collector(department);
        Listener consumer = new Consumer(collector);

        Producer producer = new Producer();
        producer.addListener(consumer);
        producer.run();

        department = originator.restoreState().getDepartment();
    }
}
