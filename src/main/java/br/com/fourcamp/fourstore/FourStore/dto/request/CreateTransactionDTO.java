package br.com.fourcamp.fourstore.FourStore.dto.request;

import br.com.fourcamp.fourstore.FourStore.entities.Client;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransactionDTO {

    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente")
    private Client client;


}
