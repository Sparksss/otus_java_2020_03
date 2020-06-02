/**
 * Created by Ilya Rogatkin, May 2020
 */

package ATMDepartment.chain;

import ATM.ATM;
import ATM.ATMCityBank;

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

    public void initializeATMs(int countATMs) {
        for(int i = 0; i < countATMs; i++) {
            try {
                atms.add(new ATMCityBank());
            } catch (Exception err) {
                System.out.println(err.getMessage());
            }
        }
    }

    public void loadCashInATMS() {
        for(int i = 0; i < atms.size(); i++) {
            loadCashInATM(i, 10, 20, 50 , 100);
        }
    }

    public ATMDepartment() {}

    ATMDepartment(List<ATM> atms) {
        this.atms = atms;
    }

    void addATMtoDepartment(ATM atm) {
        atms.add(atm);
    }

    void loadCashInATM(int numberATM, int ...cash) {
        try {
            ATM atm = atms.get(numberATM);
            atm.loadCashInStore(cash);
        } catch (Exception err) {
            System.out.println(err.getMessage());
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
