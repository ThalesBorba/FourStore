package br.com.fourcamp.fourstore.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "transactions")
public class Transaction implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, targetEntity = Client.class)
	@JoinColumn(name = "client_id", referencedColumnName = "id")
	private Client client;

	@Transient
	private static HashMap<Product, Integer> cart;

	@Column
	private BigDecimal profit;

	public void setClientAndProfit(Client client, BigDecimal profit) {
		this.client = client;
		this.profit = new BigDecimal(String.valueOf(profit)).setScale(2, RoundingMode.HALF_EVEN);
	}

	public static Map<Product, Integer> getCart() {
		return cart;
	}
}
