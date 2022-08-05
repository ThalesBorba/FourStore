package br.com.fourcamp.fourstore.fourstore.controller;

import br.com.fourcamp.fourstore.fourstore.dto.request.CreateClientDTO;
import br.com.fourcamp.fourstore.fourstore.dto.response.MessageResponseDTO;
import br.com.fourcamp.fourstore.fourstore.dto.response.ReturnClientDTO;
import br.com.fourcamp.fourstore.fourstore.entities.Client;
import br.com.fourcamp.fourstore.fourstore.service.ClientService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.function.Function;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @PostMapping("/")
    public ResponseEntity<MessageResponseDTO> createClient(@RequestBody @Valid CreateClientDTO createClientDTO) {
        var client = new Client();
        BeanUtils.copyProperties(createClientDTO, client);
        return ResponseEntity.status(HttpStatus.CREATED).body(clientService.createClient(client));
    }

    @GetMapping("/")
    public ResponseEntity<List<ReturnClientDTO>> listAll() {
        return ResponseEntity.status(HttpStatus.OK).body(clientService.listAll().stream().map(toDTO()).toList());
    }

    @GetMapping("/{cpf}")
    public ResponseEntity<ReturnClientDTO> findByCpf(@PathVariable @Valid String cpf) {
        var returnClientDTO = new ReturnClientDTO();
        BeanUtils.copyProperties(clientService.findByCpf(cpf), returnClientDTO);
        return ResponseEntity.status(HttpStatus.OK).body(returnClientDTO);
    }

    @PatchMapping("/{cpf}/{paymentMethod}/{paymentData}")
    public ResponseEntity<MessageResponseDTO> updatePaymentInfoByCpf(@PathVariable @Valid String cpf, @PathVariable @Valid
            Integer paymentMethod, @PathVariable @Valid String paymentData) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(clientService.updateByCpf(cpf, paymentMethod, paymentData));
    }

    @Transactional
    @DeleteMapping("/{cpf}")
    public ResponseEntity<MessageResponseDTO> deleteByCpf(@PathVariable String cpf) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(clientService.delete(cpf));
    }

    private Function<Client, ReturnClientDTO> toDTO() {
        return client -> {
            var returnClientDTO = new ReturnClientDTO();
            BeanUtils.copyProperties(client, returnClientDTO);
            return returnClientDTO;
        };
    }

}
