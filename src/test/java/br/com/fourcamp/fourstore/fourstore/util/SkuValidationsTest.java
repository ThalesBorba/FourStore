package br.com.fourcamp.fourstore.fourstore.util;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static br.com.fourcamp.fourstore.fourstore.constants.Constants.SKU;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SkuValidationsTest {

    @Test
    void shouldReturnTrueOrFalseBasedOnTheSkuBeingValid() {
        Boolean trueResponse = SkuValidations.validateSku(SKU);
        Boolean falseResponse = SkuValidations.validateSku("BTRESFDSFDDSKFSO");

        assertNotNull(trueResponse);
        assertNotNull(falseResponse);
        assertEquals(true, trueResponse);
        assertEquals(false, falseResponse);


    }

    @Test
    void shouldThrowNewStringIndexOutOfBoundsException () {
        assertThrows(StringIndexOutOfBoundsException.class, () -> SkuValidations.validateSku("LFMLDK"));
    }
}