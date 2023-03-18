package br.com.fourcamp.fourstore.controller;

import br.com.fourcamp.fourstore.constants.Constants;
import br.com.fourcamp.fourstore.dto.request.CreateProductDTO;
import br.com.fourcamp.fourstore.dto.request.CreateStockDTO;
import br.com.fourcamp.fourstore.dto.response.ReturnStockDTO;
import br.com.fourcamp.fourstore.entities.Product;
import br.com.fourcamp.fourstore.entities.Stock;
import br.com.fourcamp.fourstore.service.StockService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
class StockControllerTest {

    public static final String SKU = "KSR1010405023150";
    @InjectMocks
    StockController stockController;

    @Mock
    StockService stockService;

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
        assertEquals("Estoque criado", response.getBody());
    }

    @Test
    void whenListAllThenReturnAListOfReturnStockDTO() {
        when(stockService.listAll()).thenReturn(List.of(stock));

        ResponseEntity<List<ReturnStockDTO>> response = stockController.listAll();

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ReturnStockDTO.class, response.getBody().get(Constants.INDEX).getClass());
    }

    @Test
    void whenFindBySkuThenReturnAReturnStockDTO() {
        when(stockService.findBySku(SKU)).thenReturn(stock);

        ResponseEntity<ReturnStockDTO> response = stockController.findBySku(SKU);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ReturnStockDTO.class, response.getBody().getClass());
        assertEquals(SKU, response.getBody().getSku());
        Assertions.assertEquals(Constants.UUID_ID, response.getBody().getId());
        assertEquals(50, response.getBody().getQuantity());
        assertEquals("Cal�a Teste", response.getBody().getProductDescription());
    }

    @Test
    void WhenUpdateProductPricesShouldReturnAcceptedResponse() {
        when(stockService.updateProductPrice(SKU, BigDecimal.valueOf(15), BigDecimal.valueOf(45))).thenReturn("Preços do produto com a sku "
                + SKU + " atualizados. Novos preços: compra: " + 15.0 + ", venda: " + 45.0);

        ResponseEntity<String> response = stockController.updateProductPrice(SKU, BigDecimal.valueOf(15), BigDecimal.valueOf(45));

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(String.class, response.getBody().getClass());
        assertEquals("Preços do produto com a sku " + SKU + " atualizados. Novos preços: compra: " + 15.0 +
                ", venda: " + 45.0, response.getBody());
    }

    @Test
    void WhenAddProductsToStockThenReturnAcceptedResponse() {
        when(stockService.addProductsToStock(SKU, 10)).thenReturn("O produto com a sku " + SKU +
                " agora possui " + 60 + " unidades no estoque");

        ResponseEntity<String> response = stockController.addProductsToStock(SKU, 10);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(String.class, response.getBody().getClass());
        assertEquals("O produto com a sku " + SKU + " agora possui " + 60 + " unidades no estoque",
                response.getBody());
    }

    @Test
    void shouldDeleteBySkuThenReturnMessage() {
        when(stockService.delete(SKU)).thenReturn("Estoque do produto com a sku " + SKU + "removido");

        ResponseEntity<String> response = stockController.deleteBySku(SKU);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(String.class, response.getBody().getClass());
        assertEquals("Estoque do produto com a sku " + SKU + "removido", response.getBody());

    }

    private void startCreateProductDTO () {
        createProductDTO = new CreateProductDTO(SKU, "Calça Teste", BigDecimal.valueOf(10), BigDecimal.valueOf(30));
    }

    private void startCreateStockDTO () {
        product = new Product(SKU, "Calça Teste", BigDecimal.valueOf(10), BigDecimal.valueOf(30),
                "Kosair", "Tamanho RN", "Masculino", "Verão", "Vestuário", "Calça", "Vermelho");
        createStockDTO = new CreateStockDTO(product, 50);
    }

    private void startStock () {
        product = new Product(SKU, "Cal�a Teste", BigDecimal.valueOf(10), BigDecimal.valueOf(30),
                "Kosair", "Tamanho RN", "Masculino", "Ver�o", "Vestu�rio", "Cal�a", "Vermelho");
        createStockDTO = new CreateStockDTO(product, 50);
        stock = new Stock(Constants.UUID_ID, product, 50);
    }

    private void startReturnStockDTO () {
        returnStockDTO = new ReturnStockDTO(Constants.UUID_ID, "Cal�a Teste", SKU, 50);
    }
}