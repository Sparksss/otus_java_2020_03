/**
 * Created by Ilya Rogatkin, May 2020
 */

package ATM_department.chain;

import ATM.ATMCityBank;

public class ATMInitializer extends ATMProcessor {
    @Override
    protected void processInternal(ATMDepartment department) {
        try {
            department.addATMtoDepartment(new ATMCityBank());
        } catch (Exception err) {
            System.out.println(err.getMessage());
        }

    }

    @Override
    public String getProcessorName() {
        return "Инициализация банкомата";
    }
}
