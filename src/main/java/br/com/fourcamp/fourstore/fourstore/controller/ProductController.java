package br.com.fourcamp.fourstore.fourstore.controller;

import br.com.fourcamp.fourstore.fourstore.dto.response.ReturnProductDTO;
import br.com.fourcamp.fourstore.fourstore.entities.Product;
import br.com.fourcamp.fourstore.fourstore.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Function;

@RestController
@RequestMapping("/product")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<ReturnProductDTO>> listAll() {
        return ResponseEntity.status(HttpStatus.OK).body(productService.listAll().stream().map(toDTO()).toList());
    }

    @GetMapping("/{sku}")
    public ResponseEntity<Product> findBySkuWithDetails(@PathVariable String sku) {
        return ResponseEntity.status(HttpStatus.OK).body(productService.findBySkuWithDetails(sku));
    }

    private Function<Product, ReturnProductDTO> toDTO() {
        return product -> {
            var returnProductDTO = new ReturnProductDTO();
            BeanUtils.copyProperties(product, returnProductDTO);
            return returnProductDTO;
        };
    }

}
