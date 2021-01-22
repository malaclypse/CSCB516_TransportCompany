package com.transport.company.entity;

import javax.persistence.*;

@Entity
public class Vehicle {
    private long id;
    private VehicleTypeEnum vehicleType;
    private String brand;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Column(name = "vehicle_type", nullable = false)
    @Enumerated(EnumType.STRING)
    public VehicleTypeEnum getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleTypeEnum vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
