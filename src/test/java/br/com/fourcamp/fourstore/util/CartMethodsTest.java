package br.com.fourcamp.fourstore.util;

import br.com.fourcamp.fourstore.dto.request.CreateTransactionDTO;
import br.com.fourcamp.fourstore.entities.Product;
import br.com.fourcamp.fourstore.entities.Stock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static br.com.fourcamp.fourstore.constants.Constants.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CartMethodsTest {

    private CreateTransactionDTO createTransactionDTO;
    private Stock stock;
    private Product product;
    private final HashMap<String, Integer> skuMap = new HashMap<>();
    private final HashMap<Product, Integer> productMap = new HashMap<>();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startCreateTransactionDTO();
        startStock();
        startProductMap();
    }

    @Test
    void shouldDecreaseProductQuantityOnStock() {
        List<Stock> response = CartMethods.updateStock(new ArrayList<>(List.of(stock)), createTransactionDTO);

        assertNotNull(response);
        assertEquals(ArrayList.class, response.getClass());
        assertEquals(UUID_ID, response.get(INDEX).getId());
        assertEquals(product, response.get(INDEX).getProduct());
        assertEquals(40, response.get(INDEX).getQuantity());
        assertEquals(SKU, response.get(INDEX).getProduct().getSku());
        assertEquals("Cal�a Teste", response.get(INDEX).getProduct().getDescription());
        assertEquals(BigDecimal.valueOf(10), response.get(INDEX).getProduct().getBuyPrice());
        assertEquals(BigDecimal.valueOf(30), response.get(INDEX).getProduct().getSellPrice());
        assertEquals("Kosair", response.get(INDEX).getProduct().getBrand());
        assertEquals("Tamanho RN", response.get(INDEX).getProduct().getSize());
        assertEquals("Masculino", response.get(INDEX).getProduct().getCategory());
        assertEquals("Ver�o", response.get(INDEX).getProduct().getSeason());
        assertEquals("Vestu�rio", response.get(INDEX).getProduct().getDepartment());
        assertEquals("Cal�a", response.get(INDEX).getProduct().getType());
        assertEquals("Vermelho", response.get(INDEX).getProduct().getColor());
    }

    @Test
    void shouldReturnTheProfitForSellingAllProductsOnTheCartTakingIntoAccountTheDiscountFromPaymentMethod() {
        BigDecimal billetResponse = CartMethods.returnProfit(productMap, 1);
        BigDecimal billetInstallmentResponse = CartMethods.returnProfit(productMap, 2);
        BigDecimal creditCardResponse = CartMethods.returnProfit(productMap, 3);
        BigDecimal debitCardResponse = CartMethods.returnProfit(productMap, 4);
        BigDecimal pixResponse = CartMethods.returnProfit(productMap, 5);
        BigDecimal cashResponse = CartMethods.returnProfit(productMap, 6);

        assertNotNull(billetResponse);
        assertNotNull(billetInstallmentResponse);
        assertNotNull(creditCardResponse);
        assertNotNull(debitCardResponse);
        assertNotNull(pixResponse);
        assertNotNull(cashResponse);
        assertEquals(BigDecimal.valueOf(185).setScale(2, RoundingMode.HALF_EVEN), billetResponse);
        assertEquals(BigDecimal.valueOf(200).setScale(2, RoundingMode.HALF_EVEN), billetInstallmentResponse);
        assertEquals(BigDecimal.valueOf(200).setScale(2, RoundingMode.HALF_EVEN), creditCardResponse);
        assertEquals(BigDecimal.valueOf(185).setScale(2, RoundingMode.HALF_EVEN), debitCardResponse);
        assertEquals(BigDecimal.valueOf(170).setScale(2, RoundingMode.HALF_EVEN), pixResponse);
        assertEquals(BigDecimal.valueOf(170).setScale(2, RoundingMode.HALF_EVEN), cashResponse);
    }

    @Test
    void shouldReturnNullPointerExceptionWithWrongPaymentMethod () {
        assertThrows(NullPointerException.class, () -> CartMethods.returnProfit(productMap, 7));
    }

    @Test
    void testConstructorIsPrivate() throws NoSuchMethodException {
        Constructor<CartMethods> constructor = CartMethods.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()));
    }


    private void startCreateTransactionDTO () {
        skuMap.put(SKU, 10);
        createTransactionDTO = new CreateTransactionDTO(CPF, skuMap);
    }

    private void startStock () {
        product = new Product(SKU, "Cal�a Teste", BigDecimal.valueOf(10), BigDecimal.valueOf(30), "Kosair",
                "Tamanho RN", "Masculino", "Ver�o", "Vestu�rio", "Cal�a", "Vermelho");
        stock = new Stock(UUID_ID, product, 50);
    }

    private void startProductMap () {
        productMap.put(product, 10);
    }

}