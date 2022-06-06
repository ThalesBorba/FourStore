package br.com.fourcamp.fourstore.FourStore.dto.request;

import br.com.fourcamp.fourstore.FourStore.entities.Product;
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

    //só sku

    private Integer quantity;
}