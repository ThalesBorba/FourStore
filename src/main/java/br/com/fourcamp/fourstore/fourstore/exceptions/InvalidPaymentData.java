package br.com.fourcamp.fourstore.fourstore.exceptions;

import java.io.Serial;

public class InvalidPaymentData extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidPaymentData() {super("Dados de cartão ou Pix inválidos!");}
}
