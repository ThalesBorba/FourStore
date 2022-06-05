package br.com.fourcamp.fourstore.FourStore.controller;

import br.com.fourcamp.fourstore.FourStore.dto.response.MessageResponseDTO;
import br.com.fourcamp.fourstore.FourStore.entities.Transaction;
import br.com.fourcamp.fourstore.FourStore.exceptions.ProductNotFoundException;
import br.com.fourcamp.fourstore.FourStore.exceptions.TransactionNotFoundException;
import br.com.fourcamp.fourstore.FourStore.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponseDTO createTransaction(@RequestBody @Valid Transaction transaction) {

        return transactionService.createTransaction(transaction);
    }

    @GetMapping
    public List<Transaction> listAll() {
        return transactionService.listAll();
    }

    @GetMapping("/{id}")
    public Transaction findById(@PathVariable Long id) throws TransactionNotFoundException {
        return transactionService.findById(id);
    }

    @PutMapping("/{id}")
    public MessageResponseDTO updateById(@PathVariable Long id, @RequestBody @Valid Transaction transaction)
            throws ProductNotFoundException, TransactionNotFoundException {
        return transactionService.updateById(id, transaction);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) throws TransactionNotFoundException {
        transactionService.delete(id);
    }
}
