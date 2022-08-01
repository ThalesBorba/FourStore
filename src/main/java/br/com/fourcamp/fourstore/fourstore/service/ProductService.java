package br.com.fourcamp.fourstore.fourstore.service;

import br.com.fourcamp.fourstore.fourstore.entities.Product;
import br.com.fourcamp.fourstore.fourstore.enums.*;
import br.com.fourcamp.fourstore.fourstore.exceptions.ProductNotFoundException;
import br.com.fourcamp.fourstore.fourstore.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> listAll() {
        return productRepository.findAll();
    }

    public Product findBySkuWithDetails(String sku) throws ProductNotFoundException {
        Product product = productRepository.findBySku(sku).orElseThrow(() -> new ProductNotFoundException(sku));
        setSkuIntoDetails(product);
        return product;
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