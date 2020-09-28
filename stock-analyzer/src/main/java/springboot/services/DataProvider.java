package springboot.services;

import springboot.domains.Company;

import java.util.Date;
import java.util.Map;

/**
 * Created by ilya on Sep, 2020
 */
public interface DataProvider {
    Map<String, Map> getData(Company company);
    void collectData(Date reportDay);
}
