package br.com.fourcamp.fourstore.FourStore.service;

import br.com.fourcamp.fourstore.FourStore.dto.response.MessageResponseDTO;
import br.com.fourcamp.fourstore.FourStore.dto.response.ReturnProductDetailsDTO;
import br.com.fourcamp.fourstore.FourStore.entities.Product;
import br.com.fourcamp.fourstore.FourStore.exceptions.InvalidSellValueException;
import br.com.fourcamp.fourstore.FourStore.exceptions.InvalidSkuException;
import br.com.fourcamp.fourstore.FourStore.exceptions.ProductNotFoundException;
import br.com.fourcamp.fourstore.FourStore.mapper.ProductDetailsMapper;
import br.com.fourcamp.fourstore.FourStore.mapper.ProductMapper;
import br.com.fourcamp.fourstore.FourStore.repositories.ProductRepository;
import br.com.fourcamp.fourstore.FourStore.util.SkuValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private ProductRepository productRepository;

    private final ProductMapper productMapper = ProductMapper.INSTANCE;
    private final ProductDetailsMapper productDetailsMapper = ProductDetailsMapper.INSTANCE;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public MessageResponseDTO createProduct(Product product) throws InvalidSellValueException,
            InvalidSkuException {
        Product savedProduct = setProduct(product);
        return createMessageResponse(savedProduct.getSku(), "Criado");
    }

    public MessageResponseDTO updateById(String sku, Product product) throws ProductNotFoundException,
            InvalidSellValueException, InvalidSkuException {
        verifyIfExists(sku);
        Product validProduct = validProduct(product);
        productRepository.save(validProduct);
        return createMessageResponse(validProduct.getSku(), "Updated");
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
        return productRepository.findById(sku)
                .orElseThrow(() -> new ProductNotFoundException(sku));
    }

    private Product setProduct(Product product) throws InvalidSellValueException, InvalidSkuException {
        Product validProduct = validProduct(product);
        return productRepository.save(validProduct);
    }

    public ReturnProductDetailsDTO findByIdWithDetails(String sku) throws ProductNotFoundException {
        Product product = verifyIfExists(sku);
        ReturnProductDetailsDTO returnProductDetailsDTO = productDetailsMapper.toDTO(product);
        return returnProductDetailsDTO;
    }

    public Product findById(String sku) throws ProductNotFoundException {
        Product product = verifyIfExists(sku);
        return product;
    }

    private Product validProduct(Product product) throws InvalidSellValueException,
            InvalidSkuException {
        if (product.getSellPrice() * 1.25 <= product.getBuyPrice() ||
                product.getSellPrice() < 0) {
            throw new InvalidSellValueException();
        } else if (SkuValidations.validateSku(product.getSku()).equals(false)) {
            throw new InvalidSkuException();
        } else {
            return product;
        }
    }



}
