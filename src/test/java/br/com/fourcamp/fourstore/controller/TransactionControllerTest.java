package br.com.fourcamp.fourstore.controller;

import br.com.fourcamp.fourstore.constants.Constants;
import br.com.fourcamp.fourstore.dto.request.CreateTransactionDTO;
import br.com.fourcamp.fourstore.dto.response.ReturnTransactionDTO;
import br.com.fourcamp.fourstore.entities.Client;
import br.com.fourcamp.fourstore.entities.Transaction;
import br.com.fourcamp.fourstore.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
class TransactionControllerTest {

    private ReturnTransactionDTO returnTransactionDTO;
    private CreateTransactionDTO createTransactionDTO;
    private Transaction transaction;

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
    void whenCreateTransactionThenReturnCreated() {
        when(transactionService.createTransaction(createTransactionDTO)).thenReturn(transaction);

        ResponseEntity<String> response = transactionController.createTransaction(createTransactionDTO);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(String.class, response.getBody().getClass());
        assertEquals("Transação com a id " + transaction.getId() + " criada!", response.getBody());
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
        assertEquals(ReturnTransactionDTO.class, response.getBody().get(Constants.INDEX).getClass());

    }

    @Test
    void WhenFindByIdThenReturnATransaction() {
        when(transactionService.findById(Constants.UUID_ID)).thenReturn(returnTransactionDTO);

        ResponseEntity<ReturnTransactionDTO> response = transactionController.findById(Constants.UUID_ID);

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
        returnTransactionDTO = new ReturnTransactionDTO(Constants.UUID_ID, "Jose", "562.738.720-31", BigDecimal.valueOf(170.0));
    }

    private void startTransaction() {
        transaction = new Transaction(Constants.UUID_ID, new Client(Constants.UUID_ID, "562.738.720-31", "Jose", 6,
                "Cash", null), BigDecimal.valueOf(170.0));
    }

}