package br.com.fourcamp.fourstore.exceptions;

public class InvalidPaymentDataException extends RuntimeException {

    public InvalidPaymentDataException() {super("Dados de cartão ou Pix inválidos!");}
}
