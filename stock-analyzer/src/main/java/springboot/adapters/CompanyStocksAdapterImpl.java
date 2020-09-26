package springboot.adapters;

import springboot.domains.StocksPrice;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * Created by ilya on Sep, 2020
 */
public class CompanyStocksAdapterImpl implements CompanyStocksAdapter {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy:MM:dd");
    private final String NAME_OF_COLLECTION_DATES = "Time Series (Daily)";

    private static final String OPEN_PRICE_KEY = "1. open";
    private static final String HIGH_PRICE_KEY = "2. high";
    private static final String LOW_PRICE_KEY = "3. low";
    private static final String CLOSE_PRICE_KEY = "4. close";
    private static final String DAILY_VOLUME_KEY = "5. volume";

    @Override
    public StocksPrice convertToServiceFormat(Map<String, Map> data) {

        StocksPrice stocksPrice = new StocksPrice();
        String currentDate = simpleDateFormat.format(new Date());

        Map<String, Map> collectionDates = data.get(NAME_OF_COLLECTION_DATES);
        Map<String, String> collectionPricesPerDay = collectionDates.get(currentDate);
        String openPrice = collectionPricesPerDay.get(OPEN_PRICE_KEY);
        String closePrice = collectionPricesPerDay.get(CLOSE_PRICE_KEY);
        String dailyVolume = collectionPricesPerDay.get(DAILY_VOLUME_KEY);
        String highPrice = collectionPricesPerDay.get(HIGH_PRICE_KEY);
        String lowPrice = collectionPricesPerDay.get(LOW_PRICE_KEY);
        stocksPrice.setDate(new Date());
        stocksPrice.setOpenPrice(Double.parseDouble(openPrice));
        stocksPrice.setClosePrice(Double.parseDouble(closePrice));
        stocksPrice.setHigh(Double.parseDouble(highPrice));
        stocksPrice.setLow(Double.parseDouble(lowPrice));
        stocksPrice.setVolume(Double.parseDouble(dailyVolume));
        return stocksPrice;
    }
}