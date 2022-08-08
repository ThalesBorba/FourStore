package br.com.fourcamp.fourstore.fourstore.util;

import br.com.fourcamp.fourstore.fourstore.constants.Constants;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static br.com.fourcamp.fourstore.fourstore.constants.Constants.CPF;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ClientValidationsTest {

    @Test
    void shouldValidateCards() {
        Boolean trueCard = ClientValidations.validateCards(Constants.CARD);
        Boolean falseCard = ClientValidations.validateCards(Constants.FALSE_STUB);

        assertNotNull(trueCard);
        assertNotNull(falseCard);
        assertTrue(trueCard);
        assertFalse(falseCard);
    }

    @Test
    void shouldValidatePixes() {
        Boolean trueCellPhonePix = ClientValidations.validatePix("+5521987654321");
        Boolean trueCpfPix = ClientValidations.validatePix(CPF);
        Boolean trueCnpjPix = ClientValidations.validatePix("33.113.309/0001-47");
        Boolean trueEmailPix = ClientValidations.validatePix("jose@gmail.com");
        Boolean falsePix = ClientValidations.validateCpf(Constants.FALSE_STUB);

        assertNotNull(trueCellPhonePix);
        assertNotNull(trueCpfPix);
        assertNotNull(trueCnpjPix);
        assertNotNull(trueEmailPix);
        assertNotNull(falsePix);
        assertTrue(trueCellPhonePix);
        assertTrue(trueCpfPix);
        assertTrue(trueCnpjPix);
        assertTrue(trueEmailPix);
        assertFalse(falsePix);
    }

    @Test
    void shouldValidateCpfs() {
        Boolean trueCpf = ClientValidations.validateCpf(CPF);
        Boolean falseCpf = ClientValidations.validateCpf(Constants.FALSE_STUB);

        assertNotNull(trueCpf);
        assertNotNull(falseCpf);
        assertTrue(trueCpf);
        assertFalse(falseCpf);
    }

    @Test
    void shouldReturnTrueOrFalseCaseThePaymentMethodIsValid () {

        assertTrue(ClientValidations.paymentMethodValidation(1, Constants.FALSE_STUB));
        assertTrue(ClientValidations.paymentMethodValidation(2, Constants.FALSE_STUB));
        assertTrue(ClientValidations.paymentMethodValidation(3, Constants.CARD));
        assertTrue(ClientValidations.paymentMethodValidation(4, Constants.CARD));
        assertTrue(ClientValidations.paymentMethodValidation(5, CPF));
        assertTrue(ClientValidations.paymentMethodValidation(6, Constants.FALSE_STUB));
        assertFalse(ClientValidations.paymentMethodValidation(3, Constants.FALSE_STUB));
        assertFalse(ClientValidations.paymentMethodValidation(4, Constants.FALSE_STUB));
        assertFalse(ClientValidations.paymentMethodValidation(5, Constants.FALSE_STUB));
        assertFalse(ClientValidations.paymentMethodValidation(7, Constants.FALSE_STUB));

    }
}