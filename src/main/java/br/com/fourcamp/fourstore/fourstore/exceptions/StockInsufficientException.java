package br.com.fourcamp.fourstore.fourstore.exceptions;

import java.io.Serial;

public class StockInsufficientException extends RuntimeException {

	public StockInsufficientException() {
        super("Estoque do produto é insuficiente para realizar a operação!");
    }
	
}
