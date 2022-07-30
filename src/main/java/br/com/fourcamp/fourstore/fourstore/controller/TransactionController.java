package br.com.fourcamp.fourstore.fourstore.controller;

import br.com.fourcamp.fourstore.fourstore.dto.request.CreateTransactionDTO;
import br.com.fourcamp.fourstore.fourstore.dto.response.MessageResponseDTO;
import br.com.fourcamp.fourstore.fourstore.dto.response.ReturnTransactionDTO;
import br.com.fourcamp.fourstore.fourstore.exceptions.*;
import br.com.fourcamp.fourstore.fourstore.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/transaction")
@CrossOrigin(origins = "*", maxAge = 3600)
@Transactional
public class TransactionController {
    @Autowired
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/")
    public ResponseEntity<String> createTransaction(@RequestBody @Valid CreateTransactionDTO createTransactionDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body("Transação criada!");
    }


    @GetMapping
    public ResponseEntity<List<ReturnTransactionDTO>> listAll() {
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReturnTransactionDTO> findById(@PathVariable Long id) throws TransactionNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.findById(id));
    }

}
