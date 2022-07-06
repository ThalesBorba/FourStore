package br.com.fourcamp.fourstore.fourstore.exceptions;

import java.io.Serial;

public class InvalidParametersException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidParametersException() {super("Um ou mais parâmetros informados são inválidos");}

}
