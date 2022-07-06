package br.com.fourcamp.fourstore.fourstore.dto.response;

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
