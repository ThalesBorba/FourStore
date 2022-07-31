package br.com.fourcamp.fourstore.fourstore.controller;

import br.com.fourcamp.fourstore.fourstore.dto.request.CreateProductDTO;
import br.com.fourcamp.fourstore.fourstore.dto.request.CreateStockDTO;
import br.com.fourcamp.fourstore.fourstore.dto.response.MessageResponseDTO;
import br.com.fourcamp.fourstore.fourstore.dto.response.ReturnStockDTO;
import br.com.fourcamp.fourstore.fourstore.entities.Product;
import br.com.fourcamp.fourstore.fourstore.entities.Stock;
import br.com.fourcamp.fourstore.fourstore.exceptions.*;
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
    private StockService stockService;

    @PostMapping("/{quantity}")
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponseDTO createStock(@PathVariable Integer quantity, @RequestBody CreateProductDTO createProductDTO) throws
            InvalidParametersException, InvalidSellValueException, InvalidSkuException, ProductAlreadyInStockException {
        Product product = new Product();
        BeanUtils.copyProperties(createProductDTO, product);
        CreateStockDTO createStockDTO = new CreateStockDTO();
        createStockDTO.setProduct(product);
        createStockDTO.setQuantity(quantity);
        return stockService.createStock(createStockDTO);
    }

    @GetMapping
    public List<ReturnStockDTO> listAll() {
        return stockService.listAll().stream().map(toDTO()).toList();
    }

    @GetMapping("/{sku}")
    public ReturnStockDTO findBySku(@PathVariable String sku) {
        var returnStockDTO = new ReturnStockDTO();
        Stock stock = stockService.findBySku(sku);
        BeanUtils.copyProperties(stock, returnStockDTO);
        returnStockDTO.setProductDescription(stock.getProduct().getDescription());
        returnStockDTO.setSku(stock.getProduct().getSku());
        return returnStockDTO;
    }

    @PatchMapping("/{sku}/{buyPrice}/{sellPrice}")
    public MessageResponseDTO updateProductPrice(@PathVariable String sku, @PathVariable Double buyPrice,
               @PathVariable Double sellPrice) {
        return stockService.updateProductPrice(sku, buyPrice, sellPrice);
    }

    @PatchMapping("/{sku}/{quantityToAddToStock}")
    public MessageResponseDTO addProductsToStock(@PathVariable String sku, @PathVariable Integer quantityToAddToStock) {
        return stockService.addProductsToStock(sku, quantityToAddToStock);
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
            returnStockDTO.setSku(stock.getProduct().getSku());
            return returnStockDTO;
        };
    }


}
