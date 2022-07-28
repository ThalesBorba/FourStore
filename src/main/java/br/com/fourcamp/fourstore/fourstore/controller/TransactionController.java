package br.com.fourcamp.fourstore.fourstore.controller;

import br.com.fourcamp.fourstore.fourstore.dto.request.CreateTransactionDTO;
import br.com.fourcamp.fourstore.fourstore.dto.response.MessageResponseDTO;
import br.com.fourcamp.fourstore.fourstore.dto.response.ReturnTransactionDTO;
import br.com.fourcamp.fourstore.fourstore.exceptions.*;
import br.com.fourcamp.fourstore.fourstore.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
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
