package br.com.fourcamp.fourstore.FourStore.dto.response;

import br.com.fourcamp.fourstore.FourStore.entities.Product;
import br.com.fourcamp.fourstore.FourStore.entities.Stock;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReturnStockDTO {

    private Integer id;
    private String productDescription;
    private String sku;
    private Integer quantity;

}
