package br.com.fourcamp.fourstore.FourStore.controller;

import br.com.fourcamp.fourstore.FourStore.dto.request.CreateClientDTO;
import br.com.fourcamp.fourstore.FourStore.dto.response.MessageResponseDTO;
import br.com.fourcamp.fourstore.FourStore.dto.response.ReturnClientDTO;
import br.com.fourcamp.fourstore.FourStore.exceptions.ClientNotFoundException;
import br.com.fourcamp.fourstore.FourStore.exceptions.InvalidParametersException;
import br.com.fourcamp.fourstore.FourStore.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponseDTO createClient(@RequestBody @Valid CreateClientDTO createClientDTO)
            throws InvalidParametersException {
        return clientService.createClient(createClientDTO);
    }

    @GetMapping
    public List<ReturnClientDTO> listAll() {
        return clientService.listAll();
    }

    @GetMapping("/{cpf}")
    public ReturnClientDTO findByCpf(@PathVariable @Valid String cpf) throws
            ClientNotFoundException {
        return clientService.findByCpf(cpf);
    }

    @PutMapping("/{cpf}")
    public MessageResponseDTO updateById(@PathVariable String cpf, @RequestBody @Valid CreateClientDTO createClientDTO)
            throws ClientNotFoundException, InvalidParametersException {
        return clientService.updateById(cpf, createClientDTO);
        //bloquear a mudança de cpf
    }

    @Transactional
    @DeleteMapping("/{cpf}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByCpf(@PathVariable String cpf) throws ClientNotFoundException {
        clientService.delete(cpf);
        //devolver mensagem
    }

}
