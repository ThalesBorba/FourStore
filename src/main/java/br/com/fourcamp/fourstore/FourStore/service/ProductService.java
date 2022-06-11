package br.com.fourcamp.fourstore.FourStore.service;

import br.com.fourcamp.fourstore.FourStore.dto.request.CreateProductDTO;
import br.com.fourcamp.fourstore.FourStore.dto.response.MessageResponseDTO;
import br.com.fourcamp.fourstore.FourStore.dto.response.ReturnProductDTO;
import br.com.fourcamp.fourstore.FourStore.dto.response.ReturnProductDetailsDTO;
import br.com.fourcamp.fourstore.FourStore.entities.Product;
import br.com.fourcamp.fourstore.FourStore.enums.*;
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

    public ReturnProductDetailsDTO findBySkuWithDetails(String sku) throws ProductNotFoundException {
        Product product = verifyIfExists(sku);
        setSkuIntoDetails(product);
        return productDetailsMapper.toDTO(product);
    }

    private void setSkuIntoDetails(Product product) {
        product.setBrand(BrandEnum.getDescriptionByKey(product.getSku().substring(0, 3)));
        product.setSize(SizeEnum.getDescriptionByKey(product.getSku().substring(3, 5)));
        product.setCategory(CategoryEnum.getDescriptionByKey(product.getSku().substring(5, 7)));
        product.setSeason(SeasonEnum.getDescriptionByKey(product.getSku().substring(7, 9)));
        product.setDepartment(DepartmentEnum.getDescriptionByKey(product.getSku().substring(9, 11)));
        product.setType(TypeOfMerchandiseEnum.getDescriptionByKey(product.getSku().substring(11, 14)));
        product.setColor(ColorEnum.getDescriptionByKey(product.getSku().substring(14, 16)));
    }

}