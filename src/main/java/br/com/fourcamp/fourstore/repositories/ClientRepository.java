package br.com.fourcamp.fourstore.repositories;


import br.com.fourcamp.fourstore.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClientRepository extends JpaRepository<Client, UUID> {

    Optional<Client> findByCpf(String cpf);
    void deleteByCpf(String cpf);

}

