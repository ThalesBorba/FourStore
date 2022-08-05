package br.com.fourcamp.fourstore.fourstore.controller;

import br.com.fourcamp.fourstore.fourstore.dto.response.ReturnProductDTO;
import br.com.fourcamp.fourstore.fourstore.entities.Product;
import br.com.fourcamp.fourstore.fourstore.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
    public static final String SKU = "KSR1010405023150";
    public static final Integer INDEX = 0;

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
        assertEquals(ReturnProductDTO.class, response.getBody().get(INDEX).getClass());
    }

    @Test
    void whenFindBySkuWithDetailsShouldReturnADetailedProduct() {

        when(productService.findBySkuWithDetails(SKU)).thenReturn(product);

        ResponseEntity<Product> response = productController.findBySkuWithDetails(SKU);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(Product.class, response.getBody().getClass());
        assertEquals(SKU, response.getBody().getSku());
        assertEquals("Calça Teste", response.getBody().getDescription());
        assertEquals(10.0, response.getBody().getBuyPrice());
        assertEquals(30.0, response.getBody().getSellPrice());
        assertEquals("Kosair", response.getBody().getBrand());
        assertEquals("Tamanho RN", response.getBody().getSize());
        assertEquals("Masculino", response.getBody().getCategory());
        assertEquals("Verão", response.getBody().getSeason());
        assertEquals("Vestuário", response.getBody().getDepartment());
        assertEquals("Calça", response.getBody().getType());
        assertEquals("Vermelho", response.getBody().getColor());
    }

    private void startProduct () {
        product = new Product(SKU, "Calça Teste", 10.0, 30.0,
                "Kosair", "Tamanho RN", "Masculino", "Verão", "Vestuário", "Calça", "Vermelho");
    }
}