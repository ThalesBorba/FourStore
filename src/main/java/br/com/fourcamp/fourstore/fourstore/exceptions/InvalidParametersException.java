package br.com.fourcamp.fourstore.fourstore.exceptions;

import java.io.Serial;

public class InvalidParametersException extends RuntimeException {

    public InvalidParametersException() {super("Um ou mais parâmetros informados são inválidos");}

}
