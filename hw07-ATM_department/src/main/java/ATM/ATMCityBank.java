package ATM;

import ATM.Operation.CountBills;
import ATM.Operation.Operation;
import ATM.Store.*;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;


/**
 * Created by Ilya Rogatkin, May 2020
 */

public class ATMCityBank implements ATM {

    private int MIN_AMOUNT = 100;
    private final int ZERO_BILL = 0;
    private Box storage;

    public ATMCityBank() throws Exception {
        storage = new Storage();
    }

    Box getStorage() {
        return this.storage;
    }

    ATMCityBank(Box storage) throws Exception {
        TreeMap<Value, StoreMoney> cells = storage.getListCells();
        this.storage = new Storage();
        for(Map.Entry<Value, StoreMoney> entry : cells.entrySet()) {
            StoreMoney cell = entry.getValue().copy();
            this.storage.loadBillsInStorage(cell.getDenomination(), cell.getCountBills());
        }
    }

    public ATMCityBank copy() throws Exception {
        return new ATMCityBank(this.getStorage());
    }

    public void putMoney(Value bill, int countBills) throws Exception {
        if(countBills < 1) throw new Exception("Пожалуйста вставьте купюры в купюроприёмник");
        storage.loadBillsInStorage(bill, countBills);
    }

    public int takeMoney(int amount) throws Exception {
        return this.processBilling(amount);
    }

    public int getBalance() {
        int sum = 0;
        for(Value val : Value.values()) {
            sum += storage.getAmountSumByDenomination(val);
        }
        return sum;
    }

    private int processBilling(int amount) throws Exception {
        if(amount < MIN_AMOUNT) throw new Exception("Минимальная сумма должна быть не менее 100");
        int totalBalance = this.getBalance();
        int calculateAmount = amount;
        int availableCountBills;
        if(totalBalance < amount) throw new Exception("К сожалению не возможно выдать данную сумму");

        Operation countBills = new CountBills();
        Map<Value, Integer> availableStoreBills = new HashMap<>();

        for(Value key : storage.getListCells().descendingKeySet()) {
            int count = storage.getCountBillsByDenomination(key);
            availableCountBills = countBills.action(calculateAmount, count, key);
            availableStoreBills.put(key, availableCountBills);
            calculateAmount -= (key.getValue() * availableCountBills);
        }

        if(calculateAmount > 0) throw new Exception("Банкомат не может выдать запрошенную ,Вами, сумму");

        for(Value val : Value.values()) {
            storage.loadBillsInStorage(val, availableStoreBills.get(val));
        }
        return amount;
    }


    public void loadCashInStore(int... bills) throws Exception {
        int i = 0;
        for(Value key : storage.getListCells().descendingKeySet()) {
            if(i < bills.length) {
                int count = bills[i];
                storage.loadBillsInStorage(key, count);
            } else {
                storage.loadBillsInStorage(key, ZERO_BILL);
            }
            i++;
        }
    }

    public void emptyStorage() throws Exception {
        storage = new Storage();
    }
}
