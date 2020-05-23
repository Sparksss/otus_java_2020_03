/**
 * Created by Ilya Rogatkin, May 2020
 */

package ATM.Operation;

public interface Operation {
    int action(int needSum, int countBills, Value denomination);
}
