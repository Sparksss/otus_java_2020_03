/**
 * Created by Ilya Rogatkin, May 2020
 */

package ATM_department.chain;

public class ATMLoader extends ATMProcessor {
    @Override
    protected void processInternal(ATMDepartment department) {
        department.loadCashInATMS();
    }

    @Override
    public String getProcessorName() {
        return "Загрузка купюр";
    }
}
