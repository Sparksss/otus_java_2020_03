/**
 * Created by Ilya Rogatkin, May 2020
 */

package ATM.Store;

public class Cell implements StoreMoney {

    private int count;
    private Value denomination;
    private final int ZERO_BILL = 0;

    public Cell(Value denomination, int count) throws Exception {
        if(count < ZERO_BILL) throw new Exception("Не может быть отрицательного количества купюр");
        this.count = count;
        this.denomination = denomination;
    }

    public Cell(StoreMoney cell) {
        this.denomination = cell.getDenomination();
        this.count = cell.getCountBills();
    }

    @Override
    public int takeBills(int count) throws Exception {
        if(count > this.count) throw new Exception("Невозможно снять данную сумму");
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
        return denomination.getValue() * count;
    }

    @Override
    public Value getDenomination() {
        return denomination;
    }
}
