/**
 * Created by Ilya Rogatkin, May 2020
 */

package ATM_department.chain;

import ATM.ATM;

import java.util.ArrayList;
import java.util.List;

public class ATMDepartment {
    private List<ATM> atms = new ArrayList<>();

    public ATMDepartment copy() throws Exception {
        List<ATM> copyATM = new ArrayList<>();
        for(ATM atm : atms) {
            copyATM.add(atm.copy());
        }
        return new ATMDepartment(copyATM);
    }

    public ATMDepartment() {}

    ATMDepartment(List<ATM> atms) {
        this.atms = atms;
    }

    void addATMtoDepartment(ATM atm) {
        atms.add(atm);
    }

    void loadCashInATM() {
        for (ATM atm : atms) {
            try {
                atm.loadCashInStore(10, 20, 50, 100);
            } catch (Exception err) {
                System.out.println(err.getMessage());
            }

        }
    }

    void checkSumInATM() throws Exception {
        for(ATM atm : atms) {
            if(atm.getBalance() == 0) throw new Exception("Купюры не загружены в банкомат");
        }
    }

    public int collectAmountOfBalancesATM() {
        int sum = 0;
        for(ATM atm : atms) {
            sum += atm.getBalance();
            atm.emptyStorage();
        }
        return sum;
    }
}
