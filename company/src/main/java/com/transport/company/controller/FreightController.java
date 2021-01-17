package com.transport.company.controller;

import com.transport.company.entity.Client;
import com.transport.company.entity.Company;
import com.transport.company.entity.Driver;
import com.transport.company.entity.Freight;
import com.transport.company.exception.*;
import com.transport.company.repository.CompanyRepository;
import com.transport.company.repository.ClientRepository;
import com.transport.company.repository.FreightRepository;
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
public class FreightController {
    @Autowired
    private FreightRepository freightRepository;
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CompanyRepository companyRepository;

    @GetMapping("/company/{companyId}/client/{clientId}/freight")
    public List<Freight> getAllFreights(@PathVariable(value = "companyId") Long companyId,
                                        @PathVariable(value = "clientId") Long clientId)
            throws ResourceNotFoundException {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found for this id :: " + companyId));

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found for this id :: " + clientId));

        return freightRepository.findAll();
    }

    @GetMapping("/company/{companyId}/client/{clientId}/freight/{freightId}")
    public ResponseEntity<Freight> getFreightById(@PathVariable(value = "companyId") Long companyId,
                                                @PathVariable(value = "clientId") Long clientId,
                                                @PathVariable(value = "freightId") Long freightId)
            throws ResourceNotFoundException {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found for this id :: " + companyId));

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found for this id :: " + clientId));

        Freight freight = freightRepository.findById(freightId)
                .orElseThrow(() -> new ResourceNotFoundException("Freight not found for this id :: " + freightId));

        return ResponseEntity.ok().body(freight);
    }

    @PostMapping("/company/{companyId}/client/{clientId}/freight")
    public Freight createFreight(@PathVariable(value = "companyId") Long companyId,
                               @PathVariable(value = "clientId") Long clientId,
                               @PathVariable(value = "freightId") Long freightId,
                               @Validated @RequestBody Freight freight) throws ResourceNotFoundException {

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found for this id :: " + companyId));

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found for this id :: " + clientId));

        freight.setClient(client);

        return freightRepository.save(freight);
    }

    @PutMapping("/company/{companyId}/client/{clientId}/freight/{freightId}")
    public ResponseEntity<Freight> updateFreight(@PathVariable(value = "companyId") Long companyId,
                                                @PathVariable(value = "clientId") Long clientId,
                                                @PathVariable(value = "freightId") Long freightId,
                                                @Validated @RequestBody Freight freightDetails) throws ResourceNotFoundException {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found for this id :: " + companyId));

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found for this id :: " + clientId));

        Freight freight = freightRepository.findById(freightId)
                .orElseThrow(() -> new ResourceNotFoundException("Freight not found for this id :: " + freightId));

        freight.setClient(client);
        freight.setClient(freightDetails.getClient());

        final Freight updatedFreight = freightRepository.save(freight);
        return ResponseEntity.ok(updatedFreight);
    }

    @DeleteMapping("/company/{companyId}/client/{clientId}/freight/{freightId}")
    public Map<String, Boolean> deleteDriver(@PathVariable(value = "companyId") Long companyId,
                                             @PathVariable(value = "clientId") Long clientId,
                                             @PathVariable(value = "freightId") Long freightId)
            throws ResourceNotFoundException {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found for this id :: " + companyId));

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found for this id :: " + clientId));

        Freight freight = freightRepository.findById(freightId)
                .orElseThrow(() -> new ResourceNotFoundException("Freight not found for this id :: " + freightId));

        freightRepository.delete(freight);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
