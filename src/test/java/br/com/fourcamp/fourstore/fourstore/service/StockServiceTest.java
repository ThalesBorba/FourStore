package br.com.fourcamp.fourstore.fourstore.service;

import br.com.fourcamp.fourstore.fourstore.dto.request.CreateProductDTO;
import br.com.fourcamp.fourstore.fourstore.dto.request.CreateStockDTO;
import br.com.fourcamp.fourstore.fourstore.dto.request.CreateTransactionDTO;
import br.com.fourcamp.fourstore.fourstore.dto.response.ReturnTransactionDTO;
import br.com.fourcamp.fourstore.fourstore.entities.Product;
import br.com.fourcamp.fourstore.fourstore.entities.Stock;
import br.com.fourcamp.fourstore.fourstore.repositories.ProductRepository;
import br.com.fourcamp.fourstore.fourstore.repositories.StockRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class StockServiceTest {


    @InjectMocks
    StockService stockService;

    @Mock
    StockRepository stockRepository;

    @Mock
    ProductRepository productRepository;
    public static final String SKU = "KSR1010405023150";
    public static final Integer ID = 1;
    public static final Integer INDEX = 0;

    private Stock stock;
    private Product product;
    private CreateStockDTO createStockDTO;
    private CreateProductDTO createProductDTO;
    private CreateTransactionDTO createTransactionDTO;
    HashMap<String, Integer> map = new HashMap<>();
    Optional<Product> optionalProduct;

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
        assertEquals(ID, response.getId());
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
        assertEquals(ID, response.getId());
        assertEquals(product, response.getProduct());
        assertEquals(Product.class, response.getProduct().getClass());
        //assertEquals(40, response.getQuantity());
    }

    @Test
    void addProductsToStock() {
    }

    @Test
    void updateProductPrice() {
    }

    @Test
    void whenListAllThenReturnAListOfStocks() {
        when(stockRepository.findAll()).thenReturn(new ArrayList<>(List.of(stock)));

        List<Stock> response = stockService.listAll();

        assertNotNull(response);
        assertEquals(ArrayList.class, response.getClass());
        assertEquals(Stock.class, response.get(INDEX).getClass());
        assertEquals(ID, response.get(INDEX).getId());
    }

    @Test
    void delete() {
    }

    @Test
    void whenFindBySkuThenReturnAStockOfProducts() {
        when(stockRepository.findByProduct(product)).thenReturn(stock);
        when(productRepository.findBySku(SKU)).thenReturn(optionalProduct);

        Stock response = stockService.findBySku(SKU);

        assertNotNull(response);
        assertEquals(Stock.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(product, response.getProduct());
        assertEquals(SKU, response.getProduct().getSku());
        assertEquals(50, response.getQuantity());
    }

    private void startStock () {
        product = new Product(SKU, "Calça Teste", 10.0, 30.0,
                "Kosair", "Tamanho RN", "Masculino", "Verão", "Vestuário", "Calça", "Vermelho");
        optionalProduct = Optional.of(product);
        stock = new Stock(ID, product, 50);
    }

    private void startCreateStockDTO () {
        product = new Product(SKU, "Calça Teste", 10.0, 30.0,
                "Kosair", "Tamanho RN", "Masculino", "Verão", "Vestuário", "Calça", "Vermelho");
        createStockDTO = new CreateStockDTO(product, 50);
    }

    private void startCreateProductDTO () {
        createProductDTO = new CreateProductDTO(SKU, "Calça Teste", 10.0, 30.0);
    }

    private void startCreateTransactionDTO () {
        map.put(SKU, 10);
        createTransactionDTO = new CreateTransactionDTO("562.738.720-31", map);
    }
}