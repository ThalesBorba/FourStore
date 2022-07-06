package br.com.fourcamp.fourstore.fourstore.controller;

import br.com.fourcamp.fourstore.fourstore.dto.request.CreateStockDTO;
import br.com.fourcamp.fourstore.fourstore.dto.response.ReturnStockDTO;
import br.com.fourcamp.fourstore.fourstore.exceptions.*;
import br.com.fourcamp.fourstore.fourstore.service.StockService;
import br.com.fourcamp.fourstore.fourstore.util.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/")
    public ResponseEntity<Object> createStock(@RequestBody @Valid CreateStockDTO createStockDTO) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(stockService.createStock(createStockDTO));
        } catch (InvalidSellValueException | InvalidSkuException | InvalidParametersException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ResponseModel(HttpStatus.NOT_ACCEPTABLE,
                    HttpStatus.NOT_ACCEPTABLE.value(), e.getMessage()));
        }
    }

    @GetMapping
    public List<ReturnStockDTO> listAll() {
        return stockService.listAll();
    }

    @GetMapping("/{sku}")
    public ResponseEntity<Object> findBySku(@PathVariable String sku) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(stockService.findBySku(sku));
        } catch (StockNotFoundException | ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseModel(HttpStatus.NOT_FOUND,
                    HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }

    @PutMapping("/{sku}")
    public ResponseEntity<Object> updateBySku(@PathVariable String sku, @RequestBody @Valid CreateStockDTO createStockDTO) {
        try {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(stockService.updateBySku(sku, createStockDTO));
        } catch (StockNotFoundException | ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseModel(HttpStatus.NOT_FOUND,
                    HttpStatus.NOT_FOUND.value(), e.getMessage()));
        } catch (InvalidSellValueException | InvalidSkuException e) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(new ResponseModel(HttpStatus.NOT_ACCEPTABLE,
                    HttpStatus.NOT_ACCEPTABLE.value(), e.getMessage()));
        }
    }
    //todo dto para mudar apenas quantidade, avisar que é soma

    @DeleteMapping("/{sku}")
    public ResponseEntity<Object> deleteBySku(@PathVariable String sku) {
        try {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(stockService.delete(sku));
        } catch (StockNotFoundException | ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseModel(HttpStatus.NOT_FOUND,
                    HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }
}
