package com.transport.company.entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Freight {
    private long id;
    private FreightTypeEnum type;
    private Boolean delivered;
    private Date dateDelivered;
    private Client client;
    private String startLocation;
    private String destination;
    private long productWeight;
    private double price;
    private Driver driver;
    private Boolean paid;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "freight_type", nullable = false)
    @Enumerated(EnumType.STRING)
    public FreightTypeEnum getType() {
        return type;
    }

    public void setType(FreightTypeEnum type) {
        this.type = type;
    }

    @Column(name = "is_delivered", nullable = false)
    public Boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(Boolean delivered) {
        this.delivered = delivered;
    }

    @Column(name = "date_delivered")
    @DateTimeFormat(pattern = "YYYY/MM/dd")
    public Date getDateDelivered() {
        return dateDelivered;
    }

    public void setDateDelivered(Date dateDelivered) {
        this.dateDelivered = dateDelivered;
    }

    @ManyToOne(targetEntity = Client.class)
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(String startLocation) {
        this.startLocation = startLocation;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public long getProductWeight() {
        return productWeight;
    }

    public void setProductWeight(long productWeight) {
        this.productWeight = productWeight;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @ManyToOne(targetEntity = Driver.class)
    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public Boolean isPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }
}
