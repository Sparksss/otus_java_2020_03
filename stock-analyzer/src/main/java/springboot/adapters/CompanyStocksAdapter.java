package springboot.adapters;

import springboot.domains.Stock;

import java.util.Map;

/**
 * Created by ilya on Sep, 2020
 */
public interface CompanyStocksAdapter {
    Stock convertToServiceFormat(Map<String, Map> data);
}
