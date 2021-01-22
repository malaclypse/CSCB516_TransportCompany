package com.transport.company.dto;

import com.transport.company.entity.Driver;

import java.util.ArrayList;
import java.util.List;

public class DriverCreationDto {

    private List<Driver> drivers;

    public DriverCreationDto() {
        drivers = new ArrayList<Driver>();
    }

    public DriverCreationDto(Driver driver) {
        this.drivers = new ArrayList<Driver>();
        this.drivers.add(driver);
    }

    public DriverCreationDto(List<Driver> drivers) {
        this.drivers = drivers;
    }

    public void addDriver(Driver driver) {
        this.drivers.add(driver);
    }

    public void setDrivers(List<Driver> drivers) {
        this.drivers = drivers;
    }

    public List<Driver> getDrivers() {
        return drivers;
    }
}
