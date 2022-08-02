package br.com.fourcamp.fourstore.fourstore.controller;

import br.com.fourcamp.fourstore.fourstore.dto.request.CreateProductDTO;
import br.com.fourcamp.fourstore.fourstore.dto.request.CreateStockDTO;
import br.com.fourcamp.fourstore.fourstore.dto.response.MessageResponseDTO;
import br.com.fourcamp.fourstore.fourstore.dto.response.ReturnStockDTO;
import br.com.fourcamp.fourstore.fourstore.entities.Product;
import br.com.fourcamp.fourstore.fourstore.entities.Stock;
import br.com.fourcamp.fourstore.fourstore.service.StockService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<String> createStock(@PathVariable Integer quantity, @RequestBody CreateProductDTO createProductDTO) {
        Product product = new Product();
        BeanUtils.copyProperties(createProductDTO, product);
        CreateStockDTO createStockDTO = new CreateStockDTO();
        createStockDTO.setProduct(product);
        createStockDTO.setQuantity(quantity);
        stockService.createStock(createStockDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body("Estoque criado");
    }

    @GetMapping
    public List<ReturnStockDTO> listAll() {
        return stockService.listAll().stream().map(toDTO()).toList();
    }

    @GetMapping("/{sku}")
    public ResponseEntity<ReturnStockDTO> findBySku(@PathVariable String sku) {
        var returnStockDTO = new ReturnStockDTO();
        Stock stock = stockService.findBySku(sku);
        BeanUtils.copyProperties(stock, returnStockDTO);
        returnStockDTO.setProductDescription(stock.getProduct().getDescription());
        returnStockDTO.setSku(stock.getProduct().getSku());
        return ResponseEntity.status(HttpStatus.OK).body(returnStockDTO);
    }

    @PatchMapping("/{sku}/{buyPrice}/{sellPrice}")
    public ResponseEntity<String> updateProductPrice(@PathVariable String sku, @PathVariable Double buyPrice,
                                                     @PathVariable Double sellPrice) {
        stockService.updateProductPrice(sku, buyPrice, sellPrice);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Estoque atualizado");
    }

    @PatchMapping("/{sku}/{quantityToAddToStock}")
    public ResponseEntity<String> addProductsToStock(@PathVariable String sku, @PathVariable Integer quantityToAddToStock) {
        stockService.addProductsToStock(sku, quantityToAddToStock);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Estoque atualizado");
    }

    @DeleteMapping("/{sku}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<String> deleteBySku(@PathVariable String sku) {
        stockService.delete(sku);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Estoque removido");
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
