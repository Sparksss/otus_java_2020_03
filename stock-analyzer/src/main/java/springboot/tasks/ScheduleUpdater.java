package springboot.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import springboot.domains.CompanyRest;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ilya on Sep, 2020
 */
@Component
public class ScheduleUpdater {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleUpdater.class);

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private final RestTemplate restTemplate;

    public ScheduleUpdater(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {
        logger.info("The time is now {}", dateFormat.format(new Date()));
        CompanyRest s = restTemplate.getForObject("https://www.alphavantage.co/", CompanyRest.class);
//        logger.info("This is company Rest", s);
        System.out.println(s);
    }

}
