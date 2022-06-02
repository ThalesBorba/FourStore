package br.com.fourcamp.fourstore.FourStore.exceptions;

import java.io.Serial;

public class ProductNotFoundException extends Exception {

	@Serial
    private static final long serialVersionUID = 1L;

	public ProductNotFoundException(String sku) {
        super("Nenhum produto encontrado com a Sku " + sku);
    }

}
