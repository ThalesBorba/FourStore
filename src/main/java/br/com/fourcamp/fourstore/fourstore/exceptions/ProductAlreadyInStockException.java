package br.com.fourcamp.fourstore.fourstore.exceptions;

public class ProductAlreadyInStockException extends RuntimeException{

    public ProductAlreadyInStockException() {super("Produto já cadastrado no estoque!");}
}
