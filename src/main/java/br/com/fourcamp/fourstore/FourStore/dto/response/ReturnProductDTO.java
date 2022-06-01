package br.com.fourcamp.fourstore.FourStore.dto.response;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.Min;

public class ReturnProductDTO {

    private Long sku;
    private String description;
    private Double buyPrice;
    private Double sellPrice;

}
