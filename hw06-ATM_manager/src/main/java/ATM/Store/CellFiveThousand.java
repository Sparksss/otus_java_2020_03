/**
 * Created by Ilya Rogatkin, May 2020
 */

package ATM.Store;

import ATM.Operation.Value;

public class CellFiveThousand implements Store {
    private int count;
    private Value fiveThousand;

    public CellFiveThousand(int count) {
        this.count = count;
        this.fiveThousand = Value.FIVE_THOUSAND;
    }

    @Override
    public int takeBills(int count) {
        this.count -= count;
        return count;
    }

    @Override
    public void putBills(int count) {
        this.count += count;
    }

    @Override
    public int getCountBills() {
        return this.count;
    }

    @Override
    public int getSumCountBills() {
        return fiveThousand.getValue() * count;
    }
}
