package Operation;

/**
 * Created by Ilya Rogatkin, May 2020
 */

public enum Value {
    FIFTY(50), HUNDRED(100), FIFTY_HUNDRED(500), THOUSAND(1000), FIVE_THOUSAND(5000);

    private int count;

    private Value(int count) {
        this.count = count;
    }

    public int getValue() {
        return this.count;
    }
}