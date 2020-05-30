/**
 * Created by Ilya Rogatkin, May 2020
 */

package ATM_department.Command;

import ATM_department.chain.ATMDepartment;

public class Collector implements Command {
    private ATMDepartment department;

    public Collector(ATMDepartment department) {
        this.department = department;
    }


    @Override
    public void execute() {
        int sumOfDepartment = department.collectAmountOfBalancesATM();
        System.out.println("Total sum in department : " + sumOfDepartment);
    }
}
