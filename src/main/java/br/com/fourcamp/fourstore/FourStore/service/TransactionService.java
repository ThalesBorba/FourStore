package br.com.fourcamp.fourstore.FourStore.service;

import br.com.fourcamp.fourstore.FourStore.dto.request.CreateTransactionDTO;
import br.com.fourcamp.fourstore.FourStore.dto.response.MessageResponseDTO;
import br.com.fourcamp.fourstore.FourStore.entities.Transaction;
import br.com.fourcamp.fourstore.FourStore.exceptions.TransactionNotFoundException;
import br.com.fourcamp.fourstore.FourStore.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    private TransactionRepository transactionRepository;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public MessageResponseDTO createStock(Transaction transaction) {
        //validações
        Transaction savedTransaction = setTransaction(transaction);
        return createMessageResponse(savedTransaction.getId(), "Criado");
    }

    public MessageResponseDTO updateById(Long id, Transaction transaction) throws TransactionNotFoundException {
        //adicionar as validações
        verifyIfExists(id);
        Transaction updatedTransaction = setTransaction(transaction);
        return createMessageResponse(updatedTransaction.getId(), "Updated");
    }

    public List<Transaction> listAll() {
        List<Transaction> allTransactions = transactionRepository.findAll();
        return allTransactions;
    }

    public void delete(Long id) throws TransactionNotFoundException {
        verifyIfExists(id);
        transactionRepository.deleteById(id);
    }

    private MessageResponseDTO createMessageResponse(Long id, String s) {
        return MessageResponseDTO.builder().message(s + "estoque com a id" + id).build();
    }

    private Transaction verifyIfExists(Long id) throws TransactionNotFoundException {
        //trocar por find by product

        return transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException(id));
    }

    private Transaction setTransaction(Transaction transaction) {
        //adicionar as validações

        return transactionRepository.save(transaction);
    }

    public Transaction findById(Long id) throws TransactionNotFoundException {
        Transaction transaction = verifyIfExists(id);
        return transaction;
    }

    private CreateTransactionDTO validTransaction(CreateTransactionDTO createTransactionDTO) {
        //criar validações
        return createTransactionDTO;
    }

}
