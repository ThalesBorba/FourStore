package br.com.fourcamp.fourstore.FourStore.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.Min;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReturnProductDTO {

    private Long sku;
    private String description;
    private Double buyPrice;
    private Double sellPrice;

}
