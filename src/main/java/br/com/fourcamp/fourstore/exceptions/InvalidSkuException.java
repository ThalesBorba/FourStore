package br.com.fourcamp.fourstore.exceptions;

public class InvalidSkuException extends RuntimeException {

    public InvalidSkuException() { super("Sku inválida!"); }

}
