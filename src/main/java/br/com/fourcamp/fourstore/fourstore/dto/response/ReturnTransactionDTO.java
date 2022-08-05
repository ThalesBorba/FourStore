package br.com.fourcamp.fourstore.fourstore.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReturnTransactionDTO {

    private Long id;
    private String clientName;
    private String clientCpf;
    private Double profit;

    public void setNameAndCpf(String name, String cpf) {
        this.clientName = name;
        this.clientCpf = cpf;
    }

}