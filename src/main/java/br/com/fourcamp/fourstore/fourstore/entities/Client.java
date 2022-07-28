package br.com.fourcamp.fourstore.fourstore.entities;

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
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="clients")
public class Client implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    @CPF
    @Column(nullable = false, unique = true)
    private String cpf;
    @NotNull
    private String name;
    @NotNull
    @Min(1)
    @Max(6)
    private Integer paymentMethod;
    //verificação personalizada
    private String paymentData;
    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY, orphanRemoval = false)
    private List<Transaction> transactions;

}

