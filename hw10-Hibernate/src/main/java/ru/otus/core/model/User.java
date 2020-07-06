package ru.otus.core.model;


import javax.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;


    @Column(name = "id_address")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private long idAddress;

    @Column(name = "phone")
    @OneToMany()
    private long idPhone;

    public User() {
    }

    public User(long id, String name, long idAddress, long idPhone) {
        this.id = id;
        this.name = name;
        this.idAddress = idAddress;
        this.idPhone = idPhone;
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getIdAddress() {
        return this.idAddress;
    }

    public void setIdAddress(long idAddress) {
        this.idAddress = idAddress;
    }

    public long getIdPhone() {
        return this.idPhone;
    }

    public void setIdPhone(long idPhone) {
        this.idPhone = idPhone;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
