package com.transport.company.controller;
import com.transport.company.entity.Company;
import com.transport.company.entity.Driver;
import com.transport.company.exception.*;
import com.transport.company.repository.CompanyRepository;
import com.transport.company.repository.DriverRepository;
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
public class DriverController {
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private CompanyRepository companyRepository;

    @GetMapping("/company/{companyId}/driver")
    public List<Driver> getAllDrivers(@PathVariable(value = "companyId") Long companyId)
            throws ResourceNotFoundException {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found for this id :: " + companyId));

        return driverRepository.findAll();
    }

    @GetMapping("/company/{companyId}/driver/{driverId}")
    public ResponseEntity<Driver> getDriverById(@PathVariable(value = "companyId") Long companyId,
                                                 @PathVariable(value = "driverId") Long driverId)
            throws ResourceNotFoundException {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found for this id :: " + companyId));

        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found for this id :: " + driverId));
        return ResponseEntity.ok().body(driver);
    }

    @PostMapping("/company/{companyId}/driver")
    public Driver createDriver(@PathVariable(value = "companyId") Long companyId,
                               @Validated @RequestBody Driver driver) throws ResourceNotFoundException {

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found for this id :: " + companyId));
        driver.setCompany(company);

        return driverRepository.save(driver);
    }

    @PutMapping("/company/{companyId}/driver/{driverId}")
    public ResponseEntity<Driver> updateCompany(@PathVariable(value = "companyId") Long companyId,
                                                 @PathVariable(value = "driverId") Long driverId,
                                                 @Validated @RequestBody Driver driverDetails) throws ResourceNotFoundException {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found for this id :: " + companyId));

        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found for this id :: " + driverId));

        driver.setFirstName(driverDetails.getFirstName());
        driver.setLastName(driverDetails.getLastName());
        driver.setEmailId(driverDetails.getEmailId());
        driver.setCompany(company);
        driver.setDriverQualifications(driverDetails.getDriverQualifications());

        final Driver updatedDriver = driverRepository.save(driver);
        return ResponseEntity.ok(updatedDriver);
    }

    @DeleteMapping("/company/{companyId}/driver/{driverId}")
    public Map<String, Boolean> deleteDriver(@PathVariable(value = "companyId") Long companyId,
                                             @PathVariable(value = "driverId") Long driverId)
            throws ResourceNotFoundException {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found for this id :: " + companyId));

        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found for this id :: " + driverId));

        driverRepository.delete(driver);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
