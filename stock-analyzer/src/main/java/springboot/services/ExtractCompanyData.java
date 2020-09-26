package springboot.services;

import org.springframework.stereotype.Component;
import springboot.adapters.CompanyStocksAdapter;
import springboot.adapters.CompanyStocksAdapterImpl;
import springboot.dao.StockDao;
import springboot.domains.Stock;

import java.util.Map;

/**
 * Created by ilya on Sep, 2020
 */
@Component
public class ExtractCompanyData {

    private CompanyStocksAdapter companyStocksAdapter;
    private StockDao stockDao;

    public ExtractCompanyData(StockDao stockDao) {
        this.stockDao = stockDao;
    }

    public void save(Map<String, Map> data) {
        companyStocksAdapter = new CompanyStocksAdapterImpl();
        Stock stock = companyStocksAdapter.convertToServiceFormat(data);
        this.stockDao.insert(stock);
    }
}
