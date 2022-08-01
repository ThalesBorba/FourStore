package br.com.fourcamp.fourstore.fourstore.exceptions;

public class InvalidSellValueException extends RuntimeException {

	public InvalidSellValueException() {
		super("Lucro menor que 25%. Operação inválida!");
	
}
}
