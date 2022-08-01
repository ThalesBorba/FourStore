package br.com.fourcamp.fourstore.fourstore.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.ZoneId;

@ControllerAdvice
public class ResourceExceptionHandler{

    @ExceptionHandler({ClientNotFoundException.class, ProductNotFoundException.class, StockNotFoundException.class,
            TransactionNotFoundException.class})
    public ResponseEntity<StandardError> objectNotFound(Exception ex , HttpServletRequest request) {
        StandardError error =
                new StandardError(LocalDateTime.now(ZoneId.of("UTC")), HttpStatus.NOT_FOUND.value(),
                        ex.getLocalizedMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler({InvalidParametersException.class, InvalidPaymentDataException.class, InvalidSellValueException.class,
    InvalidSkuException.class, StockInsufficientException.class})
    public ResponseEntity<StandardError> invalidParameters(Exception ex, HttpServletRequest request) {
        StandardError error =
                new StandardError(LocalDateTime.now(ZoneId.of("UTC")), HttpStatus.BAD_REQUEST.value(),
                        ex.getLocalizedMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(ProductAlreadyInStockException.class)
    public ResponseEntity<StandardError> objectAlreadyPresent(Exception ex, HttpServletRequest request) {
        StandardError error =
                new StandardError(LocalDateTime.now(ZoneId.of("UTC")), HttpStatus.CONFLICT.value(),
                        ex.getLocalizedMessage(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

}
