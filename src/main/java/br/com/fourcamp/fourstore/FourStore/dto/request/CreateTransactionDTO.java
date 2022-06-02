package br.com.fourcamp.fourstore.FourStore.dto.request;

import br.com.fourcamp.fourstore.FourStore.entities.Cart;
import br.com.fourcamp.fourstore.FourStore.entities.Client;
import br.com.fourcamp.fourstore.FourStore.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransactionDTO {

    private Long id;

    @ManyToOne
    @JoinColumn(name = "client")
    private Client client;

    private Double profit;


}
