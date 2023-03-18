package br.com.fourcamp.fourstore.service;

import br.com.fourcamp.fourstore.dto.request.CreateTransactionDTO;
import br.com.fourcamp.fourstore.dto.response.ReturnTransactionDTO;
import br.com.fourcamp.fourstore.entities.Client;
import br.com.fourcamp.fourstore.entities.Product;
import br.com.fourcamp.fourstore.entities.Transaction;
import br.com.fourcamp.fourstore.exceptions.ClientNotFoundException;
import br.com.fourcamp.fourstore.exceptions.ProductNotFoundException;
import br.com.fourcamp.fourstore.exceptions.TransactionNotFoundException;
import br.com.fourcamp.fourstore.repositories.ClientRepository;
import br.com.fourcamp.fourstore.repositories.ProductRepository;
import br.com.fourcamp.fourstore.repositories.TransactionRepository;
import br.com.fourcamp.fourstore.util.CartMethods;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private StockService stockService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ClientRepository clientRepository;

    public Transaction createTransaction(CreateTransactionDTO createTransactionDTO) {
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
        returnTransactionDTO.setNameAndCpf(transaction.getClient().getName(), transaction.getClient().getCpf());
        return returnTransactionDTO;
    }

    private Transaction setTransaction(CreateTransactionDTO createTransactionDTO) {
        CreateTransactionDTO validTrasaction = validTransaction(createTransactionDTO);
        Transaction transactionToSave = new Transaction();
        BeanUtils.copyProperties(validTrasaction, transactionToSave);
        transactionToSave.setClientAndProfit(returnTransactionClient(createTransactionDTO), calculateProfit(validTrasaction));
        return transactionRepository.save(transactionToSave);
    }

    public ReturnTransactionDTO findById(UUID id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(
                () -> new TransactionNotFoundException(id));
        return convertTransactionToDTO(transaction);
    }

    private CreateTransactionDTO validTransaction(CreateTransactionDTO createTransactionDTO) {
        stockService.updateByTransaction(createTransactionDTO);
        return createTransactionDTO;
    }

    private BigDecimal calculateProfit(CreateTransactionDTO createTransactionDTO) {
        Client client = returnTransactionClient(createTransactionDTO);
        Integer paymentMethod = client.getPaymentMethod();
        HashMap<Product, Integer> cart = new HashMap<>();
        for (Map.Entry<String,Integer> products : createTransactionDTO.getCart().entrySet()) {
            Product product = productRepository.findBySku(products.getKey()).orElseThrow(() ->
                    new ProductNotFoundException(products.getKey()));
            cart.put(product, products.getValue());
        }
        return CartMethods.returnProfit(cart, paymentMethod);
    }

    private Client returnTransactionClient(CreateTransactionDTO createTransactionDTO) {
        return clientRepository.findByCpf(createTransactionDTO.getClientCpf()).orElseThrow(() ->
                new ClientNotFoundException(createTransactionDTO.getClientCpf()));
    }

}