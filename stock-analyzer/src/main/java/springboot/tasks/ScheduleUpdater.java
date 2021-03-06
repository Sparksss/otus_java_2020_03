package springboot.tasks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import springboot.services.DataProvider;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ilya on Sep, 2020
 */
@Component
public class ScheduleUpdater {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleUpdater.class);

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private final DataProvider dataProvider;

    public ScheduleUpdater(DataProvider dataProvider) {
        this.dataProvider = dataProvider;
    }

    @Scheduled(fixedRateString = "${schedule.fixedRate}")
    public void reportCurrentTime() {
        logger.info("Next run scheduler {}", dateFormat.format(new Date()));
        Calendar calendar = getYesterdayDate();
        if(isExchangeWorkDay(calendar)) {
            dataProvider.collectData(calendar.getTime());
        }
    }

    private boolean isExchangeWorkDay(Calendar calendar) {
       return calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY;
    }

    private Calendar getYesterdayDate() {
        final Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        return cal;
    }

}
