package br.com.fourcamp.fourstore.FourStore.controller;

import br.com.fourcamp.fourstore.FourStore.dto.request.CreateStockDTO;
import br.com.fourcamp.fourstore.FourStore.dto.response.MessageResponseDTO;
import br.com.fourcamp.fourstore.FourStore.dto.response.ReturnStockDTO;
import br.com.fourcamp.fourstore.FourStore.exceptions.*;
import br.com.fourcamp.fourstore.FourStore.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponseDTO createStock(@RequestBody @Valid CreateStockDTO createStockDTO) throws
            InvalidParametersException, InvalidSellValueException, InvalidSkuException {
        return stockService.createStock(createStockDTO);
    }

    @GetMapping
    public List<ReturnStockDTO> listAll() {
        return stockService.listAll();
    }

    @GetMapping("/{sku}")
    public ReturnStockDTO findBySku(@PathVariable String sku) throws StockNotFoundException {
        return stockService.findBySku(sku);
    }

    @PutMapping("/{sku}")
    public MessageResponseDTO updateBySku(@PathVariable String sku, @RequestBody @Valid CreateStockDTO createStockDTO)
            throws StockNotFoundException, InvalidSellValueException, InvalidSkuException {
        return stockService.updateBySku(sku, createStockDTO);
    }
    //todo dto para mudar apenas quantidade, avisar que é soma

    @DeleteMapping("/{sku}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public MessageResponseDTO deleteBySku(@PathVariable String sku) throws StockNotFoundException {
       return stockService.delete(sku);
    }
}
