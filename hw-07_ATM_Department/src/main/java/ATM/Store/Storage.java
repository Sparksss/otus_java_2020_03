/**
 * Created by Ilya Rogatkin, May 2020
 */

package ATM.Store;

import java.util.TreeMap;

public class Storage implements Box {
    private TreeMap<Value, StoreMoney> cells = new TreeMap<>();

    private final int ZERO_BILL = 0;

    public Storage() throws Exception {
        for(Value val : Value.values()) {
            cells.put(val, new Cell(val, ZERO_BILL));
        }
    }

    public void loadBillsInStorage(Value bill, int countBills) throws Exception {
        cells.get(bill).putBills(countBills);
    }

    public int getCountBillsByDenomination(Value bill) {
        return cells.get(bill).getCountBills();
    }

    public int getAmountSumByDenomination(Value bill) {
        return cells.get(bill).getSumCountBills();
    }

    public TreeMap<Value, StoreMoney> getListCells() {
        return cells;
    }

}
