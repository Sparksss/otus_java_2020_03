package springboot.adapters;

import springboot.domains.StocksPrice;

import java.util.Map;

/**
 * Created by ilya on Sep, 2020
 */
public interface CompanyStocksAdapter {
    StocksPrice convertToServiceFormat(Map<String, Map> data);
}
