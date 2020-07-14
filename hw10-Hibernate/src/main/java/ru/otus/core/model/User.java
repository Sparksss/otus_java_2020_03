package ru.otus.core.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @OneToOne(targetEntity = AddressDataSet.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_address")
    private AddressDataSet address;


    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<PhoneDataSet> phones;

    public User() {
    }

    public User(String name, AddressDataSet address, List<PhoneDataSet> phones) {
        this.name = name;
        this.address = address;
        this.phones = phones;
        phones.stream().forEach(p -> p.setUser(this));
    }

    public User(String name, AddressDataSet address) {
        this.name = name;
        this.address = address;
        this.phones = null;
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


    public AddressDataSet getAddress() {
        return this.address;
    }

    public void setAddress(AddressDataSet address) {
        this.address = address;
    }

    public List<PhoneDataSet> getPhones() {
        return this.phones;
    }

    public void setPhones(List<PhoneDataSet> phones) {
        this.phones = phones;
        for(PhoneDataSet p : this.phones) {
            p.setUser(this);
        }
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for(PhoneDataSet phone : this.phones) {
            s.append(phone.getNumber() + " ");
        }

        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", street' " + this.address.getStreet() +
                ", phones' " + s.toString() +
                '}';
    }
}
