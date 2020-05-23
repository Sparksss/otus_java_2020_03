/**
 * Created by Ilya Rogatkin, May 2020
 */


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import ATM.ATM;
import ATM.Operation.Value;
public class ATMTest {

    ATM atm;

    @BeforeEach
    public void setUp() {

    }




    @Test
    public void testDivide() {
        assertThrows(ArithmeticException.class, () -> {
            Integer.divideUnsigned(42, 0);
        });
    }
}
