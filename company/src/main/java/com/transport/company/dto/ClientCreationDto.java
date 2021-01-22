package com.transport.company.dto;

import com.transport.company.entity.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientCreationDto {

    private List<Client> clients;

    public ClientCreationDto() {
        clients = new ArrayList<Client>();
    }

    public ClientCreationDto(Client client) {
        this.clients = new ArrayList<Client>();
        this.clients.add(client);
    }

    public ClientCreationDto(List<Client> clients) {
        this.clients = clients;
    }

    public void addClient(Client client) {
        this.clients.add(client);
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    public List<Client> getClients() {
        return clients;
    }
}
