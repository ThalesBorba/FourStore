package br.com.fourcamp.fourstore.FourStore.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum TypeOfMerchandiseEnum {
	PANTS("231", "Calça"),
	TSHIRT("232", "Camisa"),
	BLOUSE("233", "Blusa"),
	SHOES("234", "Tênis"),
	FLIPFLOPS("235", "Chinelos"),
	INTIMATE("236", "Roupa Íntima"),
	SCARF("239", "Cachecol"),
	TIE("240", "Gravata");

	public String key;
	public String description;

	public String getKey() {
		return key;
	}

	public String getDescription() {
		return description;
	}

	TypeOfMerchandiseEnum(String key, String description) {
		this.key = key;
		this.description = description;
	}

	private static final Map<String, TypeOfMerchandiseEnum> Lookup = new HashMap<String, TypeOfMerchandiseEnum>();

	static {
		for (TypeOfMerchandiseEnum keyValue : EnumSet.allOf(TypeOfMerchandiseEnum.class))
			Lookup.put(keyValue.getKey(), keyValue);
	}

	public static TypeOfMerchandiseEnum get(String key) {
		return Lookup.get(key);
	}

	public static String getDescriptionByKey(String key) {
		for (TypeOfMerchandiseEnum keyValue : EnumSet.allOf(TypeOfMerchandiseEnum.class)) {
			if (keyValue.getKey().equals(key))
				return keyValue.getDescription();
		}
		return null;
	}

	public static TypeOfMerchandiseEnum getByKey(String type) {
		for (TypeOfMerchandiseEnum keyValue : EnumSet.allOf(TypeOfMerchandiseEnum.class)) {
			if (keyValue.getKey().equals(type))
				return keyValue;
		}
		return null;
	}
}
