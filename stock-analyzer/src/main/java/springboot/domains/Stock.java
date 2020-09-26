package springboot.domains;

import lombok.Data;

import java.util.Date;


/**
 * Created by ilya on Sep, 2020
 */
@Data
public class Stock {
    private long id;
    private long companyId;
    private Date date;
    private double openPrice;
    private double closePrice;
    private double high;
    private double low;
    private double volume;
}
