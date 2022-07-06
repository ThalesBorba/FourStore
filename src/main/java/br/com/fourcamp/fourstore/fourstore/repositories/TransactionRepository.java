package br.com.fourcamp.fourstore.fourstore.repositories;

import br.com.fourcamp.fourstore.fourstore.entities.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {


}