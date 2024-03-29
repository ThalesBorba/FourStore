package br.com.fourcamp.fourstore.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReturnStockDTO {

    private UUID id;
    private String productDescription;
    private String sku;
    private Integer quantity;

}
