package br.com.fourcamp.fourstore.fourstore.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductDTO {

    private String sku;

    private String description;

    private Double buyPrice;

    private Double sellPrice;
}