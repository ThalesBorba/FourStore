package br.com.fourcamp.fourstore.exceptions;

public class StockInsufficientException extends RuntimeException {

	public StockInsufficientException() {
        super("Estoque do produto é insuficiente para realizar a operação!");
    }
	
}
