package br.com.fourcamp.fourstore.fourstore.exceptions;

public class TransactionNotFoundException extends RuntimeException {

    public TransactionNotFoundException(Long id)  {
        super("Nenhuma transação encontrada com a ID " + id);
    }

}
