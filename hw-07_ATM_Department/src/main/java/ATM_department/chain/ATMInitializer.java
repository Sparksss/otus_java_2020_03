/**
 * Created by Ilya Rogatkin, May 2020
 */

package ATM_department.chain;

import ATM.ATMCityBank;

public class ATMInitializer extends ATMProcessor {
    @Override
    protected void processInternal(ATMDepartment department) {
        department.addATMtoDepartment(new ATMCityBank());
    }

    @Override
    public String getProcessorName() {
        return "Инициализация банкомата";
    }
}
