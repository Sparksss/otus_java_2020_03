package springboot.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by ilya on Sep, 2020
 */@JsonIgnoreProperties(ignoreUnknown = true)
public class CompanyRest {
     private String currency;
     private String symbol;
     private double previousClose;
     private double regularMarketPrice;

    public CompanyRest() {
    }

    public CompanyRest(String currency, String symbol, double previousClose, double regularMarketPrice) {
        this.currency = currency;
        this.symbol = symbol;
        this.previousClose = previousClose;
        this.regularMarketPrice = regularMarketPrice;
    }

    public String getCurrency() {
        return currency;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getPreviousClose() {
        return previousClose;
    }

    public double getRegularMarketPrice() {
        return regularMarketPrice;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setPreviousClose(double previousClose) {
        this.previousClose = previousClose;
    }

    public void setRegularMarketPrice(double regularMarketPrice) {
        this.regularMarketPrice = regularMarketPrice;
    }

    @Override
    public String toString() {
        return "CompanyRest{" +
                "currency='" + currency + '\'' +
                ", symbol='" + symbol + '\'' +
                ", previousClose=" + previousClose +
                ", regularMarketPrice=" + regularMarketPrice +
                '}';
    }
}
