package br.com.fourcamp.fourstore.dto.request;

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

    private String clientCpf;

    private HashMap<String, Integer> cart;
}