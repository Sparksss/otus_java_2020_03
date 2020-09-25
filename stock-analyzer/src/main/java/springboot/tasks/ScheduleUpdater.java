package springboot.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import springboot.domains.CompanyDataRest;
import springboot.services.ExtractCompanyData;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by ilya on Sep, 2020
 */
@Component
public class ScheduleUpdater {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleUpdater.class);

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private final RestTemplate restTemplate;

    private ExtractCompanyData extractCompanyData;

    public ScheduleUpdater(RestTemplate restTemplate, ExtractCompanyData extractCompanyData) {
        this.restTemplate = restTemplate;
        this.extractCompanyData = extractCompanyData;
    }

    @Scheduled(fixedRateString = "${schedule.fixedRate}")
    public void reportCurrentTime() {
        logger.info("The time is now {}", dateFormat.format(new Date()));
        Map<String, Map> s = restTemplate.getForObject("https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=AAPL&apikey=S1RP7VOYRC84TD3Z", Map.class);
        CompanyDataRest companyDataRest = extractCompanyData.extract(s);
        System.out.println(companyDataRest.getClosePrice());
    }

}
