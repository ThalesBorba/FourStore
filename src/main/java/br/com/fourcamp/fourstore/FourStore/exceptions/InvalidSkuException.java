package br.com.fourcamp.fourstore.FourStore.exceptions;

import java.io.Serial;

public class InvalidSkuException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidSkuException() { super("Sku inválida!"); }

}
