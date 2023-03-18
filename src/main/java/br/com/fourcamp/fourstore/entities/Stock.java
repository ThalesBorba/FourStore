package br.com.fourcamp.fourstore.entities;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name="stocks")
public class Stock implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID id;

	@OneToOne(cascade = CascadeType.ALL, targetEntity = Product.class)
	@JoinColumn(name = "product_id", referencedColumnName = "sku")
	private Product product;

	@Column
	@Min(0)
	private Integer quantity;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		Stock stock = (Stock) o;
		return id != null && Objects.equals(id, stock.id);
	}

	@Override
	public int hashCode() {
		return getClass().hashCode();
	}
}

