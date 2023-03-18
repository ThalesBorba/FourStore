package br.com.fourcamp.fourstore.exceptions;

public class ProductAlreadyInStockException extends RuntimeException{

    public ProductAlreadyInStockException() {super("Produto já cadastrado no estoque!");}
}
