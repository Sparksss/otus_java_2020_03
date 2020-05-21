/**
 * Created by Ilya Rogatkin, May 2020
 */

package Operation;

public class DecreaseFiveThousand implements Operation {
    @Override
    public int action(int countBills, int needSum, String NameBill) {
        int bill = Value.valueOf(NameBill).getValue();

        while (needSum > bill || countBills > 0 ) {
                countBills--;
        }
        return countBills;
    }
}
