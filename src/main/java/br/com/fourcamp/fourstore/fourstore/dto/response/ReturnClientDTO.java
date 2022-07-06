package br.com.fourcamp.fourstore.fourstore.dto.response;

import br.com.fourcamp.fourstore.fourstore.entities.Transaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReturnClientDTO {

    private String cpf;

    private String name;

    private List<Transaction> transactions = new ArrayList<>();



}
