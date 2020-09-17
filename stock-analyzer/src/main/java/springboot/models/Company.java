package springboot.models;

import javax.persistence.*;
import java.sql.Date;

/**
 * Created by ilya on Sep, 2020
 */
@Entity
@Table(name = "companies")
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "establish_date")
    private Date establish_date;

    public Company() {}

    public Company(String name, String description, Date establish_date) {
        this.name = name;
        this.description = description;
        this.establish_date = establish_date;
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

    public Date getEstablish_date() {
        return establish_date;
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

    public void setEstablish_date(Date establish_date) {
        this.establish_date = establish_date;
    }
}
