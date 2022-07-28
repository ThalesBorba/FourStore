package br.com.fourcamp.fourstore.fourstore.controller;

import br.com.fourcamp.fourstore.fourstore.dto.response.ReturnProductDTO;
import br.com.fourcamp.fourstore.fourstore.entities.Product;
import br.com.fourcamp.fourstore.fourstore.exceptions.ProductNotFoundException;
import br.com.fourcamp.fourstore.fourstore.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Function;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProductController {
    private final ProductService productService;
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public List<ReturnProductDTO> listAll() {
        return productService.listAll().stream().map(toDTO()).toList();
    }

    @GetMapping("/{sku}")
    public Product findBySkuWithDetails(@PathVariable String sku) {
        return productService.findBySkuWithDetails(sku);
    }

    private Function<Product, ReturnProductDTO> toDTO() {
        return product -> {
            var returnProductDTO = new ReturnProductDTO();
            BeanUtils.copyProperties(product, returnProductDTO);
            return returnProductDTO;
        };
    }

}
