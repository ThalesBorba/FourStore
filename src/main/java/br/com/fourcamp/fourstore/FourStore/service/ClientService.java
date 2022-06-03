package br.com.fourcamp.fourstore.FourStore.service;

import br.com.fourcamp.fourstore.FourStore.dto.request.CreateClientDTO;
import br.com.fourcamp.fourstore.FourStore.dto.request.CreateProductDTO;
import br.com.fourcamp.fourstore.FourStore.dto.response.MessageResponseDTO;
import br.com.fourcamp.fourstore.FourStore.entities.Client;
import br.com.fourcamp.fourstore.FourStore.entities.Product;
import br.com.fourcamp.fourstore.FourStore.exceptions.*;
import br.com.fourcamp.fourstore.FourStore.mapper.ClientMapper;
import br.com.fourcamp.fourstore.FourStore.mapper.ProductMapper;
import br.com.fourcamp.fourstore.FourStore.repositories.ClientRepository;
import br.com.fourcamp.fourstore.FourStore.util.ClientValidations;
import br.com.fourcamp.fourstore.FourStore.util.SkuValidations;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ClientService {

    private ClientRepository clientRepository;

    private final ClientMapper clientMapper = ClientMapper.INSTANCE;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public MessageResponseDTO createClient(CreateClientDTO createClientDTO) throws InvalidParametersException {
        Client savedClient = setClient(createClientDTO);
        return createMessageResponse(savedClient.getCpf(), "Criado");
    }

    public MessageResponseDTO updateById(String cpf, CreateClientDTO createClientDTO) throws ClientNotFoundException,
            InvalidParametersException {
        verifyIfExists(cpf);
        CreateClientDTO validClient = validClient(createClientDTO);
        Client updatedClient = clientMapper.toModel(validClient);
        clientRepository.save(updatedClient);
        return createMessageResponse(updatedClient.getCpf(), "Updated");
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

    private Client setClient(CreateClientDTO createClientDTO) throws InvalidParametersException {
        CreateClientDTO validClient = validClient(createClientDTO);
        Client clientToSave = clientMapper.toModel(validClient);
        return clientRepository.save(clientToSave);
    }

    public Client findById(String cpf) throws ClientNotFoundException {
        Client client = verifyIfExists(cpf);
        return client;
    }

    public CreateClientDTO validClient(CreateClientDTO createClientDTO) throws InvalidParametersException {
        if (!ClientValidations.validateCpf(createClientDTO.getCpf()) ||
                !ClientValidations.paymentMethodValidation(createClientDTO.getPaymentMethod(),
                        createClientDTO.getPaymentData())) {
            throw new InvalidParametersException();
        } else {
        return createClientDTO;
        }
    }
}