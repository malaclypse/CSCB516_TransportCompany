package com.transport.company.entity;

import javax.persistence.*;
import java.util.Collection;

@Entity
public class Client {
    private long id;
    private String name;
    private ClientTypeEnum clientType;
    private Collection<Freight> freights;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @OneToMany(targetEntity = Freight.class)
    public Collection<Freight> getFreights() {
        return freights;
    }

    public void setFreights(Collection<Freight> freights) {
        this.freights = freights;
    }

    @Column(name = "client_type", nullable = false)
    @Enumerated(EnumType.STRING)
    public ClientTypeEnum getClientType() {
        return clientType;
    }

    public void setClientType(ClientTypeEnum clientType) {
        this.clientType = clientType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
