package br.com.fourcamp.fourstore.FourStore.repositories;


import br.com.fourcamp.fourstore.FourStore.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, String> {

    Client findByCpf(String cpf);
    void deleteByCpf(String cpf);

}

