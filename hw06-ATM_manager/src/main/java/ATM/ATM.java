package ATM;

import ATM.Operation.*;

import java.util.*;

import ATM.Store.*;


/**
 * Created by Ilya Rogatkin, May 2020
 */

public class ATM {

    private TreeMap<Value, Store> store = new TreeMap<>();

    private int ZERO_BILLS = 0;

    public ATM(int countFiveThousand, int countThousand, int countFiftyHundred, int countHundred) throws Exception {
        store.put(Value.FIVE_THOUSAND, new Cell(countFiveThousand, Value.FIVE_THOUSAND));
        store.put(Value.THOUSAND, new Cell(countThousand, Value.THOUSAND));
        store.put(Value.FIFTY_HUNDRED, new Cell(countFiftyHundred, Value.FIFTY_HUNDRED));
        store.put(Value.HUNDRED, new Cell(countHundred, Value.HUNDRED));
    }

    public void putMoney(Value bill, int countBills) throws Exception {
        if(countBills < 1) throw new Exception("Пожалуйста вставьте купюры в купюроприёмник");
        store.get(bill).putBills(countBills);
    }

    public int takeMoney(int amount) throws Exception {
        return this.processBilling(amount);
    }

    public int getBalance() {
        int sum = 0;
        for(Value val : Value.values()) {
            sum += store.get(val).getSumCountBills();
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

        for(Value key : store.descendingKeySet()) {
            Store cell = store.get(key);
            availableCountBills = countBills.action(calculateAmount, cell.getCountBills(), key);
            availableStoreBills.put(key, availableCountBills);
            calculateAmount -= (key.getValue() * availableCountBills);
        }

        if(calculateAmount > 0) throw new Exception("Банкомат не может выдать запрошенную ,Вами, сумму");

        for(Value val : Value.values()) {
            store.get(val).takeBills(availableStoreBills.get(val));
        }
        return amount;
    }

    public void clearATM() throws Exception {
      for(Value val : Value.values()) {
          Store bill = store.get(val);
          bill.takeBills(bill.getCountBills());
      }
    }
}
