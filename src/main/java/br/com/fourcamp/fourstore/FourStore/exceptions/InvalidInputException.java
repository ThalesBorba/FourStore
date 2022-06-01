package br.com.fourcamp.fourstore.FourStore.exceptions;

public class InvalidInputException extends Exception {

	private static final long serialVersionUID = 1L;

    public InvalidInputException() {
        System.err.println("Op��o Inv�lida!\n");
	}
    
    public InvalidInputException(Integer id) {
        System.err.println("Op��o Inv�lida!\n");
	}

    public InvalidInputException(String option) {
        System.err.println("Op��o Inv�lida!\n");
    }
}
