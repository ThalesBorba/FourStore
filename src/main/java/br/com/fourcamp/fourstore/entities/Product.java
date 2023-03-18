package br.com.fourcamp.fourstore.entities;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
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
	private BigDecimal buyPrice;
	private BigDecimal sellPrice;
	private String brand;
	private String size;
	private String category;
	private String season;
	private String department;
	private String type;
	private String color;

	public void updatePrices(BigDecimal buyPrice, BigDecimal sellPrice) {
		this.buyPrice = buyPrice;
		this.sellPrice = sellPrice;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		Product product = (Product) o;
		return sku != null && Objects.equals(sku, product.sku);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}