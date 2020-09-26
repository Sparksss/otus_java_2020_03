package springboot.services;

import org.springframework.stereotype.Component;
import springboot.adapters.CompanyStocksAdapter;
import springboot.adapters.CompanyStocksAdapterImpl;
import springboot.domains.StocksPrice;

import java.util.Map;

/**
 * Created by ilya on Sep, 2020
 */
@Component
public class ExtractCompanyData {

    CompanyStocksAdapter companyStocksAdapter;

    public void save(Map<String, Map> data) {
        companyStocksAdapter = new CompanyStocksAdapterImpl();
        StocksPrice stocksPrice = companyStocksAdapter.convertToServiceFormat(data);
    }
}
