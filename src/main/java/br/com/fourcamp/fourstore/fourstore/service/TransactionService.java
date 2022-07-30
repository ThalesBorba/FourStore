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

    public Transaction createTransaction(CreateTransactionDTO createTransactionDTO) throws
            ClientNotFoundException, StockNotFoundException, InvalidParametersException, StockInsufficientException,
            ProductNotFoundException {
        return setTransaction(createTransactionDTO);
    }

    public List<ReturnTransactionDTO> listAll() {
        List<Transaction> allTransactions = transactionRepository.findAll();
        List<ReturnTransactionDTO> returnTransactionDTOList = new ArrayList<>();
        for (Transaction transaction : allTransactions) {
            ReturnTransactionDTO returnTransactionDTO = convertTransactionToDTO(transaction);
            returnTransactionDTOList.add(returnTransactionDTO);
        }
        return returnTransactionDTOList;
    }

    private ReturnTransactionDTO convertTransactionToDTO(Transaction transaction) {
        ReturnTransactionDTO returnTransactionDTO = new ReturnTransactionDTO();
        BeanUtils.copyProperties(transaction, returnTransactionDTO);
        returnTransactionDTO.setClientCpf(transaction.getClient().getCpf());
        returnTransactionDTO.setClientName(transaction.getClient().getName());
        return returnTransactionDTO;
    }

    private Transaction verifyIfExists(Long id) throws TransactionNotFoundException {
        return transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException(id));
    }

    private Transaction setTransaction(CreateTransactionDTO createTransactionDTO) throws ClientNotFoundException,
            StockNotFoundException, InvalidParametersException, StockInsufficientException, ProductNotFoundException {
        CreateTransactionDTO validTrasaction = validTransaction(createTransactionDTO);
        Double profit = calculateProfit(validTrasaction);
        Transaction transactionToSave = new Transaction();
        BeanUtils.copyProperties(validTrasaction, transactionToSave);
        transactionToSave.setProfit(profit);
        transactionToSave.setClient(returnTransactionClient(createTransactionDTO));
        return transactionRepository.save(transactionToSave);
    }

    public ReturnTransactionDTO findById(Long id) throws TransactionNotFoundException {
        return convertTransactionToDTO(verifyIfExists(id));
    }

    private CreateTransactionDTO validTransaction(CreateTransactionDTO createTransactionDTO) throws
            InvalidParametersException, StockInsufficientException {
        stockService.updateByTransaction(createTransactionDTO);
        return createTransactionDTO;
    }

    private Double calculateProfit(CreateTransactionDTO createTransactionDTO) throws InvalidParametersException,
            ClientNotFoundException {
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

}