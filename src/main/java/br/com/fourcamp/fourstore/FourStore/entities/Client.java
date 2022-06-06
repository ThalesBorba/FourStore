package br.com.fourcamp.fourstore.FourStore.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="clients")
public class Client implements Serializable {

    @Id
    @CPF
    private String cpf;

    @NotNull
    private String nome;

    @NotNull
    @Min(1)
    @Max(6)
    private Integer paymentMethod;

    @NotNull
    private String paymentData;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY, orphanRemoval = false)
    private List<Transaction> transactions;

}

