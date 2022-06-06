package br.com.fourcamp.fourstore.FourStore.controller;

import br.com.fourcamp.fourstore.FourStore.dto.request.CreateTransactionDTO;
import br.com.fourcamp.fourstore.FourStore.dto.response.MessageResponseDTO;
import br.com.fourcamp.fourstore.FourStore.dto.response.ReturnTransactionDTO;
import br.com.fourcamp.fourstore.FourStore.exceptions.*;
import br.com.fourcamp.fourstore.FourStore.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public MessageResponseDTO createTransaction(@RequestBody @Valid CreateTransactionDTO createTransactionDTO) throws
            ClientNotFoundException, StockNotFoundException, InvalidParametersException, StockInsufficientException {
        return transactionService.createTransaction(createTransactionDTO);
    }

    @GetMapping
    public List<ReturnTransactionDTO> listAll() {
        return transactionService.listAll();
    }

    @GetMapping("/{id}")
    public ReturnTransactionDTO findById(@PathVariable Long id) throws TransactionNotFoundException {
        return transactionService.findById(id);
    }

    //Como é apenas para fins de registro, melhor não ter update e delete
}
