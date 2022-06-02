package br.com.fourcamp.fourstore.FourStore.service;

import br.com.fourcamp.fourstore.FourStore.dto.request.CreateClientDTO;
import br.com.fourcamp.fourstore.FourStore.dto.response.MessageResponseDTO;
import br.com.fourcamp.fourstore.FourStore.entities.Client;
import br.com.fourcamp.fourstore.FourStore.exceptions.ClientNotFoundException;
import br.com.fourcamp.fourstore.FourStore.exceptions.ProductNotFoundException;
import br.com.fourcamp.fourstore.FourStore.repositories.ClientRepository;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ClientService {

    private ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public MessageResponseDTO createClient(Client client) {
        Client savedClient = setClient(client);
        return createMessageResponse(savedClient.getCpf(), "Criado");
    }

    public MessageResponseDTO updateById(String cpf, Client client) throws ClientNotFoundException {
        //validações
        verifyIfExists(cpf);
        Client updatedClient = setClient(client);
        return createMessageResponse(updatedClient.getCpf(), "Updated");
    }

    public List<Client> listAll() {
        List<Client> allClients = clientRepository.findAll();
        return allClients;
    }

    public void delete(String cpf) throws ClientNotFoundException {
        verifyIfExists(cpf);
        clientRepository.deleteById(cpf);
    }

    private MessageResponseDTO createMessageResponse(@CPF String cpf, String s) {
        return MessageResponseDTO.builder().message(s + "cliente com o cpf " + cpf).build();
    }

    private Client verifyIfExists(String cpf) throws ClientNotFoundException {
        return clientRepository.findById(cpf)
                .orElseThrow(() -> new ClientNotFoundException(cpf));
    }

    private Client setClient(Client client) {
        return clientRepository.save(client);
    }

    public Client findById(String cpf) throws ClientNotFoundException {
        Client client = verifyIfExists(cpf);
        return client;
    }

    public CreateClientDTO validClient(CreateClientDTO createClientDTO) {
        //valida cpf e forma de pagamento
        return createClientDTO;
    }
}