package springboot.adapters;

import springboot.domains.Stock;

import java.util.Date;
import java.util.Map;

/**
 * Created by ilya on Sep, 2020
 */
public class DataProviderImpl implements DataProvider {


    private final static String NAME_OF_COLLECTION_DATES = "Weekly Time Series";

    private static final String OPEN_PRICE_KEY = "1. open";
    private static final String HIGH_PRICE_KEY = "2. high";
    private static final String LOW_PRICE_KEY = "3. low";
    private static final String CLOSE_PRICE_KEY = "4. close";
    private static final String DAILY_VOLUME_KEY = "5. volume";

    @Override
    public Stock getData(long companyId,Date reportDay,Map<String, Map> data) throws Exception {

        Stock stock = new Stock();

        Map<String, Map> collectionDates = data.get(NAME_OF_COLLECTION_DATES);
        Map<String, String> collectionPricesPerDay = collectionDates.get(reportDay);
        if(collectionPricesPerDay == null) throw new Exception("Not found for this date");

        String openPrice = collectionPricesPerDay.get(OPEN_PRICE_KEY);
        String closePrice = collectionPricesPerDay.get(CLOSE_PRICE_KEY);
        String dailyVolume = collectionPricesPerDay.get(DAILY_VOLUME_KEY);
        String highPrice = collectionPricesPerDay.get(HIGH_PRICE_KEY);
        String lowPrice = collectionPricesPerDay.get(LOW_PRICE_KEY);

        stock.setCompanyId(companyId);
        stock.setDate(new Date());
        stock.setOpenPrice(Double.parseDouble(openPrice));
        stock.setClosePrice(Double.parseDouble(closePrice));
        stock.setHigh(Double.parseDouble(highPrice));
        stock.setLow(Double.parseDouble(lowPrice));
        stock.setVolume(Double.parseDouble(dailyVolume));
        return stock;
    }
}
