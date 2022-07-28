package br.com.fourcamp.fourstore.fourstore.controller;

import br.com.fourcamp.fourstore.fourstore.dto.response.ReturnProductDTO;
import br.com.fourcamp.fourstore.fourstore.dto.response.ReturnProductDetailsDTO;
import br.com.fourcamp.fourstore.fourstore.exceptions.ProductNotFoundException;
import br.com.fourcamp.fourstore.fourstore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public ReturnProductDetailsDTO findBySkuWithDetails(@PathVariable String sku) throws ProductNotFoundException {
        return productService.findBySkuWithDetails(sku);
    }

}
