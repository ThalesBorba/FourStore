package br.com.fourcamp.fourstore.fourstore.controller;

import br.com.fourcamp.fourstore.fourstore.dto.request.CreateStockDTO;
import br.com.fourcamp.fourstore.fourstore.dto.response.MessageResponseDTO;
import br.com.fourcamp.fourstore.fourstore.dto.response.ReturnStockDTO;
import br.com.fourcamp.fourstore.fourstore.entities.Stock;
import br.com.fourcamp.fourstore.fourstore.exceptions.InvalidParametersException;
import br.com.fourcamp.fourstore.fourstore.exceptions.InvalidSellValueException;
import br.com.fourcamp.fourstore.fourstore.exceptions.InvalidSkuException;
import br.com.fourcamp.fourstore.fourstore.exceptions.StockNotFoundException;
import br.com.fourcamp.fourstore.fourstore.service.StockService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.function.Function;

@RestController
@RequestMapping("/stock")
@CrossOrigin(origins = "*", maxAge = 3600)
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
        return stockService.listAll().stream().map(toDTO()).toList();
    }

    @GetMapping("/{sku}")
    public ReturnStockDTO findBySku(@PathVariable String sku) {
        var returnStockDTO = new ReturnStockDTO();
        BeanUtils.copyProperties(stockService.findBySku(sku), returnStockDTO);
        returnStockDTO.setProductDescription(stockService.findBySku(sku).getProduct().getDescription());
        return returnStockDTO;
    }

    @PatchMapping("/{sku}")
    public MessageResponseDTO updateProductPrice(@PathVariable String sku, @PathVariable Double buyPrice,
               @PathVariable Double sellPrice) {
        return stockService.updateProductPrice(sku, buyPrice, sellPrice);
    }

    @DeleteMapping("/{sku}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public MessageResponseDTO deleteBySku(@PathVariable String sku) throws StockNotFoundException {
       return stockService.delete(sku);
    }

    private Function<Stock, ReturnStockDTO> toDTO() {
        return stock -> {
            var returnStockDTO = new ReturnStockDTO();
            BeanUtils.copyProperties(stock, returnStockDTO);
            returnStockDTO.setProductDescription(stock.getProduct().getDescription());
            return returnStockDTO;
        };
    }


}
