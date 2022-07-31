package br.com.fourcamp.fourstore.fourstore.service;

import br.com.fourcamp.fourstore.fourstore.dto.request.CreateClientDTO;
import br.com.fourcamp.fourstore.fourstore.dto.response.MessageResponseDTO;
import br.com.fourcamp.fourstore.fourstore.entities.Client;
import br.com.fourcamp.fourstore.fourstore.exceptions.InvalidParametersException;
import br.com.fourcamp.fourstore.fourstore.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public MessageResponseDTO createClient(Client client) {
        clientRepository.save(client);
        return createMessageResponse(client.getCpf(), "Criado ");
    }

    public MessageResponseDTO updateById(Client client) {
        clientRepository.save(client);
        return createMessageResponse(client.getCpf(), "Atualizado ");
    }

    public List<Client> listAll() {
        return clientRepository.findAll();
    }

    public MessageResponseDTO delete(String cpf)  {
        String cpfToReturn = clientRepository.findByCpf(cpf).getCpf();
        clientRepository.deleteByCpf(cpf);
        return createMessageResponse(cpfToReturn, "Deletado ");
    }

    private MessageResponseDTO createMessageResponse(String cpf, String s) {
        return MessageResponseDTO.builder().message(s + "cliente com o cpf " + cpf).build();
    }

    public Client findByCpf(String cpf) {
        return clientRepository.findByCpf(cpf);
    }
}
