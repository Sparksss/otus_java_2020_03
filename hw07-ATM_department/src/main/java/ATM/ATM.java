/**
 * Created by Ilya Rogatkin, May 2020
 */

package ATM;

import ATM.Store.Value;

public interface ATM {
    public void putMoney(Value bill, int countBills) throws Exception;
    public int takeMoney(int amount) throws Exception;
    public int getBalance();
    public void emptyStorage();
    public void loadCashInStore(int... bills) throws Exception;
    public ATM copy() throws Exception;
}
