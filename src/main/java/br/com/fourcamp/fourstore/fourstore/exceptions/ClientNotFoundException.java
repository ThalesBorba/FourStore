package br.com.fourcamp.fourstore.fourstore.exceptions;

import java.io.Serial;

public class ClientNotFoundException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public ClientNotFoundException(String cpf) {
        super("Nenhum cliente encontrado com o cpf " + cpf);
    }
}
