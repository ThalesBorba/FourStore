package br.com.fourcamp.fourstore.service;

import br.com.fourcamp.fourstore.dto.request.CreateTransactionDTO;
import br.com.fourcamp.fourstore.dto.response.ReturnTransactionDTO;
import br.com.fourcamp.fourstore.entities.Client;
import br.com.fourcamp.fourstore.entities.Product;
import br.com.fourcamp.fourstore.entities.Stock;
import br.com.fourcamp.fourstore.entities.Transaction;
import br.com.fourcamp.fourstore.exceptions.ClientNotFoundException;
import br.com.fourcamp.fourstore.exceptions.ProductNotFoundException;
import br.com.fourcamp.fourstore.exceptions.StockInsufficientException;
import br.com.fourcamp.fourstore.exceptions.TransactionNotFoundException;
import br.com.fourcamp.fourstore.repositories.ClientRepository;
import br.com.fourcamp.fourstore.repositories.ProductRepository;
import br.com.fourcamp.fourstore.repositories.StockRepository;
import br.com.fourcamp.fourstore.repositories.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.*;

import static br.com.fourcamp.fourstore.constants.Constants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class TransactionServiceTest {

    @InjectMocks
    TransactionService transactionService;

    @Mock
    TransactionRepository transactionRepository;
    @Mock
    ProductRepository productRepository;
    @Mock
    ClientRepository clientRepository;
    @Mock
    StockRepository stockRepository;
    @Mock
    ProductService productService;

    @Mock
    StockService stockService;
    HashMap<String, Integer> map = new HashMap<>();

    private Transaction transaction;
    private CreateTransactionDTO createTransactionDTO;
    private CreateTransactionDTO createTransactionDTO2;
    private ReturnTransactionDTO returnTransactionDTO;
    private Product product;
    private Stock stock;
    private Client client;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startTransaction();
        startCreateTransactionDTOs();
        startReturnTransactionDTO();
        startClient();
    }

    @Test
    void shouldCreateATransaction() throws ClientNotFoundException, StockInsufficientException, ProductNotFoundException {
        when(transactionRepository.save(any())).thenReturn(transaction);
        when(clientRepository.findByCpf("562.738.720-31")).thenReturn(Optional.ofNullable(client));
        when(productRepository.findBySku("OBT3711415123655")).thenReturn(Optional.ofNullable(product));

        Transaction response = transactionService.createTransaction(createTransactionDTO);

        assertNotNull(response);
        assertEquals(Transaction.class, response.getClass());
        assertEquals(UUID_ID, response.getId());
        assertEquals(UUID_ID, response.getClient().getId());
        assertEquals("Jose", response.getClient().getName());
        assertEquals("562.738.720-31", response.getClient().getCpf());
        assertEquals(6, response.getClient().getPaymentMethod());
        assertEquals("Cash", response.getClient().getPaymentData());
        assertEquals(BigDecimal.valueOf(170.0), response.getProfit());
    }

    @Test
    void whenListAllThenReturnAListOfTransactions() {
        when(transactionRepository.findAll()).thenReturn(List.of(transaction));

        List<ReturnTransactionDTO> response = transactionService.listAll();

        assertNotNull(response);
        assertEquals(ArrayList.class, response.getClass());
        assertEquals(ReturnTransactionDTO.class, response.get(INDEX).getClass());
        assertEquals(UUID_ID, response.get(INDEX).getId());
    }

    @Test
    void whenFindByIdThenReturnSuccess() {
        when(transactionRepository.findById(UUID_ID)).thenReturn(Optional.ofNullable(transaction));


        ReturnTransactionDTO response = transactionService.findById(UUID_ID);

        assertNotNull(response);
        assertEquals(ReturnTransactionDTO.class, response.getClass());
        assertEquals(UUID_ID, response.getId());
        assertEquals("Jose", response.getClientName());
        assertEquals("562.738.720-31", response.getClientCpf());
        assertEquals(BigDecimal.valueOf(170.0), response.getProfit());
    }

    @Test
    void whenClientNotFoundShouldThrowClientNotFoundException() {
        String cpf = "111.111.111-33";

        assertThrows(ClientNotFoundException.class, () -> transactionService.createTransaction(createTransactionDTO2));
        assertEquals("Nenhum cliente encontrado com o cpf " + cpf, new ClientNotFoundException(cpf).getMessage());
    }

    @Test
    void whenTransactionNotFoundShouldThrowTransactionNotFoundException() {

        assertThrows(TransactionNotFoundException.class, () -> transactionService.findById(UUID_ID));
        assertEquals("Nenhuma transação encontrada com a ID " + UUID_ID, new TransactionNotFoundException(UUID_ID).getMessage());
    }

    private void startCreateTransactionDTOs() {
        product = new Product("KSR1010405023150", "Camisa teste", BigDecimal.valueOf(10),
                BigDecimal.valueOf(30), null, null, null, null, null, null, null);
        stock = new Stock(UUID_ID, product, 50);
        map.put("OBT3711415123655", 10);
        createTransactionDTO = new CreateTransactionDTO("562.738.720-31", map);
        createTransactionDTO2 = new CreateTransactionDTO(null, map);
    }

    private void startClient() {
        client = new Client(UUID_ID, "562.738.720-31", "Jose", 6,
                "Cash", null);
    }

    private void startReturnTransactionDTO () {
        returnTransactionDTO = new ReturnTransactionDTO(UUID_ID, "Jose", "562.738.720-31", BigDecimal.valueOf(170.0));
    }

    private void startTransaction() {
        transaction = new Transaction(UUID_ID, new Client(UUID_ID, "562.738.720-31", "Jose", 6,
                "Cash", null), BigDecimal.valueOf(170.0));
    }
}