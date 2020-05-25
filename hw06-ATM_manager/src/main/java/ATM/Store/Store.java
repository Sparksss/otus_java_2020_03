/**
 * Created by Ilya Rogatkin, May 2020
 */

package ATM.Store;


public interface Store {
    int takeBills(int count) throws Exception;
    void putBills(int count);
    int getCountBills();
    int getSumCountBills();
    Value getDenomination();
}
