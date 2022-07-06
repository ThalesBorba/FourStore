package br.com.fourcamp.fourstore.fourstore.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="products")
public class Product implements Serializable {


	@Id
	@Column(name = "sku", nullable = false)
	private String sku;

	@NotNull
	private String description;

	@Min(10)
	@NotNull
	private Double buyPrice;

	@Min(10)
	@NotNull
	private Double sellPrice;

	@JsonIgnore
	private String brand;

	@JsonIgnore
	private String size;

	@JsonIgnore
	private String category;

	@JsonIgnore
	private String season;

	@JsonIgnore
	private String department;

	@JsonIgnore
	private String type;

	@JsonIgnore
	private String color;
}