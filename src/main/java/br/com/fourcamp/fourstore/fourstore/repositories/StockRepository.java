package br.com.fourcamp.fourstore.fourstore.repositories;

import br.com.fourcamp.fourstore.fourstore.entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, Integer> {

}

