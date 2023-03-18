package br.com.fourcamp.fourstore.service;

import br.com.fourcamp.fourstore.dto.request.CreateProductDTO;
import br.com.fourcamp.fourstore.dto.request.CreateStockDTO;
import br.com.fourcamp.fourstore.dto.request.CreateTransactionDTO;
import br.com.fourcamp.fourstore.entities.Product;
import br.com.fourcamp.fourstore.entities.Stock;
import br.com.fourcamp.fourstore.repositories.ProductRepository;
import br.com.fourcamp.fourstore.repositories.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static br.com.fourcamp.fourstore.constants.Constants.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class StockServiceTest {


    @InjectMocks
    StockService stockService;

    @Mock
    StockRepository stockRepository;

    @Mock
    ProductRepository productRepository;

    private Stock stock;
    private Product product;
    private CreateStockDTO createStockDTO;
    private CreateProductDTO createProductDTO;
    private CreateTransactionDTO createTransactionDTO;
    HashMap<String, Integer> map = new HashMap<>();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startStock();
        startCreateProductDTO();
        startCreateStockDTO();
        startCreateTransactionDTO();
    }


    @Test
    void shouldCreateAStockOfProducts() {
        when(stockRepository.save(any())).thenReturn(stock);

        Stock response = stockService.createStock(createStockDTO);

        assertNotNull(response);
        assertEquals(Stock.class, response.getClass());
        assertEquals(UUID_ID, response.getId());
        assertEquals(product, response.getProduct());
        assertEquals(Product.class, response.getProduct().getClass());
        assertEquals(50, response.getQuantity());
    }

    @Test
    void shouldReduceTheStockQuantityAfterATransaction() {
        when(stockRepository.save(any())).thenReturn(stock);

        Stock response = stock;
        stockService.updateByTransaction(createTransactionDTO);


        assertNotNull(response);
        assertEquals(Stock.class, response.getClass());
        assertEquals(UUID_ID, response.getId());
        assertEquals(product, response.getProduct());
        assertEquals(Product.class, response.getProduct().getClass());
        //generate log for each product, then create test
    }

    @Test
    void shouldAddProductsToStockThenReturnMessage() {
        when(productRepository.findBySku(SKU)).thenReturn(Optional.ofNullable(product));
        when(stockService.findBySku(SKU)).thenReturn(stock);

        String response = stockService.addProductsToStock(SKU, 10);

        assertNotNull(response);
        assertEquals(String.class, response.getClass());
        assertEquals("O produto com a sku " + SKU + " agora possui " + 60 + " unidades no estoque", response);
    }

    @Test
    void ShouldUpdateProductPricesThenReturnMessage() {
        when(productRepository.findBySku(SKU)).thenReturn(Optional.ofNullable(product));
        when(stockService.findBySku(SKU)).thenReturn(stock);

        String response = stockService.updateProductPrice(SKU, BigDecimal.valueOf(15.0), BigDecimal.valueOf(45.0));

        assertNotNull(response);
        assertEquals(String.class, response.getClass());
        assertEquals("Preços do produto com a sku " + SKU + " atualizados. Novos preços: compra: " + 15.0
                + ", venda: " + 45.0, response);
    }

    @Test
    void whenListAllThenReturnAListOfStocks() {
        when(stockRepository.findAll()).thenReturn(new ArrayList<>(List.of(stock)));

        List<Stock> response = stockService.listAll();

        assertNotNull(response);
        assertEquals(ArrayList.class, response.getClass());
        assertEquals(Stock.class, response.get(INDEX).getClass());
        assertEquals(UUID_ID, response.get(INDEX).getId());
    }

    @Test
    void shouldDeleteStockThenReturnSuccessMessage() {
        when(productRepository.findBySku(SKU)).thenReturn(Optional.ofNullable(product));
        when(stockService.findBySku(SKU)).thenReturn(stock);

        String response = stockService.delete(SKU);

        assertNotNull(response);
        assertEquals(String.class, response.getClass());
        assertEquals( "Estoque do produto com a sku " + SKU + "removido", response);
    }

    @Test
    void whenFindBySkuThenReturnAStockOfProducts() {
        when(stockRepository.findByProduct(product)).thenReturn(stock);
        when(productRepository.findBySku(SKU)).thenReturn(Optional.ofNullable(product));

        Stock response = stockService.findBySku(SKU);

        assertNotNull(response);
        assertEquals(Stock.class, response.getClass());
        assertEquals(UUID_ID, response.getId());
        assertEquals(product, response.getProduct());
        assertEquals(SKU, response.getProduct().getSku());
        assertEquals(50, response.getQuantity());
    }

    private void startStock () {
        product = new Product(SKU, "Calça Teste", BigDecimal.valueOf(10), BigDecimal.valueOf(30),
                "Kosair", "Tamanho RN", "Masculino", "Verão", "Vestuário", "Calça", "Vermelho");
        stock = new Stock(UUID_ID, product, 50);
    }

    private void startCreateStockDTO () {
        product = new Product(SKU, "Calça Teste", BigDecimal.valueOf(10), BigDecimal.valueOf(30),
                "Kosair", "Tamanho RN", "Masculino", "Verão", "Vestuário", "Calça", "Vermelho");
        createStockDTO = new CreateStockDTO(product, 50);
    }

    private void startCreateProductDTO () {
        createProductDTO = new CreateProductDTO(SKU, "Calça Teste", BigDecimal.valueOf(10), BigDecimal.valueOf(30));
    }

    private void startCreateTransactionDTO () {
        map.put(SKU, 10);
        createTransactionDTO = new CreateTransactionDTO("562.738.720-31", map);
    }
}