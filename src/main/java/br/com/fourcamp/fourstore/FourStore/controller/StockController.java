package br.com.fourcamp.fourstore.FourStore.controller;

import br.com.fourcamp.fourstore.FourStore.dto.response.MessageResponseDTO;
import br.com.fourcamp.fourstore.FourStore.entities.Stock;
import br.com.fourcamp.fourstore.FourStore.exceptions.ProductNotFoundException;
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
    public MessageResponseDTO createStock(@RequestBody @Valid Stock stock) {
        return stockService.createStock(stock);
    }

    @GetMapping
    public List<Stock> listAll() {
        return stockService.listAll();
    }

    @GetMapping("/{id}")
    public Stock findById(@PathVariable Long id) throws StockNotFoundException {
        return stockService.findById(id);
    }

    @PutMapping("/{id}")
    public MessageResponseDTO updateById(@PathVariable Long id, @RequestBody @Valid Stock stock)
            throws StockNotFoundException {
        return stockService.updateById(id, stock);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) throws StockNotFoundException {
        stockService.delete(id);
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
