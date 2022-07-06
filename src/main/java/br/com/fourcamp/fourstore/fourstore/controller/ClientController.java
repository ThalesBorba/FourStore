package br.com.fourcamp.fourstore.fourstore.controller;

import br.com.fourcamp.fourstore.fourstore.dto.request.CreateClientDTO;
import br.com.fourcamp.fourstore.fourstore.dto.response.ReturnClientDTO;
import br.com.fourcamp.fourstore.fourstore.exceptions.ClientNotFoundException;
import br.com.fourcamp.fourstore.fourstore.exceptions.InvalidParametersException;
import br.com.fourcamp.fourstore.fourstore.service.ClientService;
import br.com.fourcamp.fourstore.fourstore.util.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Object> createClient(@RequestBody @Valid CreateClientDTO createClientDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(clientService.createClient(createClientDTO));
        } catch (InvalidParametersException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ResponseModel(HttpStatus.NOT_ACCEPTABLE,
                    HttpStatus.NOT_ACCEPTABLE.value(), e.getMessage()));
        }
    }

    @GetMapping
    public List<ReturnClientDTO> listAll() {
        return clientService.listAll();
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<Object> findByCpf(@PathVariable @Valid String cpf) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(clientService.findByCpf(cpf));
        } catch (ClientNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseModel(HttpStatus.NOT_FOUND,
                    HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }

    @PutMapping("/{cpf}")
    public ResponseEntity<Object> updateById(@PathVariable String cpf, @RequestBody @Valid CreateClientDTO createClientDTO) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(clientService.updateById(cpf, createClientDTO));
        } catch (ClientNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseModel(HttpStatus.NOT_FOUND,
                    HttpStatus.NOT_FOUND.value(), e.getMessage()));
        } catch (InvalidParametersException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ResponseModel(HttpStatus.NOT_ACCEPTABLE,
                    HttpStatus.NOT_ACCEPTABLE.value(), e.getMessage()));
        }
    }

    @Transactional
    @DeleteMapping("/{cpf}")
    public ResponseEntity<Object> deleteByCpf(@PathVariable String cpf) {
        try {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(clientService.delete(cpf));
        } catch (ClientNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseModel(HttpStatus.NOT_FOUND,
                    HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }

}
