/**
 * Created by Ilya Rogatkin, May 2020
 */

package ATM.Store;

import ATM.Operation.Value;

public class CellHundred implements Store {
    private int count;
    private Value hundred;

    public CellHundred(int count) {
        this.count = count;
        this.hundred = Value.HUNDRED;
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
        return this .count;
    }

    @Override
    public int getSumCountBills() {
        return hundred.getValue() * count;
    }
}
