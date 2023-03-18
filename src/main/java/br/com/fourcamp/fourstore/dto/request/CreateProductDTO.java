package br.com.fourcamp.fourstore.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductDTO {

    private String sku;

    private String description;

    private BigDecimal buyPrice;

    private BigDecimal sellPrice;
}