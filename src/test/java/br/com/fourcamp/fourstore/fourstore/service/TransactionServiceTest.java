package br.com.fourcamp.fourstore.fourstore.service;

import br.com.fourcamp.fourstore.fourstore.dto.request.CreateTransactionDTO;
import br.com.fourcamp.fourstore.fourstore.dto.response.ReturnTransactionDTO;
import br.com.fourcamp.fourstore.fourstore.entities.Client;
import br.com.fourcamp.fourstore.fourstore.entities.Product;
import br.com.fourcamp.fourstore.fourstore.entities.Stock;
import br.com.fourcamp.fourstore.fourstore.entities.Transaction;
import br.com.fourcamp.fourstore.fourstore.exceptions.ClientNotFoundException;
import br.com.fourcamp.fourstore.fourstore.exceptions.ProductNotFoundException;
import br.com.fourcamp.fourstore.fourstore.exceptions.StockInsufficientException;
import br.com.fourcamp.fourstore.fourstore.exceptions.TransactionNotFoundException;
import br.com.fourcamp.fourstore.fourstore.repositories.ClientRepository;
import br.com.fourcamp.fourstore.fourstore.repositories.ProductRepository;
import br.com.fourcamp.fourstore.fourstore.repositories.StockRepository;
import br.com.fourcamp.fourstore.fourstore.repositories.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static br.com.fourcamp.fourstore.fourstore.constants.Constants.INDEX;
import static br.com.fourcamp.fourstore.fourstore.constants.Constants.LONG_ID;
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
        assertEquals(LONG_ID, response.getId());
        assertEquals(LONG_ID, response.getClient().getId());
        assertEquals("Jose", response.getClient().getName());
        assertEquals("562.738.720-31", response.getClient().getCpf());
        assertEquals(6, response.getClient().getPaymentMethod());
        assertEquals("Cash", response.getClient().getPaymentData());
        assertEquals(170, response.getProfit());
    }

    @Test
    void whenListAllThenReturnAListOfTransactions() {
        when(transactionRepository.findAll()).thenReturn(List.of(transaction));

        List<ReturnTransactionDTO> response = transactionService.listAll();

        assertNotNull(response);
        assertEquals(ArrayList.class, response.getClass());
        assertEquals(ReturnTransactionDTO.class, response.get(INDEX).getClass());
        assertEquals(LONG_ID, response.get(INDEX).getId());
    }

    @Test
    void whenFindByIdThenReturnSuccess() {
        when(transactionRepository.findById(LONG_ID)).thenReturn(Optional.ofNullable(transaction));


        ReturnTransactionDTO response = transactionService.findById(LONG_ID);

        assertNotNull(response);
        assertEquals(ReturnTransactionDTO.class, response.getClass());
        assertEquals(LONG_ID, response.getId());
        assertEquals("Jose", response.getClientName());
        assertEquals("562.738.720-31", response.getClientCpf());
        assertEquals(170.0, response.getProfit());
    }

    @Test
    void whenClientNotFoundShouldThrowClientNotFoundException() {
        String cpf = "111.111.111-33";

        assertThrows(ClientNotFoundException.class, () -> transactionService.createTransaction(createTransactionDTO2));
        assertEquals("Nenhum cliente encontrado com o cpf " + cpf, new ClientNotFoundException(cpf).getMessage());
    }

    @Test
    void whenTransactionNotFoundShouldThrowTransactionNotFoundException() {

        assertThrows(TransactionNotFoundException.class, () -> transactionService.findById(1L));
        //Abrir com notepad e salvar como UTF8
        assertEquals("Nenhuma transação encontrada com a ID " + 1, new TransactionNotFoundException(1L).getMessage());
    }

    private void startCreateTransactionDTOs() {
        product = new Product("KSR1010405023150", "Camisa teste", 10.0,
                30.0, null, null, null, null, null, null, null);
        stock = new Stock(1, product, 50);
        map.put("OBT3711415123655", 10);
        createTransactionDTO = new CreateTransactionDTO("562.738.720-31", map);
        createTransactionDTO2 = new CreateTransactionDTO(null, map);
    }

    private void startClient() {
        client = new Client(LONG_ID, "562.738.720-31", "Jose", 6,
                "Cash", null);
    }

    private void startReturnTransactionDTO () {
        returnTransactionDTO = new ReturnTransactionDTO(LONG_ID, "Jose", "562.738.720-31", 170.0);
    }

    private void startTransaction() {
        transaction = new Transaction(LONG_ID, new Client(LONG_ID, "562.738.720-31", "Jose", 6,
                "Cash", null), 170.0);
    }
}