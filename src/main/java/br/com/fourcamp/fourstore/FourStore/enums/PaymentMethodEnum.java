package br.com.fourcamp.fourstore.FourStore.enums;

import java.util.EnumSet;

public enum PaymentMethodEnum {

	BILLET("Boleto à vista", 1, 0.05),
	BILLETINSTALLMENT("Boleto parcelado", 2, 0.0),
	CREDITCARD("Cartão de crédito", 3, 0.0),
	DEBITCARD("Cartão de débito", 4, 0.05),
	PIX("Pix", 5, 0.1),
	CASH("Dinheiro à vista", 6, 0.1);

	private final String paymentMethod;
	private final Integer paymentMethodId;
	private final Double discount;

	public Double getDiscount() {
		return discount;
	}

	private PaymentMethodEnum(String paymentMethod, Integer paymentMethodId, Double discount) {
		this.paymentMethod = paymentMethod;
		this.paymentMethodId = paymentMethodId;
		this.discount = discount;

	}

	public Integer getPaymentMethodId() {
		return paymentMethodId;
	}

	public static Double getDiscountByPaymentMethodId(Integer paymentMethodId) {
		for (PaymentMethodEnum keyValue : EnumSet.allOf(PaymentMethodEnum.class))
			if (keyValue.getPaymentMethodId().equals(paymentMethodId))
				return keyValue.discount;
		return null;
	}
}
