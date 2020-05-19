/**
 * Created by Ilya Rogatkin, May 2020
 */

package value;

public class Value100 implements Value {

    private int numberOfBills;
    private int amountOfMoney;

     Value100(int numberOfBills, int amountOfMoney){
         this.numberOfBills = numberOfBills;
         this.amountOfMoney = amountOfMoney;
    }

    @Override
    public void add(int value) {
        if(value == 100) {
            this.numberOfBills++;
            this.amountOfMoney += value;
        }
    }

    @Override
    public int get(int number) {
        this.numberOfBills -= number;
        int takeMoney = this.amountOfMoney - number * 100;
        return takeMoney;
    }
}
