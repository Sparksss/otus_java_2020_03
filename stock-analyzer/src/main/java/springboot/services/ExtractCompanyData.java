package springboot.services;

import org.springframework.stereotype.Component;
import springboot.domains.CompanyDataRest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by ilya on Sep, 2020
 */
@Component
public class ExtractCompanyData {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy:MM:dd");
    private final String NAME_OF_COLLECTION_DATES = "Time Series (Daily)";

    private static final String OPEN_PRICE_KEY = "1. open";
    private static final String HIGH_PRICE_KEY = "2. high";
    private static final String LOW_PRICE_KEY = "3. low";
    private static final String CLOSE_PRICE_KEY = "4. close";
    private static final String DAILY_VOLUME_KEY = "5. volume";


    public CompanyDataRest extract(Map<String, Map> data) {

        String s = simpleDateFormat.format(new Date());

        Map<String, Map> collectionDates = data.get(NAME_OF_COLLECTION_DATES);
        Map<String, String> collectionPricesPerDay = collectionDates.get("2020-09-18");
        String openPrice = collectionPricesPerDay.get(OPEN_PRICE_KEY);
        String closePrice = collectionPricesPerDay.get(CLOSE_PRICE_KEY);
        String dailyVolume = collectionPricesPerDay.get(DAILY_VOLUME_KEY);
        String highPrice = collectionPricesPerDay.get(HIGH_PRICE_KEY);
        String lowPrice = collectionPricesPerDay.get(LOW_PRICE_KEY);

        return new CompanyDataRest(Double.parseDouble(openPrice), Double.parseDouble(closePrice), Double.parseDouble(highPrice), Double.parseDouble(lowPrice), Double.parseDouble(dailyVolume));
    }
}
