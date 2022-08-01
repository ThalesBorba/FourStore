package br.com.fourcamp.fourstore.fourstore.exceptions;

import java.io.Serial;

public class ProductAlreadyInStockException extends RuntimeException{

    public ProductAlreadyInStockException() {super("Produto já cadastrado no estoque!");}
}
