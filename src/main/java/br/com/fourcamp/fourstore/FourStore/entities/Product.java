package br.com.fourcamp.fourstore.FourStore.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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

	public void createSku() {
		// recebe dados

	}

	/* @Valid
    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY)
    private List<Stock> stocks;*/

	/* @Valid
    @ManyToOne(mappedBy = "product", fetch = FetchType.LAZY)
    private List<Transaction> transactions;*/




	}


