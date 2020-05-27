/**
 * Created by Ilya Rogatkin, May 2020
 */

package ATM.Store;

import java.util.TreeMap;

public interface Box {
    StoreMoney openCellByBill(Value bill);
    TreeMap<Value, StoreMoney> getListCells();
}
