package springboot.dao;

import springboot.domains.Company;

import java.util.List;

/**
 * Created by ilya on Sep, 2020
 */
public interface CompanyDao {
    Company getById(long id);
    void insert(Company company);
    void update(Company company);
    List<Company> getAll();
    int count();
}
