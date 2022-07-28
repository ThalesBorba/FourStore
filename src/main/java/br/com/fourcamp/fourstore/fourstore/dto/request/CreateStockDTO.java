package br.com.fourcamp.fourstore.fourstore.dto.request;

import br.com.fourcamp.fourstore.fourstore.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateStockDTO {



    private Product product;

    //todo só sku usar mapper

    private Integer quantity;
}