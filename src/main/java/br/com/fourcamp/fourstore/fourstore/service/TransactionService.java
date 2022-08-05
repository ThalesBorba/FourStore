package br.com.fourcamp.fourstore.fourstore.service;

import br.com.fourcamp.fourstore.fourstore.dto.request.CreateTransactionDTO;
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

    public ReturnTransactionDTO findById(Long id) {
        Transaction transaction = transactionRepository.findById(id).orElseThrow(
                () -> new TransactionNotFoundException(id));
        return convertTransactionToDTO(transaction);
    }

    private CreateTransactionDTO validTransaction(CreateTransactionDTO createTransactionDTO) {
        stockService.updateByTransaction(createTransactionDTO);
        return createTransactionDTO;
    }

    private Double calculateProfit(CreateTransactionDTO createTransactionDTO) {
        Client client = returnTransactionClient(createTransactionDTO);
        Integer paymentMethod = client.getPaymentMethod();
        HashMap<Product, Integer> cart = new HashMap<>();
        for (Map.Entry<String,Integer> products : createTransactionDTO.getCart().entrySet()) {
            Product product = productRepository.findBySku(products.getKey()).orElseThrow(() ->
                    new ProductNotFoundException(products.getKey()));
            cart.put(product, products.getValue());
        }
        return CartMethods.retornaLucro(cart, paymentMethod);
    }

    private Client returnTransactionClient(CreateTransactionDTO createTransactionDTO) {
        return clientRepository.findByCpf(createTransactionDTO.getClientCpf()).orElseThrow(() ->
                new ClientNotFoundException(createTransactionDTO.getClientCpf()));
    }

}