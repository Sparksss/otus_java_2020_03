package springboot.domains;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import java.sql.Date;

/**
 * Created by ilya on Sep, 2020
 */
@RequiredArgsConstructor
@Data
public class StocksPrice {

    private long id;

    private int companyId;

    private Date date;

    private int openPrice;

    private int closePrice;
}
