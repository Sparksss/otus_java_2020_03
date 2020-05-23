/**
 * Created by Ilya Rogatkin, May 2020
 */


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import ATM.ATM;
import ATM.Operation.Value;

import static org.junit.jupiter.api.Assertions.*;

public class ATMTest {

    ATM atm;

    @BeforeEach
    public void setUp() {
        atm = new ATM.ATMBuilder(10)
                .insertThousand(20)
                .insertFiftyHundred(50)
                .insertHundred(100)
                .build();
    }

    @DisplayName("Check initial sum in ATM")
    @Test
    public void shouldCheckInitialSum() throws Exception {
        assertEquals(atm.getBalance(), 105000);
    }

    @Test
    public void shouldAddFiveThousand() throws Exception {
        atm.putMoney(Value.FIVE_THOUSAND);

        assertEquals(atm.getBalance(), 110000);
    }

    @Test
    public void shouldAddThousand() throws Exception {
        atm.putMoney(Value.THOUSAND);
        assertEquals(atm.getBalance(), 106000);
    }

    @Test
    public void shouldNullifyBillsCount() {
        atm.clearATM();
        assertEquals(atm.getBalance(), 0);
    }


    @Test
    public void shouldTakeMoneyMoreThenExists() {
        int amount = atm.getBalance() + 1000;
        assertThrows(Exception.class, () -> {
            atm.takeMoney(amount);
        });
    }

    @Test
    public void shouldTakeMoney() throws Exception {
        int amount = 5500;
        assertEquals(atm.takeMoney(amount), amount);
    }


    @Test
    public void shouldCheckBalanceAfterTakeMoney() throws Exception {
        int amount = 15500;
        atm.takeMoney(amount);
        assertEquals(atm.getBalance(), 89500);
    }

    @Test
    public void shouldThrowIncorrectSum() {
        int amount = 10652;
        assertThrows(Exception.class, () -> {
            atm.takeMoney(amount);
        });
    }

//    @Test
//    public void testDivide() {
//        assertThrows(ArithmeticException.class, () -> {
//            Integer.divideUnsigned(42, 0);
//        });
//    }
}
