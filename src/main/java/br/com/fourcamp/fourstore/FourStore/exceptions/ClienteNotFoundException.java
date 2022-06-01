package br.com.fourcamp.fourstore.FourStore.exceptions;

public class ClienteNotFoundException extends Exception {
    public ClienteNotFoundException(Long id) {
        super("Nenhum cliente encontrado com a id " + id);
    }
}
