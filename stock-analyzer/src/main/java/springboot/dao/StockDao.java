package springboot.dao;

import springboot.domains.Stock;

import java.util.List;

/**
 * Created by ilya on Sep, 2020
 */
public interface StockDao {
    Stock getById(long id);
    void insert(Stock stock);
    void update(Stock stock);
    List<Stock> getAll();
}
