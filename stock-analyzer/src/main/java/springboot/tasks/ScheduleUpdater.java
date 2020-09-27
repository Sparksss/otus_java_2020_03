package springboot.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import springboot.services.StocksService;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ilya on Sep, 2020
 */
@Component
public class ScheduleUpdater {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleUpdater.class);

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private StocksService stocksService;

    public ScheduleUpdater(StocksService stocksService) {
        this.stocksService = stocksService;
    }

    @Scheduled(fixedRateString = "${schedule.fixedRate}")
    public void reportCurrentTime() {
        logger.info("Next run scheduler {}", dateFormat.format(new Date()));
        stocksService.collectPrices();
    }

}
