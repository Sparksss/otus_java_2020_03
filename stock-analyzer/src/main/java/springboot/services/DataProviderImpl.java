package springboot.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import springboot.adapters.CompanyStocksAdapter;
import springboot.adapters.CompanyStocksAdapterImpl;
import springboot.config.AlphavantageConf;
import springboot.dao.CompanyDao;
import springboot.dao.StockDao;
import springboot.domains.Company;
import springboot.domains.Stock;
import springboot.parameters.Periods;
import springboot.tasks.ScheduleUpdater;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by ilya on Sep, 2020
 */
@Component
public class DataProviderImpl implements DataProvider {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleUpdater.class);

    private final RestTemplate restTemplate;
    private final AlphavantageConf alphavantageConf;
    private final CompanyStocksAdapter companyStocksAdapter;
    private final StockDao stockDao;
    private final CompanyDao companyDao;

    public DataProviderImpl(RestTemplate restTemplate, StockDao stockDao, CompanyDao companyDao, AlphavantageConf alphavantageConf) {
        this.stockDao = stockDao;
        this.restTemplate = restTemplate;
        this.companyDao = companyDao;
        this.alphavantageConf = alphavantageConf;
        this.companyStocksAdapter = new CompanyStocksAdapterImpl();
    }

    @Override
    public Map<String, Map> getData(Company company) {
        String apiKey =  this.alphavantageConf.getApikey();
        String url = this.alphavantageConf.getUrl();
        return restTemplate.getForObject(String.format("%s?function=%s&symbol=%s&apikey=%s", url, Periods.valueOf("WEEKLY").getPeriod(), company.getSymbol(), apiKey), Map.class);
    }

    @Override
    public void collectData(Date reportDay) {
        List<Company> companies = companyDao.getAll();
        for(Company company : companies) {
            try {
                Stock stock = companyStocksAdapter.getData(company.getId(), reportDay, this.getData(company));
                saveData(stock);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }

    private void saveData(Stock stock) {
        if(stock != null) {
            stockDao.insert(stock);
        }
    }
}
