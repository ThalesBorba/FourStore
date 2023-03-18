package br.com.fourcamp.fourstore.exceptions;

import java.util.UUID;

public class TransactionNotFoundException extends RuntimeException {

    public TransactionNotFoundException(UUID id)  {
        super("Nenhuma transação encontrada com a ID " + id);
    }

}
