/**
 * Created by Ilya Rogatkin, May 2020
 */

package ATM_department.chain;

public class ATMChecker extends ATMProcessor {
    @Override
    protected void processInternal(ATMDepartment department) {
        try {
            department.checkSumInATM();
        } catch (Exception err) {
            System.out.println(err.getMessage());
        }
    }

    @Override
    public String getProcessorName() {
        return null;
    }
}
