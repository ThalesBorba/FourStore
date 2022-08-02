package br.com.fourcamp.fourstore.fourstore.controller;

import br.com.fourcamp.fourstore.fourstore.dto.request.CreateProductDTO;
import br.com.fourcamp.fourstore.fourstore.dto.request.CreateStockDTO;
import br.com.fourcamp.fourstore.fourstore.dto.response.ReturnStockDTO;
import br.com.fourcamp.fourstore.fourstore.dto.response.ReturnTransactionDTO;
import br.com.fourcamp.fourstore.fourstore.entities.Product;
import br.com.fourcamp.fourstore.fourstore.entities.Stock;
import br.com.fourcamp.fourstore.fourstore.service.StockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class StockControllerTest {

    public static final String SKU = "KSR1010405023150";
    @InjectMocks
    StockController stockController;

    @Mock
    StockService stockService;

    public static final Integer ID = 1;
    public static final Integer INDEX = 0;

    private CreateProductDTO createProductDTO;
    private CreateStockDTO createStockDTO;
    private Product product;
    private Stock stock;
    private ReturnStockDTO returnStockDTO;

    @BeforeEach
    void setUp () {
        MockitoAnnotations.openMocks(this);
        startCreateProductDTO();
        startCreateStockDTO();
        startStock();
    }

    @Test
    void whenCreateStockThenReturnCreatedResponse() {
        when(stockService.createStock(createStockDTO)).thenReturn(stock);

        ResponseEntity<String> response = stockController.createStock(50, createProductDTO);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(String.class, response.getBody().getClass());
    }

    @Test
    void listAll() {
        when(stockService.listAll()).thenReturn(new ArrayList<>(List.of(stock)));

        ResponseEntity<List<ReturnStockDTO>> response = stockController.listAll();

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ArrayList.class, response.getBody().getClass());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ReturnStockDTO.class, response.getBody().get(INDEX).getClass());
    }

    @Test
    void whenFindBySkuThen() {
        when(stockService.findBySku(SKU)).thenReturn(stock);

        ResponseEntity<ReturnStockDTO> response = stockController.findBySku(SKU);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ReturnStockDTO.class, response.getBody().getClass());
        assertEquals(SKU, response.getBody().getSku());
        assertEquals(ID, response.getBody().getId());
        assertEquals(50, response.getBody().getQuantity());
        assertEquals("Calça Teste", response.getBody().getProductDescription());
    }

    @Test
    void updateProductPrice() {
    }

    @Test
    void addProductsToStock() {
    }

    @Test
    void deleteBySku() {
    }

    private void startCreateProductDTO () {
        createProductDTO = new CreateProductDTO(SKU, "Calça Teste", 10.0, 30.0);
    }

    private void startCreateStockDTO () {
        product = new Product(SKU, "Calça Teste", 10.0, 30.0,
                "Kosair", "Tamanho RN", "Masculino", "Verão", "Vestuário", "Calça", "Vermelho");
        createStockDTO = new CreateStockDTO(product, 50);
    }

    private void startStock () {
        product = new Product(SKU, "Calça Teste", 10.0, 30.0,
                "Kosair", "Tamanho RN", "Masculino", "Verão", "Vestuário", "Calça", "Vermelho");
        createStockDTO = new CreateStockDTO(product, 50);
        stock = new Stock(ID, product, 50);
    }

    private void startReturnStockDTO () {
        returnStockDTO = new ReturnStockDTO(ID, "Calça Teste", SKU, 50);
    }
}