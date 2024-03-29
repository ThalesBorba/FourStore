package br.com.fourcamp.fourstore.dto.request;

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

    private String name;

    private Integer paymentMethod;

    private String paymentData;

}