package org.example.entity;

import jakarta.persistence.*;

@Entity
@Table(name="client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;

    @Column(name="has_paid")
    private boolean hasPaid;

    public Client() {
    }

    public Client(String name, Company company, boolean hasPaid) {
        this.name = name;
        this.company = company;
        this.hasPaid = hasPaid;
    }

    //getters & setters
    public boolean getHasPaid() {
        return hasPaid;
    }

    public void setHasPaid(boolean hasPaid) {
        this.hasPaid = hasPaid;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    //to-string method
    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", company=" + company +
                ", hasPaid=" + hasPaid +
                '}';
    }
}
