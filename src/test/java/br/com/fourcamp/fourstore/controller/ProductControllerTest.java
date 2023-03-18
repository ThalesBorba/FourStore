package br.com.fourcamp.fourstore.controller;

import br.com.fourcamp.fourstore.constants.Constants;
import br.com.fourcamp.fourstore.dto.response.ReturnProductDTO;
import br.com.fourcamp.fourstore.entities.Product;
import br.com.fourcamp.fourstore.service.ProductService;
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
class ProductControllerTest {

    @InjectMocks
    ProductController productController;

    @Mock
    ProductService productService;
    private Product product;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startProduct();
    }

    @Test
    void whenListAllThenReturnAListOfProducts() {
        when(productService.listAll()).thenReturn(List.of(product));

        ResponseEntity<List<ReturnProductDTO>> response = productController.listAll();

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ReturnProductDTO.class, response.getBody().get(Constants.INDEX).getClass());
    }

    @Test
    void whenFindBySkuWithDetailsShouldReturnADetailedProduct() {

        when(productService.findBySkuWithDetails(Constants.SKU)).thenReturn(product);

        ResponseEntity<Product> response = productController.findBySkuWithDetails(Constants.SKU);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(Product.class, response.getBody().getClass());
        assertEquals(Constants.SKU, response.getBody().getSku());
        assertEquals("Calça Teste", response.getBody().getDescription());
        assertEquals(BigDecimal.valueOf(10), response.getBody().getBuyPrice());
        assertEquals(BigDecimal.valueOf(30), response.getBody().getSellPrice());
        assertEquals("Kosair", response.getBody().getBrand());
        assertEquals("Tamanho RN", response.getBody().getSize());
        assertEquals("Masculino", response.getBody().getCategory());
        assertEquals("Verão", response.getBody().getSeason());
        assertEquals("Vestuário", response.getBody().getDepartment());
        assertEquals("Calça", response.getBody().getType());
        assertEquals("Vermelho", response.getBody().getColor());
    }

    private void startProduct () {
        product = new Product(Constants.SKU, "Calça Teste", BigDecimal.valueOf(10), BigDecimal.valueOf(30),
                "Kosair", "Tamanho RN", "Masculino", "Verão", "Vestuário", "Calça", "Vermelho");
    }
}