package springboot.domains;

import lombok.Data;

/**
 * Created by ilya on Sep, 2020
 */
@Data
public class Company {

    private long id;

    private String name;

    private String description;

    public Company() {
    }

    public Company(long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
