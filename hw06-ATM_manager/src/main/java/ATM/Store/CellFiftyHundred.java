/**
 * Created by Ilya Rogatkin, May 2020
 */

package ATM.Store;

import ATM.Operation.Value;

public class CellFiftyHundred implements Store {
    private int count;
    private Value fiftyHundred;

    public CellFiftyHundred(int count) {
        this.count = count;
        this.fiftyHundred = Value.FIFTY_HUNDRED;
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
        return fiftyHundred.getValue() * count;
    }
}
