package br.com.fourcamp.fourstore.FourStore.controller;

import br.com.fourcamp.fourstore.FourStore.dto.request.CreateProductDTO;
import br.com.fourcamp.fourstore.FourStore.dto.response.MessageResponseDTO;
import br.com.fourcamp.fourstore.FourStore.dto.response.ReturnProductDTO;
import br.com.fourcamp.fourstore.FourStore.dto.response.ReturnProductDetailsDTO;
import br.com.fourcamp.fourstore.FourStore.exceptions.InvalidSellValueException;
import br.com.fourcamp.fourstore.FourStore.exceptions.InvalidSkuException;
import br.com.fourcamp.fourstore.FourStore.exceptions.ProductNotFoundException;
import br.com.fourcamp.fourstore.FourStore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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

    @PostMapping("/{id}")
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponseDTO createProduct(@RequestBody @Valid CreateProductDTO createProductDTO)
            throws InvalidSellValueException, InvalidSkuException {
        return productService.createProduct(createProductDTO);
    }

    @GetMapping
    public List<ReturnProductDTO> listAll() {
        return productService.listAll();
    }

    @GetMapping("/{id}")
    public ReturnProductDTO findById(@PathVariable String sku) throws ProductNotFoundException {
        return productService.findById(sku);
    }

    @GetMapping("/{id}/{details}")
    public ReturnProductDetailsDTO findByIdWithDetails(@PathVariable String sku) throws ProductNotFoundException {
        return productService.findByIdWithDetails(sku);
    }

    @PutMapping("/{id}")
    public MessageResponseDTO updateById(@PathVariable String sku, @RequestBody @Valid
                                         CreateProductDTO createProductDTO)
            throws ProductNotFoundException, InvalidSellValueException, InvalidSkuException {
        return productService.updateById(sku, createProductDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable String sku) throws ProductNotFoundException {
        productService.delete(sku);
    }

}
