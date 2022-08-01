package br.com.fourcamp.fourstore.fourstore.exceptions;

public class StockNotFoundException extends RuntimeException {

    public StockNotFoundException(String sku) {
        super("Produto com a sku " + sku + " não existe no estoque!");
    }

}
