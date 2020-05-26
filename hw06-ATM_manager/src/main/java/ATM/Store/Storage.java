/**
 * Created by Ilya Rogatkin, May 2020
 */

package ATM.Store;

import java.util.TreeMap;

public class Storage {
    private TreeMap<Value, StoreMoney> cells = new TreeMap<>();

    private final int ZERO_BILL = 0;

    public Storage(int countFiveThousand, int countThousand, int countFiftyHundred, int countHundred) throws Exception {
        cells.put(Value.FIVE_THOUSAND, new Cell(Value.FIVE_THOUSAND, countFiveThousand));
        cells.put(Value.THOUSAND, new Cell(Value.THOUSAND, countThousand));
        cells.put(Value.FIFTY_HUNDRED, new Cell(Value.FIFTY_HUNDRED, countFiftyHundred));
        cells.put(Value.HUNDRED, new Cell(Value.HUNDRED, countHundred));
    }

    public Storage() throws Exception {
        for(Value val : Value.values()) {
            cells.put(val, new Cell(val, ZERO_BILL));
        }
    }

    public StoreMoney openCellByBill(Value bill) {
        return cells.get(bill);
    }

    public TreeMap<Value, StoreMoney> getListCells() {
        return cells;
    }

}
