package br.com.fourcamp.fourstore.FourStore.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum ColorEnum {

	RED(50, "Vermelho"),
	GREEN(51, "Verde"),
	BLUE(52, "Azul"),
	WHITE(53, "Branco"),
	BLACK(54, "Preto"),
	YELLOW(55, "Amarelo"),
	BROWN(56, "Marrom"),
	GREY(57, "Cinza"),
	PINK(58, "Rosa");

	public Integer key;
	public String description;



	public Integer getKey() {
		return key;
	}

	public String getDescription() {
		return description;
	}

	ColorEnum(Integer key, String description) {
		this.key = key;
		this.description = description;
	}

	private static final Map<Integer, ColorEnum> Lookup = new HashMap<Integer, ColorEnum>();

	static {
		for (ColorEnum keyValue : EnumSet.allOf(ColorEnum.class))
			Lookup.put(keyValue.getKey(), keyValue);
	}

	public static ColorEnum get(String key) {
		return Lookup.get(key);
	}

	public static ColorEnum getByDescription(String description) {
		for (ColorEnum keyValue : EnumSet.allOf(ColorEnum.class)) {
			if (keyValue.getDescription().equals(description))
				return keyValue;
		}
		return null;
	}

	public static ColorEnum getByKey(Integer color) {
		for (ColorEnum keyValue : EnumSet.allOf(ColorEnum.class)) {
			if (keyValue.getKey().equals(color))
				return keyValue;
		}
		return null;
	}
}
