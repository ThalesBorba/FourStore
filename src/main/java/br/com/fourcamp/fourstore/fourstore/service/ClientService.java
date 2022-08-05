package br.com.fourcamp.fourstore.fourstore.service;

import br.com.fourcamp.fourstore.fourstore.dto.response.MessageResponseDTO;
import br.com.fourcamp.fourstore.fourstore.entities.Client;
import br.com.fourcamp.fourstore.fourstore.exceptions.ClientNotFoundException;
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

    public MessageResponseDTO updateByCpf(String cpf, Integer paymentMethod, String paymentData) {
        Client client = clientRepository.findByCpf(cpf).orElseThrow(() -> new ClientNotFoundException(cpf));
        client.updatePaymentInfo(paymentMethod, paymentData);
        clientRepository.save(client);
        return createMessageResponse(client.getCpf(), "Atualizado ");
    }

    public List<Client> listAll() {
        return clientRepository.findAll();
    }

    public MessageResponseDTO delete(String cpf) {
        findByCpf(cpf);
        clientRepository.deleteByCpf(cpf);
        return createMessageResponse(cpf, "Deletado ");
    }

    private MessageResponseDTO createMessageResponse(String cpf, String s) {
        return MessageResponseDTO.builder().message(s + "cliente com o cpf " + cpf).build();
    }

    public Client findByCpf(String cpf) {
        return clientRepository.findByCpf(cpf).orElseThrow(() -> new ClientNotFoundException(cpf));
    }
}
