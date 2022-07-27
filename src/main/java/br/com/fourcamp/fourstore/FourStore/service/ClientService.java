package br.com.fourcamp.fourstore.FourStore.service;

import br.com.fourcamp.fourstore.FourStore.dto.request.CreateClientDTO;
import br.com.fourcamp.fourstore.FourStore.dto.response.MessageResponseDTO;
import br.com.fourcamp.fourstore.FourStore.dto.response.ReturnClientDTO;
import br.com.fourcamp.fourstore.FourStore.entities.Client;
import br.com.fourcamp.fourstore.FourStore.exceptions.ClientNotFoundException;
import br.com.fourcamp.fourstore.FourStore.exceptions.InvalidParametersException;
import br.com.fourcamp.fourstore.FourStore.mapper.ClientMapper;
import br.com.fourcamp.fourstore.FourStore.repositories.ClientRepository;
import br.com.fourcamp.fourstore.FourStore.util.ClientValidations;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClientService {
    private ClientRepository clientRepository;

    private final ClientMapper clientMapper = ClientMapper.INSTANCE;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public MessageResponseDTO createClient(CreateClientDTO createClientDTO) throws InvalidParametersException {
        Client savedClient = setClient(createClientDTO);
        return createMessageResponse(savedClient.getCpf(), "Criado ");
    }

    public MessageResponseDTO updateById(String cpf, CreateClientDTO createClientDTO) throws ClientNotFoundException,
            InvalidParametersException {
        verifyIfExists(cpf);
        CreateClientDTO validClient = validClient(createClientDTO);
        Client updatedClient = clientMapper.toModel(validClient);
        if(!updatedClient.getCpf().equals(cpf)) {
            throw new InvalidParametersException();
        }
        clientRepository.save(updatedClient);
        return createMessageResponse(updatedClient.getCpf(), "Atualizado ");
    }

    public List<ReturnClientDTO> listAll() {
        List<Client> allClients = clientRepository.findAll();
        List<ReturnClientDTO> returnClientDTOList = new ArrayList<>();
        for (Client client : allClients) {
            ReturnClientDTO returnClientDTO = clientMapper.toDTO(client);
            returnClientDTOList.add(returnClientDTO);
        }
        return returnClientDTOList;
    }

    public MessageResponseDTO delete(String cpf) throws ClientNotFoundException {
        verifyIfExists(cpf);
        Client deletedClient = clientRepository.findByCpf(cpf);
        String cpfOfClientToBeDeleted = deletedClient.getCpf();
        clientRepository.deleteByCpf(cpf);
        return createMessageResponse(cpfOfClientToBeDeleted, "Deletado ");
    }

    private MessageResponseDTO createMessageResponse(@CPF String cpf, String s) {
        return MessageResponseDTO.builder().message(s + "cliente com o cpf " + cpf).build();
    }

    private Client verifyIfExists(String cpf) throws ClientNotFoundException {
        if (clientRepository.findByCpf(cpf) != null) {
            return clientRepository.findByCpf(cpf);
        } else {
            throw new ClientNotFoundException(cpf);
        }
        }


    private Client setClient(CreateClientDTO createClientDTO) throws InvalidParametersException {
        CreateClientDTO validClient = validClient(createClientDTO);
        Client clientToSave = clientMapper.toModel(validClient);
        return clientRepository.save(clientToSave);
    }

    public ReturnClientDTO findByCpf(String cpf) throws ClientNotFoundException {
        Client client = verifyIfExists(cpf);
        return clientMapper.toDTO(client);
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
