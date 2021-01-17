package com.transport.company.entity;

import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "company")
public class Company {
    private long id;
    private String name;
    private List<Driver> drivers;
    private List<Vehicle> vehicles;
    private List<Client> clients;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @OneToMany(targetEntity = Vehicle.class)
    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    @OneToMany(targetEntity = Driver.class)
    public List<Driver> getDrivers() {
        return drivers;
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

    @OneToMany(targetEntity = Client.class)
    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }
}
