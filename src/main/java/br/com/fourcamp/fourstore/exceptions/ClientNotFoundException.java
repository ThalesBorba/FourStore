package br.com.fourcamp.fourstore.exceptions;

public class ClientNotFoundException extends RuntimeException {

    public ClientNotFoundException(String cpf) {
        super("Nenhum cliente encontrado com o cpf " + cpf);
    }
}
