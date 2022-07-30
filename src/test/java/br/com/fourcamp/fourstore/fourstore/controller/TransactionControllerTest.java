package br.com.fourcamp.fourstore.fourstore.controller;

import br.com.fourcamp.fourstore.fourstore.dto.response.ReturnTransactionDTO;
import br.com.fourcamp.fourstore.fourstore.entities.Transaction;
import br.com.fourcamp.fourstore.fourstore.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
class TransactionControllerTest {

    public static final Long ID = 1L;
    private ReturnTransactionDTO returnTransactionDTO;
    private Optional<ReturnTransactionDTO> optionalReturnTransactionDTO;

    @InjectMocks
    private TransactionController transactionController;

    @Mock
    private TransactionService transactionService;

    @BeforeEach
    void setUp () {
        MockitoAnnotations.openMocks(this);
        startReturnTransactionDTO();
    }

    @Test
    void createTransaction() {
    }

    @Test
    void listAll() {
    }

    @Test
    void WhenFindByIdThenReturnATransaction() {
        when(transactionService.findById(ID)).thenReturn(returnTransactionDTO);

        ResponseEntity<ReturnTransactionDTO> response = transactionController.findById(ID);
        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ReturnTransactionDTO.class, response.getBody().getClass());
    }

    private void startReturnTransactionDTO () {
        returnTransactionDTO = new ReturnTransactionDTO(ID, "Jose", "562.738.720-31", 170.0);
        optionalReturnTransactionDTO = Optional.of(returnTransactionDTO);
    }

}