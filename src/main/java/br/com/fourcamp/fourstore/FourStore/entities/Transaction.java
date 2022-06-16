package br.com.fourcamp.fourstore.FourStore.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashMap;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="transactions")
public class Transaction implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "client_id")
	private Client client;

	@Transient
	private static HashMap<Product, Integer> cart;

	@Column
	private Double profit;

	public static HashMap<Product, Integer> getCart() {
		return cart;
	}
}
