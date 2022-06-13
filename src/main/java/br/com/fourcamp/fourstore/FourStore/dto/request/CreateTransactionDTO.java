package br.com.fourcamp.fourstore.FourStore.dto.request;

import br.com.fourcamp.fourstore.FourStore.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateTransactionDTO {

    private String ClientCpf;


    private HashMap<Product, Integer> cart;
}