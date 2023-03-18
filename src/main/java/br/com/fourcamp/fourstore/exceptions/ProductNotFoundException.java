package br.com.fourcamp.fourstore.exceptions;

public class ProductNotFoundException extends RuntimeException {

	public ProductNotFoundException(String sku) {
        super("Nenhum produto encontrado com a Sku " + sku);
    }

}
