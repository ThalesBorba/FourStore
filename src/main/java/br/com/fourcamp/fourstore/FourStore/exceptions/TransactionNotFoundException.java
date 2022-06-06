package br.com.fourcamp.fourstore.FourStore.exceptions;

import java.io.Serial;

public class TransactionNotFoundException extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public TransactionNotFoundException(Long id)  {
        super("Nenhuma transação encontrada com a ID " + id);
    }

}
