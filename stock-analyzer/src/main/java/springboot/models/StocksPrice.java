package springboot.models;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by ilya on Sep, 2020
 */
@Entity
@Table(name = "stocks_price")
public class StocksPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "company_id")
    private int companyId;

    @Column(name = "date")
    private Date date;

    @Column(name = "open_price")
    private int openPrice;

    @Column(name = "close_price")
    private int closePrice;

    public StocksPrice() {
    }

    public StocksPrice(int companyId, Date date, int openPrice, int closePrice) {
        this.companyId = companyId;
        this.date = date;
        this.openPrice = openPrice;
        this.closePrice = closePrice;
    }
}
