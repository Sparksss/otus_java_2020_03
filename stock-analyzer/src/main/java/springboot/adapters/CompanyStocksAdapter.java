package springboot.adapters;

import springboot.domains.Stock;

import java.util.Date;
import java.util.Map;

/**
 * Created by ilya on Sep, 2020
 */
public interface CompanyStocksAdapter {
    Stock convertToServiceFormat(long companyId, Date reportDay , Map<String, Map> data) throws Exception;
}
