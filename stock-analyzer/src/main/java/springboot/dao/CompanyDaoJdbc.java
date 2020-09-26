package springboot.dao;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import springboot.domains.Company;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by ilya on Sep, 2020
 */
@SuppressWarnings({"SqlNoDataSourceInspection", "ConstantConditions", "SqlDialectInspection"})
@Repository
public class CompanyDaoJdbc implements CompanyDao {

    private final JdbcOperations jdbc;

    public CompanyDaoJdbc(JdbcOperations jdbcOperations)
    {
        this.jdbc = jdbcOperations;
    }


    @Override
    public Company getById(long id) {
        return jdbc.queryForObject("select * from companies where id = ?", new Object[]{id}, new CompanyMapper());
    }

    @Override
    public void insert(Company company) {
        jdbc.update("insert into companies (name, description) values (?, ?)", company.getName(), company.getDescription());
    }

    @Override
    public void update(Company company) {
        jdbc.update("update companies set name = ?, description = ? where id = ?", company.getName(), company.getDescription(), company.getId());
    }

    @Override
    public List<Company> getAll() {
        return jdbc.query("select * from companies", new CompanyMapper());
    }

    private static class CompanyMapper implements RowMapper<Company> {

        @Override
        public Company mapRow(ResultSet resultSet, int i) throws SQLException {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            String symbol = resultSet.getString("symbol");
            String description = resultSet.getString("description");
            Company company = new Company();
            company.setId(id);
            company.setName(name);
            company.setSymbol(symbol);
            company.setDescription(description);
            return company;
        }
    }
}
