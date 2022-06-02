package br.com.fourcamp.fourstore.FourStore.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Client implements Serializable {

    @Id
    @CPF
    private String cpf;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String paymentData;

    @Valid
    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY, orphanRemoval = false)
    private List<Transaction> transactions;

}

