package br.com.fourcamp.fourstore.FourStore.service;

import br.com.fourcamp.fourstore.FourStore.dto.request.CreateTransactionDTO;
import br.com.fourcamp.fourstore.FourStore.dto.response.MessageResponseDTO;
import br.com.fourcamp.fourstore.FourStore.dto.response.ReturnTransactionDTO;
import br.com.fourcamp.fourstore.FourStore.entities.Client;
import br.com.fourcamp.fourstore.FourStore.entities.Transaction;
import br.com.fourcamp.fourstore.FourStore.exceptions.*;
import br.com.fourcamp.fourstore.FourStore.mapper.TransactionMapper;
import br.com.fourcamp.fourstore.FourStore.repositories.ClientRepository;
import br.com.fourcamp.fourstore.FourStore.repositories.TransactionRepository;
import br.com.fourcamp.fourstore.FourStore.util.CartMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionService {

    private TransactionRepository transactionRepository;

    private StockService stockService;
    private ClientRepository clientRepository;
    private TransactionMapper transactionMapper;

    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public MessageResponseDTO createTransaction(CreateTransactionDTO createTransactionDTO) throws
            ClientNotFoundException, StockNotFoundException, InvalidParametersException, StockInsufficientException {
        Transaction savedTransaction = setTransaction(createTransactionDTO);
        return createMessageResponse(savedTransaction.getId(), "Criado ");
    }

    public List<ReturnTransactionDTO> listAll() {
        List<Transaction> allTransactions = transactionRepository.findAll();
        List<ReturnTransactionDTO> returnTransactionDTOList = new ArrayList<>();
        for (Transaction transaction : allTransactions) {
            ReturnTransactionDTO returnTransactionDTO = transactionMapper.toDTO(transaction);
            returnTransactionDTOList.add(returnTransactionDTO);
        }
        return returnTransactionDTOList;
    }

    private MessageResponseDTO createMessageResponse(Long id, String s) {
        return MessageResponseDTO.builder().message(s + "estoque com a id " + id).build();
    }

    private Transaction verifyIfExists(Long id) throws TransactionNotFoundException {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException(id));
    }

    private Transaction setTransaction(CreateTransactionDTO createTransactionDTO) throws ClientNotFoundException,
            StockNotFoundException, InvalidParametersException, StockInsufficientException {
        CreateTransactionDTO validTrasaction = validTransaction(createTransactionDTO);
        Double profit = calculateProfit(validTrasaction);
        Transaction transactionToSave = transactionMapper.toModel(validTrasaction);
        transactionToSave.setProfit(profit);
        return transactionRepository.save(transactionToSave);
    }

    public ReturnTransactionDTO findById(Long id) throws TransactionNotFoundException {
        Transaction transaction = verifyIfExists(id);
        return transactionMapper.toDTO(transaction);
    }

    private CreateTransactionDTO validTransaction(CreateTransactionDTO createTransactionDTO) throws
            StockNotFoundException, InvalidParametersException, StockInsufficientException, ClientNotFoundException {
        List<Client> clients = clientRepository.findAll();
        if (!clients.contains(createTransactionDTO.getClient())) {
            throw new ClientNotFoundException(createTransactionDTO.getClient().getCpf());
        }
        stockService.updateByTransaction(createTransactionDTO);
        return createTransactionDTO;
    }

    private Double calculateProfit(CreateTransactionDTO createTransactionDTO) throws InvalidParametersException {
        Integer paymentMethod = createTransactionDTO.getClient().getPaymentMethod();
        return CartMethods.retornaLucro(createTransactionDTO.getCart(), paymentMethod);
    }

}