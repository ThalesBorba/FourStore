package br.com.fourcamp.fourstore.fourstore.exceptions;

import java.io.Serial;

public class ProductAlreadyInStockException extends Exception{

    @Serial
    private static final long serialVersionUID = 1L;

    public ProductAlreadyInStockException() {super("Produto já cadastrado no estoque!");}
}
