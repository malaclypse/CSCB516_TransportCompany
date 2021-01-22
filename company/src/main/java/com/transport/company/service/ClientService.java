package com.transport.company.service;

import com.transport.company.entity.Client;
import com.transport.company.entity.Driver;
import com.transport.company.exception.ResourceNotFoundException;
import com.transport.company.repository.ClientRepository;
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
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    public List<Client> getClients() throws ResourceNotFoundException {
        return clientRepository.findAll();
    }

    public Page<Client> findPaginated(final int pageNumber, final int pageSize,
                                      final String sortField, final String sortDirection) {

        final Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        final Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
        return clientRepository.findAll(pageable);
    }

    public Client getClient( Long clientId) throws ResourceNotFoundException {
        return clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found for this id :: " + clientId));
    }

    public Map<String, Boolean> deleteClient(Long clientId) throws ResourceNotFoundException {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found for this id :: " + clientId));

        clientRepository.delete(client);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }

    public Client updateClient(Long clientId, Client clientDetails) throws ResourceNotFoundException {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found for this id :: " + clientId));

        client.setFreights(clientDetails.getFreights());
        client.setClientType(clientDetails.getClientType());
        client.setName(clientDetails.getName());
        return clientRepository.save(client);
    }

    public Client createClient(Client client) throws ResourceNotFoundException {
        return clientRepository.save(client);
    }
}
