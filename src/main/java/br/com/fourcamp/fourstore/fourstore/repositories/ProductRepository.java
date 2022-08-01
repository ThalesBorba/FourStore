package br.com.fourcamp.fourstore.fourstore.repositories;

import br.com.fourcamp.fourstore.fourstore.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {
    Optional<Product> findBySku(String sku);
    Product deleteBySku(String sku);

}