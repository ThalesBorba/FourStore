package br.com.fourcamp.fourstore.FourStore.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum TypeOfMerchandiseEnum {
	PANTS(231, "Calça"),
	TSHIRT(232, "Camisa"),
	BLOUSE(233, "Blusa"),
	SHOES(234, "Tênis"),
	FLIPFLOPS(235, "Chinelos"),
	INTIMATE(236, "Roupa Íntima"),
	SCARF(239, "Cachecol"),
	TIE(240, "Gravata");

	public Integer key;
	public String description;

	public Integer getKey() {
		return key;
	}

	public String getDescription() {
		return description;
	}

	TypeOfMerchandiseEnum(Integer key, String description) {
		this.key = key;
		this.description = description;
	}

	private static final Map<Integer, TypeOfMerchandiseEnum> Lookup = new HashMap<Integer, TypeOfMerchandiseEnum>();

	static {
		for (TypeOfMerchandiseEnum keyValue : EnumSet.allOf(TypeOfMerchandiseEnum.class))
			Lookup.put(keyValue.getKey(), keyValue);
	}

	public static TypeOfMerchandiseEnum get(Integer key) {
		return Lookup.get(key);
	}

	public static TypeOfMerchandiseEnum getByDescription(String description) {
		for (TypeOfMerchandiseEnum keyValue : EnumSet.allOf(TypeOfMerchandiseEnum.class)) {
			if (keyValue.getDescription().equals(description))
				return keyValue;
		}
		return null;
	}

	public static TypeOfMerchandiseEnum getByKey(Integer type) {
		for (TypeOfMerchandiseEnum keyValue : EnumSet.allOf(TypeOfMerchandiseEnum.class)) {
			if (keyValue.getKey().equals(type))
				return keyValue;
		}
		return null;
	}
}
