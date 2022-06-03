package br.com.fourcamp.fourstore.FourStore.dto.request;

import br.com.fourcamp.fourstore.FourStore.entities.Stock;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Min;
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
