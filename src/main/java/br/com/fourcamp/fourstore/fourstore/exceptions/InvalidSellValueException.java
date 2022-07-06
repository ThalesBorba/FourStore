package br.com.fourcamp.fourstore.fourstore.exceptions;

import java.io.Serial;

public class InvalidSellValueException extends Exception {

	@Serial
	private static final long serialVersionUID = 1L;

	public InvalidSellValueException() {
		super("Lucro menor que 25%. Operação inválida!");
	
}
}
