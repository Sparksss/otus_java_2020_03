package springboot.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import springboot.domains.Stock;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by ilya on Sep, 2020
 */
@JdbcTest
@Import({StockDaoJdbc.class})
class StockDaoJdbcTest {

    @Autowired
    private StockDaoJdbc stockDaoJdbc;

    private long FIRST_STOCK_ID = 1;
    private final double OPEN_PRICE = 453.13;
    private final double CLOSE_PRICE = 407.34;
    private final double HIGH = 455.68;
    private final double LOW = 351.3;
    private final double UPDATE_OPEN_PRICE = 100.345;

    @Test
    void getById() {
        Stock actualStock = stockDaoJdbc.getById(FIRST_STOCK_ID);
        assertThat(actualStock)
                .hasFieldOrPropertyWithValue("id", FIRST_STOCK_ID)
                .hasFieldOrPropertyWithValue("openPrice", OPEN_PRICE)
                .hasFieldOrPropertyWithValue("closePrice", CLOSE_PRICE)
                .hasFieldOrPropertyWithValue("high", HIGH)
                .hasFieldOrPropertyWithValue("low", LOW);
    }

    @Test
    void insert() throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date d = dateFormat.parse("2020-10-10");
        Stock expectedStock = new Stock();
        expectedStock.setId(3);
        expectedStock.setCompanyId(1);
        expectedStock.setDate(d);
        expectedStock.setOpenPrice(675.4546);
        expectedStock.setClosePrice(656.3456);
        expectedStock.setVolume(432546456);
        expectedStock.setLow(501.32432432);
        expectedStock.setHigh(710.432432);

        stockDaoJdbc.insert(expectedStock);
        Stock actualStock = stockDaoJdbc.getById(expectedStock.getId());
        assertThat(actualStock).isEqualTo(expectedStock);
    }

    @Test
    void update() {
        Stock expectedStock = stockDaoJdbc.getById(FIRST_STOCK_ID + 1);
        expectedStock.setOpenPrice(UPDATE_OPEN_PRICE);
        stockDaoJdbc.update(expectedStock);
        Stock actualStock = stockDaoJdbc.getById(expectedStock.getId());
        assertThat(actualStock).isEqualTo(expectedStock);
    }
}