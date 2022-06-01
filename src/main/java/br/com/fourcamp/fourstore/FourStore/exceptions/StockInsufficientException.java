package br.com.fourcamp.fourstore.FourStore.exceptions;

public class StockInsufficientException extends Exception {

	private static final long serialVersionUID = 1L;

	public StockInsufficientException() {
        System.err.println("Estoque do produto � insuficiente para realizar a operação!");
    }
	
}
