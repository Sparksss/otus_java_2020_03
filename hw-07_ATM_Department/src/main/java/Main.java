import ATM_department.chain.*;

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

    }
}
