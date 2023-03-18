package br.com.fourcamp.fourstore.entities;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name="clients")
public class Client implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    private String paymentData;
    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY, targetEntity = Transaction.class)
    @ToString.Exclude
    private List<Transaction> transactions;

    public void updatePaymentInfo(Integer paymentMethod, String paymentData) {
        this.paymentMethod = paymentMethod;
        this.paymentData = paymentData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Client client = (Client) o;
        return id != null && Objects.equals(id, client.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

