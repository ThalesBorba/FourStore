package br.com.fourcamp.fourstore.fourstore.service;

import br.com.fourcamp.fourstore.fourstore.dto.request.CreateTransactionDTO;
import br.com.fourcamp.fourstore.fourstore.dto.response.MessageResponseDTO;
import br.com.fourcamp.fourstore.fourstore.dto.response.ReturnTransactionDTO;
import br.com.fourcamp.fourstore.fourstore.entities.Client;
import br.com.fourcamp.fourstore.fourstore.entities.Product;
import br.com.fourcamp.fourstore.fourstore.entities.Transaction;
import br.com.fourcamp.fourstore.fourstore.exceptions.*;
import br.com.fourcamp.fourstore.fourstore.repositories.ClientRepository;
import br.com.fourcamp.fourstore.fourstore.repositories.ProductRepository;
import br.com.fourcamp.fourstore.fourstore.repositories.TransactionRepository;
import br.com.fourcamp.fourstore.fourstore.util.CartMethods;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    @Autowired
    private StockService stockService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ClientRepository clientRepository;

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
        return transactionRepository.findAll().stream().map(toDTO()).toList();
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
        CreateTransactionDTO validTransaction = validTransaction(createTransactionDTO);
        Double profit = calculateProfit(validTransaction);
        Transaction transactionToSave = new Transaction();
        BeanUtils.copyProperties(validTransaction, transactionToSave);
        transactionToSave.setProfit(profit);
        transactionToSave.setClient(returnTransactionClient(createTransactionDTO));
        return transactionRepository.save(transactionToSave);
    }

    public ReturnTransactionDTO findById(Long id) throws TransactionNotFoundException {
        Transaction transaction = verifyIfExists(id);
        ReturnTransactionDTO transactionDTO = new ReturnTransactionDTO();
        BeanUtils.copyProperties(transaction, transactionDTO);
        return transactionDTO;
    }

    private CreateTransactionDTO validTransaction(CreateTransactionDTO createTransactionDTO) throws
            InvalidParametersException, StockInsufficientException {
        stockService.updateByTransaction(createTransactionDTO);
        return createTransactionDTO;
    }

    private Double calculateProfit(CreateTransactionDTO createTransactionDTO) throws InvalidParametersException, ClientNotFoundException {
        Client client = returnTransactionClient(createTransactionDTO);
        Integer paymentMethod = client.getPaymentMethod();
        HashMap<Product, Integer> cart = new HashMap<>();
        for (Map.Entry<String,Integer> products : createTransactionDTO.getCart().entrySet()) {
            Product product = productRepository.findBySku(products.getKey());
            cart.put(product, products.getValue());
        }
        return CartMethods.retornaLucro(cart, paymentMethod);
    }

    private Client returnTransactionClient(CreateTransactionDTO createTransactionDTO) throws ClientNotFoundException {
        Client client = clientRepository.findByCpf(createTransactionDTO.getClientCpf());
        if (client != null) {
            return client;
        } else {
            throw new ClientNotFoundException(createTransactionDTO.getClientCpf());
        }
    }

    private Function<Transaction, ReturnTransactionDTO> toDTO() {
        return transaction -> {
            var returnTransactionDTO = new ReturnTransactionDTO();
            BeanUtils.copyProperties(transaction, returnTransactionDTO);
            return returnTransactionDTO;
        };
    }

}