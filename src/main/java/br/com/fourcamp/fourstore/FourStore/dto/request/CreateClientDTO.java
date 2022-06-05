package br.com.fourcamp.fourstore.FourStore.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateClientDTO {

    private String cpf;

    private String nome;

    private Integer paymentMethod;

    private String paymentData;

}