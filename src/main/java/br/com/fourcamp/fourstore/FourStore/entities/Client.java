package br.com.fourcamp.fourstore.FourStore.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.Valid;
import java.io.Serializable;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Client implements Serializable {

    @Id
    @CPF
    private Long cpf;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String paymentData;

    @Valid
    @OneToMany(mappedBy = "id", fetch = FetchType.LAZY, orphanRemoval = false)
    @JoinColumn(name = "transaction")
    private Set<Transaction> transactions = new LinkedHashSet<>();

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(Set<Transaction> transactions) {
        this.transactions = transactions;
    }

   /* @Valid
    @OneToOne(mappedBy = "cliente", fetch = FetchType.LAZY)
    private List<Transaction> transactions;*/

}

