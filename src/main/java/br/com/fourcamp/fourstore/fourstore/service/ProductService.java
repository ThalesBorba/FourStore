package br.com.fourcamp.fourstore.fourstore.service;

import br.com.fourcamp.fourstore.fourstore.dto.response.MessageResponseDTO;
import br.com.fourcamp.fourstore.fourstore.dto.response.ReturnProductDTO;
import br.com.fourcamp.fourstore.fourstore.dto.response.ReturnProductDetailsDTO;
import br.com.fourcamp.fourstore.fourstore.entities.Product;
import br.com.fourcamp.fourstore.fourstore.enums.*;
import br.com.fourcamp.fourstore.fourstore.exceptions.ProductNotFoundException;
import br.com.fourcamp.fourstore.fourstore.mapper.ProductDetailsMapper;
import br.com.fourcamp.fourstore.fourstore.mapper.ProductMapper;
import br.com.fourcamp.fourstore.fourstore.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    protected static ProductRepository productRepository;

    private static final ProductMapper productMapper = ProductMapper.INSTANCE;
    private static final ProductDetailsMapper productDetailsMapper = ProductDetailsMapper.INSTANCE;

    @Autowired
    public ProductService(ProductRepository productRepository) {

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