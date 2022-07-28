package br.com.fourcamp.fourstore.fourstore.repositories;

import br.com.fourcamp.fourstore.fourstore.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    Product findBySku(String sku);
    Product deleteBySku(String sku);

}