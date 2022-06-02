package br.com.fourcamp.fourstore.FourStore.controller;

import br.com.fourcamp.fourstore.FourStore.dto.response.MessageResponseDTO;
import br.com.fourcamp.fourstore.FourStore.entities.Client;
import br.com.fourcamp.fourstore.FourStore.entities.Stock;
import br.com.fourcamp.fourstore.FourStore.exceptions.ClientNotFoundException;
import br.com.fourcamp.fourstore.FourStore.exceptions.ProductNotFoundException;
import br.com.fourcamp.fourstore.FourStore.service.ClientService;
import br.com.fourcamp.fourstore.FourStore.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

public class ClientController {

    @Autowired
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponseDTO createStock(@RequestBody @Valid Client client) {
        return clientService.createStock(client);
    }

    @GetMapping
    public List<Client> listAll() {
        return clientService.listAll();
    }

    @GetMapping("/{id}")
    public Client findById(@PathVariable String cpf) throws ClientNotFoundException {
        return clientService.findById(cpf);
    }

    @PutMapping("/{id}")
    public MessageResponseDTO updateById(@PathVariable String cpf, @RequestBody @Valid Client client)
            throws ClientNotFoundException {
        return clientService.updateById(cpf, client);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable String cpf) throws ClientNotFoundException {
        clientService.delete(cpf);
    }

}
