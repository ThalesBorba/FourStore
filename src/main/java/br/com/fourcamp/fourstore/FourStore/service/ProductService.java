package br.com.fourcamp.fourstore.FourStore.service;

import br.com.fourcamp.fourstore.FourStore.dto.request.CreateProductDTO;
import br.com.fourcamp.fourstore.FourStore.dto.response.MessageResponseDTO;
import br.com.fourcamp.fourstore.FourStore.dto.response.ReturnProductDTO;
import br.com.fourcamp.fourstore.FourStore.dto.response.ReturnProductDetailsDTO;
import br.com.fourcamp.fourstore.FourStore.entities.Product;
import br.com.fourcamp.fourstore.FourStore.exceptions.ClientNotFoundException;
import br.com.fourcamp.fourstore.FourStore.exceptions.InvalidSellValueException;
import br.com.fourcamp.fourstore.FourStore.exceptions.InvalidSkuException;
import br.com.fourcamp.fourstore.FourStore.exceptions.ProductNotFoundException;
import br.com.fourcamp.fourstore.FourStore.mapper.ProductDetailsMapper;
import br.com.fourcamp.fourstore.FourStore.mapper.ProductMapper;
import br.com.fourcamp.fourstore.FourStore.repositories.ProductRepository;
import br.com.fourcamp.fourstore.FourStore.util.SkuValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    protected static ProductRepository productRepository;

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

    public MessageResponseDTO updateBySku(String sku, CreateProductDTO createProductDTO) throws ProductNotFoundException,
            InvalidSellValueException, InvalidSkuException {
        verifyIfExists(sku);
        CreateProductDTO validProduct = validProduct(createProductDTO);
        Product updatedProduct = productMapper.toModel(validProduct);
        productRepository.save(updatedProduct);
        return createMessageResponse(updatedProduct.getSku(), "Updated");
    }

    public List<ReturnProductDTO> listAll() {
        List<Product> allProducts = productRepository.findAll();
        List<ReturnProductDTO> returnProductDTOList = new ArrayList<>();
        for (Product product : allProducts) {
            ReturnProductDTO returnProductDTO = productMapper.toDTO(product);
            returnProductDTOList.add(returnProductDTO);
        }
        return returnProductDTOList;
    }

    private MessageResponseDTO createMessageResponse(String sku, String s) {
        return MessageResponseDTO.builder().message(s + "produto com a sku" + sku).build();
    }

    private Product verifyIfExists(String sku) throws ProductNotFoundException {
        if (productRepository.findBySku(sku) != null) {
            return productRepository.findBySku(sku);
        } else {
            throw new ProductNotFoundException(sku);
        }
    }

    private Product setProduct(CreateProductDTO createProductDTO) throws InvalidSellValueException, InvalidSkuException {
        CreateProductDTO validProduct = validProduct(createProductDTO);
        Product productToSave = productMapper.toModel(validProduct);
        return productRepository.save(productToSave);
    }

    public ReturnProductDetailsDTO findByIdWithDetails(String sku) throws ProductNotFoundException {
        Product product = verifyIfExists(sku);
        return productDetailsMapper.toDTO(product);
    }

    public ReturnProductDTO findBySku(String sku) throws ProductNotFoundException {
        Product product = verifyIfExists(sku);
        return productMapper.toDTO(product);
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