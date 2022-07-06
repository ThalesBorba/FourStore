package br.com.fourcamp.fourstore.fourstore.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReturnProductDTO {

    private String sku;
    private String description;
    private Double buyPrice;
    private Double sellPrice;

}
