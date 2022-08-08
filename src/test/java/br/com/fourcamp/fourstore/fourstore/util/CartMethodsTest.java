package br.com.fourcamp.fourstore.fourstore.util;

import br.com.fourcamp.fourstore.fourstore.dto.request.CreateTransactionDTO;
import br.com.fourcamp.fourstore.fourstore.entities.Product;
import br.com.fourcamp.fourstore.fourstore.entities.Stock;
import br.com.fourcamp.fourstore.fourstore.enums.PaymentMethodEnum;
import br.com.fourcamp.fourstore.fourstore.exceptions.StockInsufficientException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static br.com.fourcamp.fourstore.fourstore.constants.Constants.*;
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
        assertEquals(INTEGER_ID, response.get(INDEX).getId());
        assertEquals(product, response.get(INDEX).getProduct());
        assertEquals(40, response.get(INDEX).getQuantity());
        assertEquals(SKU, response.get(INDEX).getProduct().getSku());
        assertEquals("Calça Teste", response.get(INDEX).getProduct().getDescription());
        assertEquals(10.0, response.get(INDEX).getProduct().getBuyPrice());
        assertEquals(30.0, response.get(INDEX).getProduct().getSellPrice());
        assertEquals("Kosair", response.get(INDEX).getProduct().getBrand());
        assertEquals("Tamanho RN", response.get(INDEX).getProduct().getSize());
        assertEquals("Masculino", response.get(INDEX).getProduct().getCategory());
        assertEquals("Verão", response.get(INDEX).getProduct().getSeason());
        assertEquals("Vestuário", response.get(INDEX).getProduct().getDepartment());
        assertEquals("Calça", response.get(INDEX).getProduct().getType());
        assertEquals("Vermelho", response.get(INDEX).getProduct().getColor());
    }

    @Test
    void shouldReturnTheProfitForSellingAllProductsOnTheCartTakingIntoAccountTheDiscountFromPaymentMethod() {
        Double billetResponse = CartMethods.returnProfit(productMap, 1);
        Double billetInstallmentResponse = CartMethods.returnProfit(productMap, 2);
        Double creditCardResponse = CartMethods.returnProfit(productMap, 3);
        Double debitCardResponse = CartMethods.returnProfit(productMap, 4);
        Double pixResponse = CartMethods.returnProfit(productMap, 5);
        Double cashResponse = CartMethods.returnProfit(productMap, 6);

        assertNotNull(billetResponse);
        assertNotNull(billetInstallmentResponse);
        assertNotNull(creditCardResponse);
        assertNotNull(debitCardResponse);
        assertNotNull(pixResponse);
        assertNotNull(cashResponse);
        assertEquals(185.0, billetResponse);
        assertEquals(200.0, billetInstallmentResponse);
        assertEquals(200.0, creditCardResponse);
        assertEquals(185.0, debitCardResponse);
        assertEquals(170.0, pixResponse);
        assertEquals(170.0, cashResponse);
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
        product = new Product(SKU, "Calça Teste", 10.0, 30.0, "Kosair",
                "Tamanho RN", "Masculino", "Verão", "Vestuário", "Calça", "Vermelho");
        stock = new Stock(INTEGER_ID, product, 50);
    }

    private void startProductMap () {
        productMap.put(product, 10);
    }

}