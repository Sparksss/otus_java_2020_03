package ru.otus.core.model;

import javax.persistence.*;

@Entity
@Table(name = "address")
public class AddressDataSet {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_address")
    private long id;

    @Column(name = "address")
    private String address;
}
