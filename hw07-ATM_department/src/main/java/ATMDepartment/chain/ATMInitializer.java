/**
 * Created by Ilya Rogatkin, May 2020
 */

package ATMDepartment.chain;

public class ATMInitializer extends ATMProcessor {
    @Override
    protected void processInternal(ATMDepartment department) {
        try {
            final int NUMBER_OF_ATMS = 5;
            department.initializeATMs(NUMBER_OF_ATMS);
        } catch (Exception err) {
            System.out.println(err.getMessage());
        }

    }

    @Override
    public String getProcessorName() {
        return "Инициализация банкоматов";
    }
}
