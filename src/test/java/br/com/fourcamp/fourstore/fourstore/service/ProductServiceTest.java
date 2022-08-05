package br.com.fourcamp.fourstore.fourstore.service;

import br.com.fourcamp.fourstore.fourstore.entities.Product;
import br.com.fourcamp.fourstore.fourstore.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static br.com.fourcamp.fourstore.fourstore.constants.Constants.INDEX;
import static br.com.fourcamp.fourstore.fourstore.constants.Constants.SKU;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest
class ProductServiceTest {

    @InjectMocks
    ProductService productService;

    @Mock
    ProductRepository productRepository;

    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startProduct();
    }


    @Test
    void whenListAllShouldReturnAListOfProducts() {
        when(productRepository.findAll()).thenReturn(new ArrayList<>(List.of(product)));

        List<Product> response = productService.listAll();

        assertNotNull(response);
        assertEquals(ArrayList.class, response.getClass());
        assertEquals(Product.class, response.get(INDEX).getClass());
        assertEquals(SKU, response.get(INDEX).getSku());
    }

    @Test
    void whenFindBySkuWithDetailsShouldReturnDetailedProduct() {
        when(productRepository.findBySku(SKU)).thenReturn(Optional.ofNullable(product));

        Product response = productService.findBySkuWithDetails(SKU);
        assertNotNull(response);
        assertEquals(Product.class, response.getClass());
        assertEquals(SKU, response.getSku());
        assertEquals("Calça Teste", response.getDescription());
        assertEquals(10.0, response.getBuyPrice());
        assertEquals(30.0, response.getSellPrice());
        assertEquals("Kosair", response.getBrand());
        assertEquals("Tamanho RN", response.getSize());
        assertEquals("Masculino", response.getCategory());
        assertEquals("Verão", response.getSeason());
        assertEquals("Vestuário", response.getDepartment());
        assertEquals("Calça", response.getType());
        assertEquals("Vermelho", response.getColor());
    }

    private void startProduct () {
        product = new Product(SKU, "Calça Teste", 10.0, 30.0,
                "Kosair", "Tamanho RN", "Masculino", "Verão", "Vestuário", "Calça", "Vermelho");
    }
}