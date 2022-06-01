package br.com.fourcamp.fourstore.FourStore.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
	private Long sku;

	@Column(nullable = false)
	private String description;

	@Min(10)
	@Column(nullable = false)
	private Double buyPrice;

	@Min(10)
	@Column(nullable = false)
	private Double sellPrice;

	@JoinColumn
	@OneToOne(mappedBy = "id", fetch = FetchType.LAZY, orphanRemoval = true)
	private Stock stock;

	}


