/**
 * Created by Ilya Rogatkin, May 2020
 */


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import ATM.ATM;
import ATM.Store.Value;

import static org.junit.jupiter.api.Assertions.*;

public class ATMTest {

    ATM atm;

    @BeforeEach
    public void setUp() throws Exception {
        atm = new ATM(10, 20, 50, 100);
    }

    @DisplayName("Check initial sum in ATM")
    @Test
    public void shouldCheckInitialSum() throws Exception {
        assertEquals(atm.getBalance(), 105000);
    }

    @Test
    public void shouldAddFiveThousand() throws Exception {
        atm.putMoney(Value.FIVE_THOUSAND, 1);
        assertEquals(atm.getBalance(), 110000);
    }

    @Test
    public void shouldAddThousand() throws Exception {
        atm.putMoney(Value.THOUSAND, 1);
        assertEquals(atm.getBalance(), 106000);
    }

    @Test
    public void shouldNullifyBillsCount() throws Exception {
        atm.emptyStorage();
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

    @Test
    public void shouldThrowExceptionSumLessMinimum() {
        int amount = 50;
        assertThrows(Exception.class, () -> {
            atm.takeMoney(amount);
        });
    }

    @Test
    public void shouldThrowExceptionIncorrectCountOfBills() {
        int countBills = 0;
        assertThrows(Exception.class, () -> {
            atm.putMoney(Value.FIFTY_HUNDRED, countBills);
        });
    }

}
