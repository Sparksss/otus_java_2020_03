import ATM.ATM;

/**
 * Created by Ilya Rogatkin, May 2020
 */

public class Main {
    public static void main(String[] args) throws Exception {
        ATM atm = new ATM(10, 20, 50, 100);
        int amount = 15500;
        atm.takeMoney(amount);


//        System.out.println(atm.getBalance());

//        System.out.println(atm.getBalance());
    }
}
