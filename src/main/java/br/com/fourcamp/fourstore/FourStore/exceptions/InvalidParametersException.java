package br.com.fourcamp.fourstore.FourStore.exceptions;

import java.io.Serial;

public class InvalidParametersException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public InvalidParametersException() {super("Um ou mais parametros informados são inválidos");}

}
