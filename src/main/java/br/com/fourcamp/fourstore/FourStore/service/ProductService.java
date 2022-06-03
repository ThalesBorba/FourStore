package br.com.fourcamp.fourstore.FourStore.service;

import br.com.fourcamp.fourstore.FourStore.dto.request.CreateProductDTO;
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

import java.util.List;

public class ProductService {

    private ProductRepository productRepository;

    private final ProductMapper productMapper = ProductMapper.INSTANCE;
    private final ProductDetailsMapper productDetailsMapper = ProductDetailsMapper.INSTANCE;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public MessageResponseDTO createProduct(CreateProductDTO createProductDTO) throws InvalidSellValueException,
            InvalidSkuException {
        Product savedProduct = setProduct(createProductDTO);
        return createMessageResponse(savedProduct.getSku(), "Criado");
    }

    public MessageResponseDTO updateById(String sku, CreateProductDTO createProductDTO) throws ProductNotFoundException,
            InvalidSellValueException, InvalidSkuException {
        verifyIfExists(sku);
        CreateProductDTO validProduct = validProduct(createProductDTO);
        Product updatedProduct = productMapper.toModel(validProduct);
        productRepository.save(updatedProduct);
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
        return productRepository.findById(sku)
                .orElseThrow(() -> new ProductNotFoundException(sku));
    }

    private Product setProduct(CreateProductDTO createProductDTO) throws InvalidSellValueException, InvalidSkuException {
        CreateProductDTO validProduct = validProduct(createProductDTO);
        Product productToSave = productMapper.toModel(validProduct);
        return productRepository.save(productToSave);
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

    private CreateProductDTO validProduct(CreateProductDTO createProductDTO) throws InvalidSellValueException,
            InvalidSkuException {
        if (createProductDTO.getSellPrice() * 1.25 <= createProductDTO.getBuyPrice() ||
                createProductDTO.getSellPrice() < 0) {
            throw new InvalidSellValueException();
        } else if (SkuValidations.validateSku(createProductDTO.getSku()).equals(false)) {
            throw new InvalidSkuException();
        } else {
            return createProductDTO;
        }
    }



}
