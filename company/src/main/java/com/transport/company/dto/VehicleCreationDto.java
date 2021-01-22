package com.transport.company.dto;

import com.transport.company.entity.Vehicle;

import java.util.ArrayList;
import java.util.List;

public class VehicleCreationDto {

    private List<Vehicle> vehicles;

    public VehicleCreationDto() {
        vehicles = new ArrayList<Vehicle>();
    }

    public VehicleCreationDto(Vehicle vehicle) {
        this.vehicles = new ArrayList<Vehicle>();
        this.vehicles.add(vehicle);
    }

    public VehicleCreationDto(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public void addVehicle(Vehicle vehicle) {
        this.vehicles.add(vehicle);
    }

    public void setVehicles(List<Vehicle> vehicles) {
        this.vehicles = vehicles;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }
}
