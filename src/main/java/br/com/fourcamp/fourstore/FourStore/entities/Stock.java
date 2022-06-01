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
public class Stock implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JoinColumn
	@OneToOne(mappedBy = "sku", fetch = FetchType.LAZY, orphanRemoval = true)
	private Product product;

	@Column
	@Min(0)
	private Integer quantity;


}

