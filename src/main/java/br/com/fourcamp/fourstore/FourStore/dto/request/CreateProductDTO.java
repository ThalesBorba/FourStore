package br.com.fourcamp.fourstore.FourStore.dto.request;

import br.com.fourcamp.fourstore.FourStore.entities.Stock;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Min;

public class CreateProductDTO {

    private String sku;

    private String description;

    private Double buyPrice;

    private Double sellPrice;

    private Stock stock;
}
