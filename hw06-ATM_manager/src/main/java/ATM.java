import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ilya Rogatkin, May 2020
 */

public class ATM {

    private Map<Integer, Integer> value = new HashMap<>();

    public static void main(String[] args) {

    }

    public ATM() {
        value.put(5000, 0);
        value.put(1000, 0);
        value.put(500, 0);
        value.put(100, 0);
        value.put(50, 0);
    }

    public void putMoney(int bill) throws Exception {
        if(!value.containsKey(bill)) throw new Exception("Пожалуйста вставьте купюру");

        int count = value.get(bill);
        value.put(bill, ++count);
    }

    public int takeMoney(int amount) throws Exception {
        int totalSum = this.processBilling(amount);
        return totalSum;
    }

    public int getBalance() {
        int totalBalance = 0;
        for(Map.Entry<Integer, Integer> pair : value.entrySet()){
            totalBalance += (pair.getKey() * pair.getValue());
        }
        return totalBalance;
    }

    private int processBilling(int amount) throws Exception {
        int totalBalance = this.getBalance();
        if(totalBalance < amount) throw new Exception("Запрашиваемая сумма превышает сумму денег в банкомате");

        return 0;
    }
}
