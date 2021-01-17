package com.transport.company.controller;

import com.transport.company.entity.Client;
import com.transport.company.entity.Company;
import com.transport.company.entity.Driver;
import com.transport.company.exception.*;
import com.transport.company.repository.CompanyRepository;
import com.transport.company.repository.ClientRepository;
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
public class ClientController {
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private CompanyRepository companyRepository;

    @GetMapping("/company/{companyId}/client")
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @GetMapping("/company/{companyId}/client/{clientId}")
    public ResponseEntity<Client> getClientById(@PathVariable(value = "companyId") Long companyId,
                                                @PathVariable(value = "clientId") Long clientId)
            throws ResourceNotFoundException {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found for this id :: " + companyId));

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found for this id :: " + clientId));
        return ResponseEntity.ok().body(client);
    }

    @PostMapping("/company/{companyId}/client")
    public Client createDriver(@PathVariable(value = "companyId") Long companyId,
                               @Validated @RequestBody Client client) throws ResourceNotFoundException {

        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found for this id :: " + companyId));
        client.setCompany(company);

        return clientRepository.save(client);
    }

    @PutMapping("/company/{companyId}/client/{clientId}")
    public ResponseEntity<Client> updateCompany(@PathVariable(value = "companyId") Long companyId,
                                                @PathVariable(value = "clientId") Long clientId,
                                                @Validated @RequestBody Client clientDetails) throws ResourceNotFoundException {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found for this id :: " + companyId));

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found for this id :: " + clientId));

        client.setCompany(company);
        client.setFreights(clientDetails.getFreights());

        final Client updatedClient = clientRepository.save(client);
        return ResponseEntity.ok(updatedClient);
    }

    @DeleteMapping("/company/{companyId}/client/{clientId}")
    public Map<String, Boolean> deleteDriver(@PathVariable(value = "companyId") Long companyId,
                                             @PathVariable(value = "clientId") Long clientId)
            throws ResourceNotFoundException {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found for this id :: " + companyId));

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found for this id :: " + clientId));

        clientRepository.delete(client);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
