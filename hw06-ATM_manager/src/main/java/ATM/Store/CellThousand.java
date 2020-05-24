/**
 * Created by Ilya Rogatkin, May 2020
 */

package ATM.Store;

public class CellThousand implements Store {

    private int count;
    private Value thousand;

    public CellThousand(int count) {
        this.count = count;
        this.thousand = Value.THOUSAND;
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
        return thousand.getValue() * count;
    }

    @Override
    public Value getDenomination() {
        return thousand;
    }
}
