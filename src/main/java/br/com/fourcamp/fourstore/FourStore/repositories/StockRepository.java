package br.com.fourcamp.fourstore.FourStore.repositories;

import br.com.fourcamp.fourstore.FourStore.entities.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockRepository extends JpaRepository<Stock, String> {
}