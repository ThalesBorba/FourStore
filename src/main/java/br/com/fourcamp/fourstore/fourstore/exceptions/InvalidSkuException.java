package br.com.fourcamp.fourstore.fourstore.exceptions;

import java.io.Serial;

public class InvalidSkuException extends RuntimeException {

    public InvalidSkuException() { super("Sku inválida!"); }

}
