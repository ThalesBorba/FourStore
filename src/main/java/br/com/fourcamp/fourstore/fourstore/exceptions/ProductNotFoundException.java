package br.com.fourcamp.fourstore.fourstore.exceptions;

import java.io.Serial;

public class ProductNotFoundException extends RuntimeException {

	public ProductNotFoundException(String sku) {
        super("Nenhum produto encontrado com a Sku " + sku);
    }

}
