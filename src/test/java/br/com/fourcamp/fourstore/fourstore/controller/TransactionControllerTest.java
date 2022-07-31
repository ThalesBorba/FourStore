package br.com.fourcamp.fourstore.fourstore.controller;

import br.com.fourcamp.fourstore.fourstore.dto.request.CreateTransactionDTO;
import br.com.fourcamp.fourstore.fourstore.dto.response.ReturnTransactionDTO;
import br.com.fourcamp.fourstore.fourstore.entities.Client;
import br.com.fourcamp.fourstore.fourstore.entities.Transaction;
import br.com.fourcamp.fourstore.fourstore.exceptions.*;
import br.com.fourcamp.fourstore.fourstore.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class TransactionControllerTest {

    public static final Long ID = 1L;
    public static final Integer INDEX = 0;
    private ReturnTransactionDTO returnTransactionDTO;
    private CreateTransactionDTO createTransactionDTO;
    private Transaction transaction;
    private Optional<ReturnTransactionDTO> optionalReturnTransactionDTO;

    @InjectMocks
    private TransactionController transactionController;

    @Mock
    private TransactionService transactionService;

    @BeforeEach
    void setUp () {
        MockitoAnnotations.openMocks(this);
        startReturnTransactionDTO();
        startCreateTransactionDTO();
        startTransaction();
    }

    @Test
    void whenCreateTransactionThenReturnCreated() throws ClientNotFoundException, StockNotFoundException,
            InvalidParametersException, ProductNotFoundException, StockInsufficientException {
        when(transactionService.createTransaction(createTransactionDTO)).thenReturn(transaction);

        ResponseEntity<String> response = transactionController.createTransaction(createTransactionDTO);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(String.class, response.getBody().getClass());
    }

    @Test
    void whenFindAllThenReturnListOfTransactions() {
        when(transactionService.listAll()).thenReturn(new ArrayList<>(List.of(returnTransactionDTO)));

        ResponseEntity<List<ReturnTransactionDTO>> response = transactionController.listAll();

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ArrayList.class, response.getBody().getClass());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ReturnTransactionDTO.class, response.getBody().get(INDEX).getClass());

    }

    @Test
    void WhenFindByIdThenReturnATransaction() {
        when(transactionService.findById(ID)).thenReturn(returnTransactionDTO);

        ResponseEntity<ReturnTransactionDTO> response = transactionController.findById(ID);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ReturnTransactionDTO.class, response.getBody().getClass());
    }

    private void startCreateTransactionDTO () {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("OBT3711415123655", 10);
        createTransactionDTO = new CreateTransactionDTO("562.738.720-31", map);
    }

    private void startReturnTransactionDTO () {
        returnTransactionDTO = new ReturnTransactionDTO(ID, "Jose", "562.738.720-31", 170.0);
        optionalReturnTransactionDTO = Optional.of(returnTransactionDTO);
    }

    private void startTransaction() {
        transaction = new Transaction(ID, new Client(ID, "562.738.720-31", "Jose", 6,
                "Cash", null), 170.0);
    }

}