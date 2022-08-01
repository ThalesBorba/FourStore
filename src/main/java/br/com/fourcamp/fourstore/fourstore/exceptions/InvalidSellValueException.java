package br.com.fourcamp.fourstore.fourstore.exceptions;

import java.io.Serial;

public class InvalidSellValueException extends RuntimeException {

	public InvalidSellValueException() {
		super("Lucro menor que 25%. Operação inválida!");
	
}
}
