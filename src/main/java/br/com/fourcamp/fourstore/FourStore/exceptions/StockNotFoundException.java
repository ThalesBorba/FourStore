package br.com.fourcamp.fourstore.FourStore.exceptions;

import java.io.Serial;

public class StockNotFoundException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public StockNotFoundException(String sku) {
        super("Produto com a sku " + sku + " não existe no estoque!");
    }

}
