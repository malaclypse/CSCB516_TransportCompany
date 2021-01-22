package com.transport.company.service;

import com.transport.company.entity.Client;
import com.transport.company.entity.Vehicle;
import com.transport.company.exception.ResourceNotFoundException;
import com.transport.company.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class VehicleService {
    @Autowired
    private VehicleRepository vehicleRepository;
    public List<Vehicle> getVehicles() throws ResourceNotFoundException {
           return vehicleRepository.findAll();
    }

    public Page<Vehicle> findPaginated(final int pageNumber, final int pageSize,
                                      final String sortField, final String sortDirection) {

        final Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        final Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
        return vehicleRepository.findAll(pageable);
    }

    public Vehicle getVehicle(Long vehicleId) throws ResourceNotFoundException {
        return vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found for this id :: " + vehicleId));
    }

    public Map<String, Boolean> deleteVehicle(Long vehicleId) throws ResourceNotFoundException {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found for this id :: " + vehicleId));

        vehicleRepository.delete(vehicle);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    public Vehicle updateVehicle(Long vehicleId, Vehicle vehicleDetails) throws ResourceNotFoundException {
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found for this id :: " + vehicleId));

        vehicle.setVehicleType(vehicleDetails.getVehicleType());
        vehicle.setBrand(vehicleDetails.getBrand());
        return vehicleRepository.save(vehicle);
    }

    public Vehicle createVehicle( Vehicle vehicle) throws ResourceNotFoundException {
        return vehicleRepository.save(vehicle);
    }
}
