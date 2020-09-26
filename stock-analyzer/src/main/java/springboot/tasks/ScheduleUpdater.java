package springboot.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import springboot.config.AlphavantageConf;
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

    private AlphavantageConf alphavantageConf;

    private static final Logger logger = LoggerFactory.getLogger(ScheduleUpdater.class);

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private final RestTemplate restTemplate;

    private ExtractCompanyData extractCompanyData;

    private CompanyDao companyDao;

    private StringBuilder preparedURLWithParams = new StringBuilder();

    public ScheduleUpdater(RestTemplate restTemplate, ExtractCompanyData extractCompanyData, CompanyDao companyDao, AlphavantageConf alphavantageConf) {
        this.restTemplate = restTemplate;
        this.extractCompanyData = extractCompanyData;
        this.companyDao = companyDao;
        this.alphavantageConf = alphavantageConf;
    }

    @Scheduled(fixedRateString = "${schedule.fixedRate}")
    public void reportCurrentTime() {
        logger.info("Next run scheduler {}", dateFormat.format(new Date()));
        List<Company> companies = companyDao.getAll();
        String apiKey =  this.alphavantageConf.getApikey();
        String url = this.alphavantageConf.getUrl();
        for(Company company : companies) {
            preparedURLWithParams.append(url + "?function=" + Periods.valueOf("DAILY") + "&symbol=" + company.getSymbol() + "&apikey=" + apiKey);
            Map<String, Map> data = restTemplate.getForObject(preparedURLWithParams.toString(), Map.class);
            extractCompanyData.save(data);
            preparedURLWithParams.setLength(0);
        }
    }

}
