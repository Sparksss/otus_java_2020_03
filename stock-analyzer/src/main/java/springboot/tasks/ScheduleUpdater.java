package springboot.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import springboot.dao.CompanyDao;
import springboot.domains.Company;
import springboot.parameters.Periods;
import springboot.services.ExtractCompanyData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by ilya on Sep, 2020
 */
@Component
public class ScheduleUpdater {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleUpdater.class);

    private final String URL = "https://www.alphavantage.co/query";

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private final RestTemplate restTemplate;

    private ExtractCompanyData extractCompanyData;

    private CompanyDao companyDao;

    private StringBuilder preparedURLWithParams = new StringBuilder();

    public ScheduleUpdater(RestTemplate restTemplate, ExtractCompanyData extractCompanyData, CompanyDao companyDao) {
        this.restTemplate = restTemplate;
        this.extractCompanyData = extractCompanyData;
        this.companyDao = companyDao;
    }

    @Scheduled(fixedRateString = "${schedule.fixedRate}")
    public void reportCurrentTime() {
        logger.info("The time is now {}", dateFormat.format(new Date()));
        List<Company> companies = companyDao.getAll();
        for(Company company : companies) {
            preparedURLWithParams.append(URL + "?function=" + Periods.valueOf("DAILY") + "&symbol=" + company.getSymbol() + "apikey=S1RP7VOYRC84TD3Z");
            Map<String, Map> data = restTemplate.getForObject(preparedURLWithParams.toString(), Map.class);
            extractCompanyData.save(data);
            preparedURLWithParams.setLength(0);
        }
    }

}
