package br.com.fourcamp.fourstore.FourStore.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Client implements Serializable {

    @Id
    @CPF
    private Long id;
    //Mesmo quando o POST falha, o ID é usado?

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String paymentData;

   /* @Valid
    @OneToOne(mappedBy = "cliente", fetch = FetchType.LAZY)
    private List<Transaction> transactions;*/

}

