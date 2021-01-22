package com.transport.company.repository;

import com.transport.company.entity.Client;
import com.transport.company.entity.Freight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FreightRepository extends JpaRepository<Freight, Long> {
    List<Freight> findByClientId(long clientId);
    List<Freight> findByDriverId(long driverId);
}
