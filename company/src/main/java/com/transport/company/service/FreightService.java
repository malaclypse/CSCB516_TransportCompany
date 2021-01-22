package com.transport.company.service;

import com.transport.company.entity.Client;
import com.transport.company.entity.Freight;
import com.transport.company.exception.ResourceNotFoundException;
import com.transport.company.repository.ClientRepository;
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
public class FreightService {
    @Autowired
    private FreightRepository freightRepository;
    @Autowired
    private ClientRepository clientRepository;

    public List<Freight> getFreights() throws ResourceNotFoundException {
        return freightRepository.findAll();
    }

    public List<Freight> getFreights(Long clientId) throws ResourceNotFoundException {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found for this id :: " + clientId));

        return freightRepository.findByClientId(clientId);
    }

    public Page<Freight> findPaginated(final int pageNumber, final int pageSize,
                                      final String sortField, final String sortDirection) {

        final Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortField).ascending() : Sort.by(sortField).descending();
        final Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sort);
        return freightRepository.findAll(pageable);
    }

    public Freight getFreight(Long freightId) throws ResourceNotFoundException {
        return freightRepository.findById(freightId)
                .orElseThrow(() -> new ResourceNotFoundException("Freight not found for this id :: " + freightId));
    }

    public Freight createFreight(Freight freight) throws ResourceNotFoundException {
        Client client = clientRepository.findById(freight.getClient().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Client not found for this id :: " + freight.getClient().getId()));

        if(freight.isDelivered()==null ){
            freight.setDelivered(false);
        }
        if(freight.isPaid()==null){
            freight.setPaid(false);
        }

        return freightRepository.save(freight);
    }

    public Freight updateFreight(Freight freightDetails) throws ResourceNotFoundException {
        Client client = clientRepository.findById(freightDetails.getClient().getId())
                .orElseThrow(() -> new ResourceNotFoundException("Client not found for this id :: " + freightDetails.getClient().getId()));

        Freight freight = freightRepository.findById(freightDetails.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Freight not found for this id :: " + freightDetails.getId()));

        freight.setClient(client);
        if(freight.isDelivered()==null ){
            freight.setDelivered(false);
        }
        if(freight.isPaid()==null){
            freight.setPaid(false);
        }

        return freightRepository.save(freight);
    }

    public Freight updateFreight(Long clientId, Long freightId, Freight freightDetails) throws ResourceNotFoundException {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client not found for this id :: " + clientId));

        Freight freight = freightRepository.findById(freightId)
                .orElseThrow(() -> new ResourceNotFoundException("Freight not found for this id :: " + freightId));

        freight.setClient(client);
        freight.setClient(freightDetails.getClient());

        return freightRepository.save(freight);
    }

    public Map<String, Boolean> deleteFreight(Long freightId) throws ResourceNotFoundException {
        Freight freight = freightRepository.findById(freightId)
                .orElseThrow(() -> new ResourceNotFoundException("Freight not found for this id :: " + freightId));

        freightRepository.delete(freight);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}
