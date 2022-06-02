package br.com.fourcamp.fourstore.FourStore.service;

import br.com.fourcamp.fourstore.FourStore.dto.request.CreateProductDTO;
import br.com.fourcamp.fourstore.FourStore.dto.response.MessageResponseDTO;
import br.com.fourcamp.fourstore.FourStore.entities.Product;
import br.com.fourcamp.fourstore.FourStore.exceptions.ProductNotFoundException;
import br.com.fourcamp.fourstore.FourStore.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ProductService {

    private ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public MessageResponseDTO createStock(Product product) {
        //validações
        Product savedProduct = setProduct(product);
        return createMessageResponse(savedProduct.getSku(), "Criado");
    }

    public MessageResponseDTO updateById(String sku, Product product) throws ProductNotFoundException {
        //validações
        verifyIfExists(sku);
        Product updatedProduct = setProduct(product);
        return createMessageResponse(updatedProduct.getSku(), "Updated");
    }

    public List<Product> listAll() {
        List<Product> allProducts = productRepository.findAll();
        return allProducts;
    }

    public void delete(String sku) throws ProductNotFoundException {
        verifyIfExists(sku);
        productRepository.deleteById(sku);
    }

    private MessageResponseDTO createMessageResponse(String sku, String s) {
        return MessageResponseDTO.builder().message(s + "produto com a sku" + sku).build();
    }

    private Product verifyIfExists(String sku) throws ProductNotFoundException {
        //trocar por find by product

        return productRepository.findById(sku)
                .orElseThrow(() -> new ProductNotFoundException(sku));
    }

    private Product setProduct(Product product) {
        return productRepository.save(product);
    }

    public Product findById(String sku) throws ProductNotFoundException {
        Product product = verifyIfExists(sku);
        return product;
    }

    private CreateProductDTO validStock(CreateProductDTO createProductDTO) {
        //validações
        return createProductDTO;
    }

}
