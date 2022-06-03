package br.com.fourcamp.fourstore.FourStore.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product implements Serializable {


	@Id
	@Column(name = "sku", nullable = false)
	@NaturalId
	private String sku;

	@Column(nullable = false)
	private String description;

	@Min(10)
	@Column(nullable = false)
	private Double buyPrice;

	@Min(10)
	@Column(nullable = false)
	private Double sellPrice;

	@OneToOne
	@MapsId
	@JoinColumn(name = "stock_id")
	private Stock stock;

	}


