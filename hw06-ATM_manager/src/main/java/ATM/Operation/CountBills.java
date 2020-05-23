/**
 * Created by Ilya Rogatkin, May 2020
 */

package ATM.Operation;

public class CountBills implements Operation {
    @Override
    public int action(int needSum, int countBills, Value denomination) {
        int val = denomination.getValue();
        int availableBills = 0;
        while (needSum > 0 & countBills > 0) {
            needSum -= val;
            countBills--;
            availableBills++;
        }
        return availableBills;
    }
}
