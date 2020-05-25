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

    public ATM() {
        store.put(Value.FIVE_THOUSAND, new Cell(0, Value.FIVE_THOUSAND));
        store.put(Value.THOUSAND, new Cell(0, Value.THOUSAND));
        store.put(Value.FIFTY_HUNDRED, new Cell(0, Value.FIFTY_HUNDRED));
        store.put(Value.HUNDRED, new Cell(0, Value.HUNDRED));
    }

    private ATM(ATMBuilder builder) {
        store.put(Value.FIVE_THOUSAND, new Cell(builder.countFiveThousand, Value.FIVE_THOUSAND));
        store.put(Value.THOUSAND, new Cell(builder.countThousand, Value.THOUSAND));
        store.put(Value.FIFTY_HUNDRED, new Cell(builder.FiftyHundred, Value.FIFTY_HUNDRED));
        store.put(Value.HUNDRED, new Cell(builder.hundred, Value.HUNDRED));
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

    public static class ATMBuilder {
        private int countFiveThousand;
        private int countThousand;
        private int FiftyHundred;
        private int hundred;

      public ATMBuilder(int countFiveThousand){
           this.countFiveThousand = countFiveThousand;
       }

       public ATMBuilder insertThousand(int count) {
            this.countThousand = count;
            return this;
        }

       public ATMBuilder insertFiftyHundred(int count) {
            this.FiftyHundred = count;
            return this;
        }
        public ATMBuilder insertHundred(int count) {
            this.hundred = count;
            return this;
        }
       public ATM build() { return new ATM(this); }
    }
}
