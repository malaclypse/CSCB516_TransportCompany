package com.transport.company.service;

import com.transport.company.entity.Driver;
import com.transport.company.entity.Freight;
import com.transport.company.exception.ResourceNotFoundException;
import com.transport.company.repository.DriverRepository;
import com.transport.company.repository.FreightRepository;
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
public class DriverService {
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private FreightRepository freightRepository;

    public List<Driver> getDrivers() throws ResourceNotFoundException {
        return driverRepository.findAll();
    }

    public Page<Driver> findPaginated(final int pageNumber, final int pageSize,
                                      final String sortField, final String sortDirection) {

        final Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        final Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
        return driverRepository.findAll(pageable);
    }

    public Map<Driver, List<Freight>> getFreightsForDrivers(){
        Map<Driver, List<Freight>> result= new HashMap<>();
        var drivers = driverRepository.findAll();
        for(var driver : drivers){
            result.put(driver, freightRepository.findByDriverId(driver.getId()));
        }

        return  result;
    }

    public Map<Driver, Integer> getFreightCountForDrivers(){
        Map<Driver, Integer> result= new HashMap<>();
        var drivers = driverRepository.findAll();
        for(var driver : drivers){
            result.put(driver, freightRepository.findByDriverId(driver.getId()).size());
        }

        return  result;
    }

    public Map<Driver, Double> getEarningsPerDriver(){
        Map<Driver, Double> result= new HashMap<>();
        var drivers = driverRepository.findAll();
        for(var driver : drivers){
            result.put(driver, freightRepository.findByDriverId(driver.getId())
                    .stream()
                    .mapToDouble(Freight::getPrice)
                    .sum());
        }

        return  result;
    }

    public Driver getDriver(Long driverId) throws ResourceNotFoundException {

        return driverRepository.findById(driverId)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found for this id :: " + driverId));
    }

    public Driver createDriver(Driver driver) throws ResourceNotFoundException {
        return driverRepository.save(driver);
    }

    public Driver updateDriver(Long driverId, Driver driverDetails) throws ResourceNotFoundException {

        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found for this id :: " + driverId));

        driver.setFirstName(driverDetails.getFirstName());
        driver.setLastName(driverDetails.getLastName());
        driver.setEmailId(driverDetails.getEmailId());
        driver.setDriverQualifications(driverDetails.getDriverQualifications());

        return driverRepository.save(driver);
    }


    public Map<String, Boolean> deleteDriver(Long driverId) throws ResourceNotFoundException {
        Driver driver = driverRepository.findById(driverId)
                .orElseThrow(() -> new ResourceNotFoundException("Driver not found for this id :: " + driverId));

        driverRepository.delete(driver);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
