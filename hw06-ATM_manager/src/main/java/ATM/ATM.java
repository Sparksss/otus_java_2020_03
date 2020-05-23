package ATM;

import ATM.Operation.*;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by Ilya Rogatkin, May 2020
 */

public class ATM {

    private Map<Value, Integer> value = new HashMap<>();

    private int ZERO_BILLS = 0;

    public ATM() {
        value.put(Value.FIVE_THOUSAND, 0);
        value.put(Value.THOUSAND, 0);
        value.put(Value.FIFTY_HUNDRED, 0);
        value.put(Value.HUNDRED, 0);
    }

    public ATM(ATMBuilder builder) {
        value.put(Value.FIVE_THOUSAND, 0);
        value.put(Value.THOUSAND, 0);
        value.put(Value.FIFTY_HUNDRED, 0);
        value.put(Value.HUNDRED, 0);
    }

    public void putMoney(int bill) throws Exception {

        if(!value.containsKey(bill)) throw new Exception("Пожалуйста вставьте купюру");

        if(bill == Value.FIVE_THOUSAND.getValue()) {
            this.addBill(Value.FIVE_THOUSAND);
        } else if(bill == Value.THOUSAND.getValue()) {
            this.addBill(Value.THOUSAND);
        } else if(bill == Value.FIFTY_HUNDRED.getValue()){
            this.addBill(Value.FIFTY_HUNDRED);
        } else if(bill == Value.HUNDRED.getValue()) {
            this.addBill(Value.HUNDRED);
        }
    }

    public int takeMoney(int amount) throws Exception {
        int totalSum = this.processBilling(amount);
        return totalSum;
    }

    public int getBalance() {
        int totalBalance = 0;
        for(Map.Entry<Value, Integer> pair : value.entrySet()){
            totalBalance += (pair.getKey().getValue() * pair.getValue());
        }
        return totalBalance;
    }

    private void decreaseBalance(int countBills, Value denomination) {
        value.put(denomination, value.get(denomination.getValue()) - countBills);
    }

    private void addBill(Value bill) {
        value.put(bill, value.get(bill.getValue()) + 1);
    }

    private int processBilling(int amount) throws Exception {
        int totalBalance = this.getBalance();
        if(totalBalance < amount) throw new Exception("Запрашиваемая сумма превышает сумму денег в банкомате");
        Operation countBills = new CountBills();
        int countFiftyThousand = countBills.action(amount, Value.FIVE_THOUSAND.getValue(), Value.FIVE_THOUSAND);
        int countThousandBills = countBills.action(amount, Value.THOUSAND.getValue(), Value.THOUSAND);
        int countFiftyHundred = countBills.action(amount, Value.FIFTY_HUNDRED.getValue(), Value.FIFTY_HUNDRED);
        int countHundred = countBills.action(amount, Value.HUNDRED.getValue(), Value.HUNDRED);
        int availableAmount = this.countAvailableAmount(countFiftyThousand, countThousandBills, countFiftyHundred, countHundred);

        if(availableAmount != amount) throw new Exception("Банкомат не может выдать запрошенную ,Вами, сумму");

        this.decreaseBalance(countFiftyThousand, Value.FIVE_THOUSAND);
        this.decreaseBalance(countThousandBills, Value.THOUSAND);
        this.decreaseBalance(countFiftyHundred, Value.FIFTY_HUNDRED);
        this.decreaseBalance(countHundred, Value.HUNDRED);
        return availableAmount;
    }

    private void clearATM() {
        this.decreaseBalance(ZERO_BILLS, Value.FIVE_THOUSAND);
        this.decreaseBalance(ZERO_BILLS, Value.THOUSAND);
        this.decreaseBalance(ZERO_BILLS, Value.FIFTY_HUNDRED);
        this.decreaseBalance(ZERO_BILLS, Value.HUNDRED);
    }

    private int countAvailableAmount(int countFiftyThousand, int countThousandBills, int countFiftyHundred, int countHundred) {
        return (countFiftyThousand * Value.FIVE_THOUSAND.getValue()) + (countThousandBills * Value.THOUSAND.getValue()) + (countFiftyHundred * Value.FIFTY_HUNDRED.getValue()) + (countHundred * Value.HUNDRED.getValue());
    }

    public static class ATMBuilder {
        private int countFiveThousand;
        private int countThousand;
        private int FiftyHundred;
        private int hundred;

        ATMBuilder insertFiveThousand(int count) {
            this.countFiveThousand = count;
            return this;
        }

        ATMBuilder insertThousand(int count) {
            this.countThousand = count;
            return this;
        }

        ATMBuilder insertFiftyHundred(int count) {
            this.FiftyHundred = count;
            return this;
        }
        ATMBuilder insertHundred(int count) {
            this.FiftyHundred = count;
            return this;
        }
        ATMBuilder build() { return new ATMBuilder(); }
    }
}
