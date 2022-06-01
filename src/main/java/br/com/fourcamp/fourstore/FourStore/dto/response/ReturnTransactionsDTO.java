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
public class ReturnTransactionsDTO {

    private Long id;
    private Long clientCpf;
    private Double profit;

    public ReturnTransactionsDTO(Transaction transaction) {
        this.id = transaction.getId();
        this.clientCpf = transaction.getClient().getCpf();
        this.profit = transaction.getProfit();
    }

}