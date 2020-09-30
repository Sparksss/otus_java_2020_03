package springboot.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import springboot.domains.Company;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by ilya on Sep, 2020
 */
@JdbcTest
@Import({CompanyDaoJdbc.class})
class CompanyDaoJdbcTest {

    private static final int INITIAL_COUNT_COMPANIES = 6;
    private static long TESLA_ID = 1;
    private static String NEW_NAME_COMPANY = "Super Company";

    @Autowired
    private CompanyDaoJdbc companyDaoJdbc;

    @Test
    void getById() {
        Company actualCompany = companyDaoJdbc.getById(TESLA_ID);
        assertThat(actualCompany)
                .hasFieldOrPropertyWithValue("id", TESLA_ID)
                .hasFieldOrPropertyWithValue("symbol", "TSLA");
    }

    @Test
    void insert() {
        Company expectedCompany = new Company();
        expectedCompany.setId(INITIAL_COUNT_COMPANIES + 1);
        expectedCompany.setName("New company");
        expectedCompany.setSymbol("COMP");
        expectedCompany.setDescription("This is basic company");
        companyDaoJdbc.insert(expectedCompany);
        Company actualCompany = companyDaoJdbc.getById(expectedCompany.getId());
        assertThat(actualCompany).isEqualTo(expectedCompany);
    }

    @Test
    void update() {
        Company expectedCompany = companyDaoJdbc.getById(1);
        expectedCompany.setName(NEW_NAME_COMPANY);
        companyDaoJdbc.update(expectedCompany);
        assertThat(expectedCompany).hasFieldOrPropertyWithValue("name", NEW_NAME_COMPANY);
    }

    @Test
    void count() {
        int count = companyDaoJdbc.count();
        assertThat(count).isEqualTo(INITIAL_COUNT_COMPANIES);
    }
}