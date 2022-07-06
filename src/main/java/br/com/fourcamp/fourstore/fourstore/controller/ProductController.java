package br.com.fourcamp.fourstore.fourstore.controller;

import br.com.fourcamp.fourstore.fourstore.dto.response.ReturnProductDTO;
import br.com.fourcamp.fourstore.fourstore.exceptions.ProductNotFoundException;
import br.com.fourcamp.fourstore.fourstore.service.ProductService;
import br.com.fourcamp.fourstore.fourstore.util.ResponseModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ReturnProductDTO> listAll() {
        return productService.listAll();
    }

    @GetMapping("/{sku}")
    public ResponseEntity<Object> findBySkuWithDetails(@PathVariable String sku) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(productService.findBySkuWithDetails(sku));
        } catch (ProductNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseModel(HttpStatus.NOT_FOUND,
                    HttpStatus.NOT_FOUND.value(), e.getMessage()));
        }
    }

}