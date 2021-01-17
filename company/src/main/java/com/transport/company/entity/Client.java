package com.transport.company.entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Client {
    private long id;
    private Company company;
    private Collection<Freight> freights;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @ManyToOne(targetEntity = Company.class)
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @OneToMany(targetEntity = Freight.class)
    public Collection<Freight> getFreights() {
        return freights;
    }

    public void setFreights(Collection<Freight> freights) {
        this.freights = freights;
    }
}
