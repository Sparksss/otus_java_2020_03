/**
 * Created by Ilya Rogatkin, May 2020
 */

package ATM.Operation;

import ATM.Store.Value;

public interface Operation {
    int action(int needSum, int countBills, Value denomination);
}
