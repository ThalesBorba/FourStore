package br.com.fourcamp.fourstore.FourStore.dto.response;

import br.com.fourcamp.fourstore.FourStore.entities.Transaction;
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

    public ReturnTransactionDTO(Transaction transaction) {
        this.id = transaction.getId();
        this.clientName = transaction.getClient().getNome();
        this.clientCpf = transaction.getClient().getCpf();
        this.profit = transaction.getProfit();
    }

}