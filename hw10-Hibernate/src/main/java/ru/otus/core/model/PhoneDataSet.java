package ru.otus.core.model;

import javax.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "phones")
public class PhoneDataSet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_phone", nullable = false)
    private long id;

    @Column
    private String number;

    @ManyToOne()
    @JoinColumn(table = "phones", name="link")
    private User user;

    public PhoneDataSet() {}

    public PhoneDataSet(String number) {
        this.number = number;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setUser(User user) {
        this.user = user;
    }
    public User getUser(){
        return this.user;
    }


}
