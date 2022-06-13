package br.com.fourcamp.fourstore.FourStore.service;

import br.com.fourcamp.fourstore.FourStore.dto.request.CreateTransactionDTO;
import br.com.fourcamp.fourstore.FourStore.dto.response.MessageResponseDTO;
import br.com.fourcamp.fourstore.FourStore.dto.response.ReturnTransactionDTO;
import br.com.fourcamp.fourstore.FourStore.entities.Client;
import br.com.fourcamp.fourstore.FourStore.entities.Product;
import br.com.fourcamp.fourstore.FourStore.entities.Transaction;
import br.com.fourcamp.fourstore.FourStore.exceptions.*;
import br.com.fourcamp.fourstore.FourStore.mapper.TransactionMapper;
import br.com.fourcamp.fourstore.FourStore.repositories.ClientRepository;
import br.com.fourcamp.fourstore.FourStore.repositories.ProductRepository;
import br.com.fourcamp.fourstore.FourStore.repositories.TransactionRepository;
import br.com.fourcamp.fourstore.FourStore.util.CartMethods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TransactionService {

    private TransactionRepository transactionRepository;
    @Autowired
    private StockService stockService;
    private ProductRepository productRepository;
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
        transactionToSave.setClient(ReturnTransactionClient(createTransactionDTO));
        return transactionRepository.save(transactionToSave);
    }

    public ReturnTransactionDTO findById(Long id) throws TransactionNotFoundException {
        Transaction transaction = verifyIfExists(id);
        return transactionMapper.toDTO(transaction);
    }

    private CreateTransactionDTO validTransaction(CreateTransactionDTO createTransactionDTO) throws
            StockNotFoundException, InvalidParametersException, StockInsufficientException {
        stockService.updateByTransaction(createTransactionDTO);
        return createTransactionDTO;
    }

    private Double calculateProfit(CreateTransactionDTO createTransactionDTO) throws InvalidParametersException, ClientNotFoundException {
        Client client = ReturnTransactionClient(createTransactionDTO);
        Integer paymentMethod = client.getPaymentMethod();
        HashMap<Product, Integer> cart = new HashMap<>();
        for (Map.Entry<String,Integer> products : createTransactionDTO.getCart().entrySet()) {
            Product product = productRepository.findBySku(products.getKey());
            cart.put(product, products.getValue());
        }
        return CartMethods.retornaLucro(cart, paymentMethod);
    }

    private Client ReturnTransactionClient(CreateTransactionDTO createTransactionDTO) throws ClientNotFoundException {
        Client client = clientRepository.findByCpf(createTransactionDTO.getClientCpf());
        if (client != null) {
            return client;
        } else {
            throw new ClientNotFoundException(createTransactionDTO.getClientCpf());
        }
    }

}