package br.com.fourcamp.fourstore.fourstore.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReturnProductDetailsDTO {

    private String sku;
    private String description;
    private Double buyPrice;
    private Double sellPrice;
    private String brand;
    private String size;
    private String category;
    private String season;
    private String department;
    private String type;
    private String color;

}
