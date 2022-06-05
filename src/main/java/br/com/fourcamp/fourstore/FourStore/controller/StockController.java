package br.com.fourcamp.fourstore.FourStore.controller;

import br.com.fourcamp.fourstore.FourStore.dto.response.MessageResponseDTO;
import br.com.fourcamp.fourstore.FourStore.entities.Stock;
import br.com.fourcamp.fourstore.FourStore.exceptions.InvalidParametersException;
import br.com.fourcamp.fourstore.FourStore.exceptions.ProductNotFoundException;
import br.com.fourcamp.fourstore.FourStore.exceptions.StockInsufficientException;
import br.com.fourcamp.fourstore.FourStore.exceptions.StockNotFoundException;
import br.com.fourcamp.fourstore.FourStore.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/stock")
public class StockController {

    @Autowired
    private final StockService stockService;

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponseDTO createStock(@RequestBody @Valid Stock stock) throws InvalidParametersException {
        return stockService.createStock(stock);
    }

    @GetMapping
    public List<Stock> listAll() {
        return stockService.listAll();
    }

    @GetMapping("/{id}")
    public Stock findById(@PathVariable String sku) throws StockNotFoundException {
        return stockService.findById(sku);
    }

    @PutMapping("/{id}")
    public MessageResponseDTO updateById(@PathVariable String sku, @RequestBody @Valid Stock stock)
            throws StockNotFoundException, StockInsufficientException {
        return stockService.updateById(sku, stock);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable String sku) throws StockNotFoundException {
        stockService.delete(sku);
    }

/*
    Métodos JavaFx
    public void createStock() {

    }

    public void findBySku() {

    }

    public void listStock() {

    }

    public void updateStock() {

    }

    public void returnToMainMenu() {

    }

    */


}
