package springboot.dao;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import springboot.domains.Stock;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Created by ilya on Sep, 2020
 */
@Repository
public class StockDaoJdbc implements StockDao {

    private final JdbcOperations jdbc;

    public StockDaoJdbc(JdbcOperations jdbcOperations)
    {
        this.jdbc = jdbcOperations;
    }


    @Override
    public Stock getById(long id) {
        return this.jdbc.queryForObject("select * from stocks_price", Stock.class);
    }

    @Override
    public void insert(Stock stock) {
        jdbc.update("insert into stocks_price (company_id, date_price, open, close, high, low, volume) values (?, ?, ?, ?, ?, ?, ?)",
                stock.getCompanyId(),
                stock.getDate(),
                stock.getOpenPrice(),
                stock.getClosePrice(),
                stock.getHigh(),
                stock.getLow(),
                stock.getVolume());
    }

    @Override
    public void update(Stock stock) {
        jdbc.update("update stocks_price set company_id = ?, date_price = ?, open = ?, close = ?, high = ?, low = ?, volume = ?  where id = ?",
                stock.getCompanyId(),
                stock.getDate(),
                stock.getOpenPrice(),
                stock.getClosePrice(),
                stock.getHigh(),
                stock.getLow(),
                stock.getVolume(),
                stock.getId());
    }

    @Override
    public List<Stock> getAll() {
        return jdbc.query("select * from stocks_price", new StockDaoJdbc.StockMapper());
    }

    private static class StockMapper implements RowMapper<Stock> {

        @Override
        public Stock mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            long companyId = resultSet.getInt("company_id");
            Date date = resultSet.getDate("date_price");
            double open = resultSet.getDouble("open");
            double close = resultSet.getDouble("close");
            double high = resultSet.getDouble("high");
            double low = resultSet.getDouble("low");
            double volume = resultSet.getDouble("volume");

            Stock stock = new Stock();
            stock.setId(id);
            stock.setCompanyId(companyId);
            stock.setDate(date);
            stock.setOpenPrice(open);
            stock.setClosePrice(close);
            stock.setHigh(high);
            stock.setLow(low);
            stock.setVolume(volume);
            return stock;
        }
    }
}
