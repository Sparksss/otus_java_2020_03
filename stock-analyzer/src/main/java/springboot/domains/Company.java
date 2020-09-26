package springboot.domains;

import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * Created by ilya on Sep, 2020
 */
@Data
@RequiredArgsConstructor
public class Company {

    private long id;

    private String name;

    private String description;

}
