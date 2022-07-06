package br.com.fourcamp.fourstore.fourstore.enums;

import java.util.EnumSet;

public enum ColorEnum {

	RED("50", "Vermelho"),
	GREEN("51", "Verde"),
	BLUE("52", "Azul"),
	WHITE("53", "Branco"),
	BLACK("54", "Preto"),
	YELLOW("55", "Amarelo"),
	BROWN("56", "Marrom"),
	GREY("57", "Cinza"),
	PINK("58", "Rosa");

	private String key;
	private String description;



	public String getKey() {
		return key;
	}

	public String getDescription() {
		return description;
	}

	ColorEnum(String key, String description) {
		this.key = key;
		this.description = description;
	}

	public static String getDescriptionByKey(String key) {
		for (ColorEnum keyValue : EnumSet.allOf(ColorEnum.class)) {
			if (keyValue.getKey().equals(key))
				return keyValue.getDescription();
		}
		return null;
	}

	public static ColorEnum getByKey(String color) {
		for (ColorEnum keyValue : EnumSet.allOf(ColorEnum.class)) {
			if (keyValue.getKey().equals(color))
				return keyValue;
		}
		return null;
	}
}
