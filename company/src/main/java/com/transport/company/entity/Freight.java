package com.transport.company.entity;

import javax.persistence.Entity;

@Entity
public class Freight {
    private long id;
    private Driver driver;
    private Vehicle vehicle;
    private FreightType type;
    private Boolean IsDelivered;
}
