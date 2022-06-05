package br.com.fourcamp.fourstore.FourStore.service;

import br.com.fourcamp.fourstore.FourStore.dto.response.MessageResponseDTO;
import br.com.fourcamp.fourstore.FourStore.entities.Client;
import br.com.fourcamp.fourstore.FourStore.exceptions.ClientNotFoundException;
import br.com.fourcamp.fourstore.FourStore.exceptions.InvalidParametersException;
import br.com.fourcamp.fourstore.FourStore.mapper.ClientMapper;
import br.com.fourcamp.fourstore.FourStore.repositories.ClientRepository;
import br.com.fourcamp.fourstore.FourStore.util.ClientValidations;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    private ClientRepository clientRepository;

    private final ClientMapper clientMapper = ClientMapper.INSTANCE;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public MessageResponseDTO createClient(Client client) throws InvalidParametersException {
        Client savedClient = setClient(client);
        return createMessageResponse(savedClient.getCpf(), "Criado");
    }

    public MessageResponseDTO updateById(String cpf, Client client) throws ClientNotFoundException,
            InvalidParametersException {
        verifyIfExists(cpf);
        Client validClient = validClient(client);
        clientRepository.save(client);
        return createMessageResponse(client.getCpf(), "Updated");
    }

    public List<Client> listAll() {
        //retornarDTO
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

    private Client setClient(Client client) throws InvalidParametersException {
        Client validClient = validClient(client);
        return clientRepository.save(validClient);
    }

    public Client findById(String cpf) throws ClientNotFoundException {
        Client client = verifyIfExists(cpf);
        return client;
    }

    public Client validClient(Client client) throws InvalidParametersException {
        if (!ClientValidations.validateCpf(client.getCpf()) ||
                !ClientValidations.paymentMethodValidation(client.getPaymentMethod(),
                        client.getPaymentData())) {
            throw new InvalidParametersException();
        } else {
        return client;
        }
    }
}