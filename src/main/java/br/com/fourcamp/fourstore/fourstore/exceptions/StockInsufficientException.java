package br.com.fourcamp.fourstore.fourstore.exceptions;

import java.io.Serial;

public class StockInsufficientException extends Exception {

	@Serial
    private static final long serialVersionUID = 1L;

	public StockInsufficientException() {
        super("Estoque do produto é insuficiente para realizar a operação!");
    }
	
}
