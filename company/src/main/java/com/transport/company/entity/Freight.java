package com.transport.company.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Freight {
    private long id;
    private Driver driver;
    private Vehicle vehicle;
    private FreightType type;
    private Boolean IsDelivered;
    private Date DateDelivered;
    private Client client;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @OneToOne(targetEntity = Driver.class)
    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    @OneToOne(targetEntity = Vehicle.class)
    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @Column(name = "freight_type", nullable = false)
    @Enumerated(EnumType.STRING)
    public FreightType getType() {
        return type;
    }

    public void setType(FreightType type) {
        this.type = type;
    }

    @Column(name = "is_delivered", nullable = false)
    public Boolean getDelivered() {
        return IsDelivered;
    }

    public void setDelivered(Boolean delivered) {
        IsDelivered = delivered;
    }

    @Column(name = "date_delivered", nullable = true)
    public Date getDateDelivered() {
        return DateDelivered;
    }

    public void setDateDelivered(Date dateDelivered) {
        DateDelivered = dateDelivered;
    }

    @ManyToOne(targetEntity = Client.class)
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
