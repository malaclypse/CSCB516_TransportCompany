package com.transport.company.controller;
import com.transport.company.entity.Company;
import com.transport.company.entity.Driver;
import com.transport.company.entity.Vehicle;
import com.transport.company.exception.*;
import com.transport.company.repository.ClientRepository;
import com.transport.company.repository.CompanyRepository;
import com.transport.company.repository.DriverRepository;
import com.transport.company.repository.VehicleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class VehicleController {
    @Autowired
    private VehicleRepository vehicleRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private CompanyRepository companyRepository;

    @GetMapping("/company/{companyId}/vehicle")
    public List<Vehicle> getAllDrivers(@PathVariable(value = "companyId") Long companyId)
            throws ResourceNotFoundException {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found for this id :: " + companyId));

        return vehicleRepository.findAll();
    }

    @GetMapping("/company/{companyId}/vehicle/{vehicleId}")
    public ResponseEntity<Vehicle> getDriverById(@PathVariable(value = "companyId") Long companyId,
                                                @PathVariable(value = "vehicleId") Long vehicleId)
            throws ResourceNotFoundException {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found for this id :: " + companyId));

        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found for this id :: " + vehicleId));
        return ResponseEntity.ok().body(vehicle);
    }

    @PostMapping("/company/{companyId}/vehicle")
    public Vehicle createDriver(@PathVariable(value = "companyId") Long companyId,
                               @Validated @RequestBody Vehicle vehicle) throws ResourceNotFoundException {

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found for this id :: " + companyId));

        Driver driver = driverRepository.findById(vehicle.getDriver().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found for this id :: " + vehicle.getDriver().getId()));

        vehicle.setCompany(company);

        return vehicleRepository.save(vehicle);
    }

    @PutMapping("/company/{companyId}/vehicle/{vehicleId}")
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable(value = "companyId") Long companyId,
                                                @PathVariable(value = "vehicleId") Long vehicleId,
                                                @Validated @RequestBody Vehicle vehicleDetails) throws ResourceNotFoundException {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found for this id :: " + companyId));

        Driver driver = driverRepository.findById(vehicleDetails.getDriver().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found for this id :: " + vehicleDetails.getDriver().getId()));

        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found for this id :: " + vehicleId));

        vehicle.setVehicleType(vehicleDetails.getVehicleType());
        vehicle.setCompany(company);
        vehicle.setDriver(driver);

        final Vehicle updatedVehicle = vehicleRepository.save(vehicle);
        return ResponseEntity.ok(updatedVehicle);
    }

    @DeleteMapping("/company/{companyId}/vehicle/{vehicleId}")
    public Map<String, Boolean> deleteVehicle(@PathVariable(value = "companyId") Long companyId,
                                             @PathVariable(value = "driverId") Long vehicleId)
            throws ResourceNotFoundException {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found for this id :: " + companyId));

        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found for this id :: " + vehicleId));

        vehicleRepository.delete(vehicle);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
