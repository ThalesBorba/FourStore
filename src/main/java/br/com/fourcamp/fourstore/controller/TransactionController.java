package br.com.fourcamp.fourstore.controller;

import br.com.fourcamp.fourstore.dto.request.CreateTransactionDTO;
import br.com.fourcamp.fourstore.dto.response.ReturnTransactionDTO;
import br.com.fourcamp.fourstore.entities.Transaction;
import br.com.fourcamp.fourstore.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/transaction")
@CrossOrigin(origins = "*", maxAge = 3600)
@Transactional
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping("/")
    public ResponseEntity<String> createTransaction(@RequestBody @Valid CreateTransactionDTO createTransactionDTO) {
        Transaction transaction = transactionService.createTransaction(createTransactionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Transação com a id " + transaction.getId() + " criada!");
    }


    @GetMapping
    public ResponseEntity<List<ReturnTransactionDTO>> listAll() {
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReturnTransactionDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.status(HttpStatus.OK).body(transactionService.findById(id));
    }

}
