/**
 * Created by Ilya Rogatkin, May 2020
 */

package ATM_department.chain;

import ATM.ATM;
import ATM.ATMCityBank;

import java.util.ArrayList;
import java.util.List;

public class ATMDepartment {
    private final List<ATMCityBank> atms = new ArrayList<>();

    void addATMtoDepartment(ATMCityBank atm) {
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
}
