package ATM;

import ATM.Operation.*;

import java.util.*;

import ATM.Store.*;


/**
 * Created by Ilya Rogatkin, May 2020
 */

public class ATM {

    private int ZERO_BILLS = 0;
    private Box storage;

    public ATM(int countFiveThousand, int countThousand, int countFiftyHundred, int countHundred) throws Exception {
        storage = new Storage(countFiveThousand, countThousand, countFiftyHundred, countHundred);
    }

    public void putMoney(Value bill, int countBills) throws Exception {
        if(countBills < 1) throw new Exception("Пожалуйста вставьте купюры в купюроприёмник");
        StoreMoney cell = storage.openCellByBill(bill);
        cell.putBills(countBills);
    }

    public int takeMoney(int amount) throws Exception {
        return this.processBilling(amount);
    }

    public int getBalance() {
        int sum = 0;
        for(Value val : Value.values()) {
            StoreMoney cell = storage.openCellByBill(val);
            sum += cell.getSumCountBills();
        }
        return sum;
    }

    private int processBilling(int amount) throws Exception {
        int totalBalance = this.getBalance();
        int calculateAmount = amount;
        int availableCountBills;
        if(totalBalance < amount) throw new Exception("К сожалению не возможно выдать данную сумму");

        Operation countBills = new CountBills();
        Map<Value, Integer> availableStoreBills = new HashMap<>();

        for(Value key : storage.getListCells().descendingKeySet()) {
            StoreMoney cell = storage.openCellByBill(key);
            availableCountBills = countBills.action(calculateAmount, cell.getCountBills(), key);
            availableStoreBills.put(key, availableCountBills);
            calculateAmount -= (key.getValue() * availableCountBills);
        }

        if(calculateAmount > 0) throw new Exception("Банкомат не может выдать запрошенную ,Вами, сумму");

        for(Value val : Value.values()) {
            StoreMoney cell = storage.openCellByBill(val);
            cell.takeBills(availableStoreBills.get(val));
        }
        return amount;
    }

    public void emptyStorage() throws Exception {
        storage = new Storage();
    }
}
