package br.com.fourcamp.fourstore.FourStore.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum PaymentMethodEnum {

	BILLET("Boleto à vista", 1, 5.0),
	BILLETINSTALLMENT("Boleto parcelado", 2, 0.0),
	CREDITCARD("Cartão de crédito", 3, 0.0),
	DEBITCARD("Cartão de débito", 4, 5.0),
	PIX("Pix", 5, 10.0),
	CASH("Dinheiro à vista", 6, 10.0);

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

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public Integer getPaymentMethodId() {
		return paymentMethodId;
	}

	private static final Map<String, PaymentMethodEnum> Lookup = new HashMap<String, PaymentMethodEnum>();

	static {
		for (PaymentMethodEnum keyValue : EnumSet.allOf(PaymentMethodEnum.class))
			Lookup.put(keyValue.getPaymentMethod(), keyValue);
	}

	public static PaymentMethodEnum get(String paymentMethod) {
		return Lookup.get(paymentMethod);
	}

	public static PaymentMethodEnum getByPaymentMethodId(Integer paymentMethodId) {
		for (PaymentMethodEnum keyValue : EnumSet.allOf(PaymentMethodEnum.class))
			if (keyValue.getPaymentMethodId().equals(paymentMethodId))
				return keyValue;
		return null;
	}
}
