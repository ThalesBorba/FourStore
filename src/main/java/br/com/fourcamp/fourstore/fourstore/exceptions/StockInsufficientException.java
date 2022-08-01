package br.com.fourcamp.fourstore.fourstore.exceptions;

public class StockInsufficientException extends RuntimeException {

	public StockInsufficientException() {
        super("Estoque do produto é insuficiente para realizar a operação!");
    }
	
}
