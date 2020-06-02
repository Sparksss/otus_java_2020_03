/**
 * Created by Ilya Rogatkin, May 2020
 */

package ATMDepartment.Command;

import ATMDepartment.chain.ATMDepartment;

public class Collector implements Command {
    private ATMDepartment department;

    public Collector(ATMDepartment department) {
        this.department = department;
    }


    @Override
    public void execute() {
        int sumOfDepartment = department.collectAmountOfBalancesATM();
        System.out.println("Собранна следующая сумма остатков : " + sumOfDepartment);
    }
}
