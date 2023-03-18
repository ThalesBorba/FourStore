package br.com.fourcamp.fourstore.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReturnTransactionDTO {

    private UUID id;
    private String clientName;
    private String clientCpf;
    private BigDecimal profit;

    public void setNameAndCpf(String name, String cpf) {
        this.clientName = name;
        this.clientCpf = cpf;
    }

}