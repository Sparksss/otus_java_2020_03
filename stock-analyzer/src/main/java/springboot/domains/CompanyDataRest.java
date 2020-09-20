package springboot.domains;

/**
 * Created by ilya on Sep, 2020
 */
public class CompanyDataRest {

    private double openPrice;
    private double closePrice;
    private double high;
    private double low;
    private double volume;

    public CompanyDataRest() {}

    public CompanyDataRest(double openPrice, double closePrice, double high, double low, double volume) {
        this.openPrice = openPrice;
        this.closePrice = closePrice;
        this.high = high;
        this.low = low;
        this.volume = volume;
    }

    public double getOpenPrice() {
        return openPrice;
    }

    public double getClosePrice() {
        return closePrice;
    }

    public double getHigh() {
        return high;
    }

    public double getLow() {
        return low;
    }

    public double getVolume() {
        return volume;
    }

    public void setOpenPrice(double openPrice) {
        this.openPrice = openPrice;
    }

    public void setClosePrice(double closePrice) {
        this.closePrice = closePrice;
    }

    public void setHigh(double high) {
        this.high = high;
    }

    public void setLow(double low) {
        this.low = low;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }
}
