package br.com.fourcamp.fourstore.FourStore.entities;

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

	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "stock_id", nullable = false)
	private Stock stock;

	}


