package br.com.fourcamp.fourstore.fourstore.service;

import br.com.fourcamp.fourstore.fourstore.dto.request.CreateProductDTO;
import br.com.fourcamp.fourstore.fourstore.dto.request.CreateStockDTO;
import br.com.fourcamp.fourstore.fourstore.entities.Product;
import br.com.fourcamp.fourstore.fourstore.entities.Stock;
import br.com.fourcamp.fourstore.fourstore.repositories.StockRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StockServiceTest {

    @InjectMocks
    StockService stockService;

    @Mock
    StockRepository stockRepository;

    public static final Integer ID = 1;

    private Stock stock;
    private Product product;
    private CreateStockDTO createStockDTO;
    private CreateProductDTO createProductDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startStock();
        startCreateProductDTO();
        startCreateStockDTO();
    }


    @Test
    void createStock() {
    }

    @Test
    void updateByTransaction() {
    }

    @Test
    void addProductsToStock() {
    }

    @Test
    void updateProductPrice() {
    }

    @Test
    void listAll() {
    }

    @Test
    void delete() {
    }

    @Test
    void findBySku() {
    }

    private void startStock () {
        product = new Product("KSR1010405023150", "Calca Teste", 10.0, 30.0,
                "Kosair", "Tamanho RN", "Masculino", "Verão", "Vestuário", "Calça", "Vermelho");
        stock = new Stock(ID, product, 50);
    }

    private void startCreateStockDTO () {
        product = new Product("KSR1010405023150", "Calca Teste", 10.0, 30.0,
                "Kosair", "Tamanho RN", "Masculino", "Verão", "Vestuário", "Calça", "Vermelho");
        createStockDTO = new CreateStockDTO(product, 50);
    }

    private void startCreateProductDTO () {
        createProductDTO = new CreateProductDTO("KSR1010405023150", "Calca Teste", 10.0, 30.0);
    }
}