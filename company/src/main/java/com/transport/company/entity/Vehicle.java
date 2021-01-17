package com.transport.company.entity;

import javax.persistence.*;

@Entity
public class Vehicle {
    private long id;
    private VehicleTypeEnum vehicleType;
    private Driver driver;
    private Company company;

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

    @OneToOne(targetEntity=Driver.class)
    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    @ManyToOne(targetEntity=Company.class)
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
