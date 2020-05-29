/**
 * Created by Ilya Rogatkin, May 2020
 */

package ATM.Store;

import java.util.TreeMap;

public interface Box {
    TreeMap<Value, StoreMoney> getListCells();
    void loadBillsInStorage(Value bill, int countBills) throws Exception;
    int getCountBillsByDenomination(Value bill);
    int getAmountSumByDenomination(Value bill);
}
