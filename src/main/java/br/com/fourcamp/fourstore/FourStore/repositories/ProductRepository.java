package br.com.fourcamp.fourstore.FourStore.repositories;

import br.com.fourcamp.fourstore.FourStore.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, String> {


}