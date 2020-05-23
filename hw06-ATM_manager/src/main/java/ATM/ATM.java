package ATM;

import ATM.Operation.*;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Ilya Rogatkin, May 2020
 */

public class ATM {

    private Map<Value, Integer> value;

    private int ZERO_BILLS = 0;

    public ATM() {
        this.value = new HashMap<>();
        value.put(Value.FIVE_THOUSAND, 0);
        value.put(Value.THOUSAND, 0);
        value.put(Value.FIFTY_HUNDRED, 0);
        value.put(Value.HUNDRED, 0);
    }

    private ATM(ATMBuilder builder) {
        this.value = new HashMap<>();
        value.put(Value.FIVE_THOUSAND, builder.countFiveThousand);
        value.put(Value.THOUSAND, builder.countThousand);
        value.put(Value.FIFTY_HUNDRED, builder.FiftyHundred);
        value.put(Value.HUNDRED, builder.hundred);
    }

    public void putMoney(Value bill) throws Exception {
        if(!value.containsKey(bill)) throw new Exception("Пожалуйста вставьте купюру");

        if(bill == Value.FIVE_THOUSAND) {
            this.addBill(Value.FIVE_THOUSAND);
        } else if(bill == Value.THOUSAND) {
            this.addBill(Value.THOUSAND);
        } else if(bill == Value.FIFTY_HUNDRED){
            this.addBill(Value.FIFTY_HUNDRED);
        } else if(bill == Value.HUNDRED) {
            this.addBill(Value.HUNDRED);
        }
    }

    public int takeMoney(int amount) throws Exception {
        return this.processBilling(amount);
    }

    public int getBalance() {
        int totalBalance = 0;
        for(Map.Entry<Value, Integer> pair : value.entrySet()){
            totalBalance += (pair.getKey().getValue() * pair.getValue());
        }
        return totalBalance;
    }

    private void decreaseBalance(int countBills, Value denomination) {
        value.put(denomination, value.get(denomination) - countBills);
    }

    private void addBill(Value bill) {
        value.put(bill, value.get(bill) + 1);
    }

    private int processBilling(int amount) throws Exception {
        int totalBalance = this.getBalance();
        int calculateAmount = amount;
        if(totalBalance < amount) throw new Exception("Запрашиваемая сумма превышает сумму денег в банкомате");

        Operation countBills = new CountBills();

        int countFiftyThousand = countBills.action(calculateAmount, Value.FIVE_THOUSAND.getValue(), Value.FIVE_THOUSAND);
        calculateAmount -= (Value.FIVE_THOUSAND.getValue() * countFiftyThousand);

        int countThousandBills = countBills.action(calculateAmount, Value.THOUSAND.getValue(), Value.THOUSAND);
        calculateAmount -= (Value.THOUSAND.getValue() * countThousandBills);

        int countFiftyHundred = countBills.action(calculateAmount, Value.FIFTY_HUNDRED.getValue(), Value.FIFTY_HUNDRED);
        calculateAmount -= (Value.FIFTY_HUNDRED.getValue() * countFiftyHundred);

        int countHundred = countBills.action(calculateAmount, Value.HUNDRED.getValue(), Value.HUNDRED);
        calculateAmount -= (Value.HUNDRED.getValue() * countHundred);

        if(calculateAmount > 0) throw new Exception("Банкомат не может выдать запрошенную ,Вами, сумму");

        this.decreaseBalance(countFiftyThousand, Value.FIVE_THOUSAND);
        this.decreaseBalance(countThousandBills, Value.THOUSAND);
        this.decreaseBalance(countFiftyHundred, Value.FIFTY_HUNDRED);
        this.decreaseBalance(countHundred, Value.HUNDRED);
        return amount;
    }

    public void clearATM() {
      for(Value val : Value.values()) {
          this.decreaseBalance(value.get(val), val);
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
