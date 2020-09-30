package springboot.domains;

import lombok.Data;

/**
 * Created by ilya on Sep, 2020
 */
@Data
public class Company {

    private long id;

    private String name;

    private String symbol;

    private String description;

}
