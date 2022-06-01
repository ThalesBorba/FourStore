package br.com.fourcamp.fourstore.FourStore.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum CategoryEnum {

	MALE(10, "Masculino"),
	FEMALE(11, "Feminino"),
	BABY(12, "Moda Bebê");

	public Integer key;
	public String description;

	public Integer getKey() {
		return key;
	}

	public String getDescription() {
		return description;
	}

	CategoryEnum(Integer key, String description) {
		this.key = key;
		this.description = description;
	}

	private static final Map<Integer, CategoryEnum> Lookup = new HashMap<Integer, CategoryEnum>();

	static {
		for (CategoryEnum keyValue : EnumSet.allOf(CategoryEnum.class))
			Lookup.put(keyValue.getKey(), keyValue);
	}

	public static CategoryEnum get(String key) {
		return Lookup.get(key);
	}

	public static CategoryEnum getByDescription(String description) {
		for (CategoryEnum keyValue : EnumSet.allOf(CategoryEnum.class)) {
			if (keyValue.getDescription().equals(description))
				return keyValue;
		}
		return null;
	}

	public static CategoryEnum getByKey(Integer option) {
		for (CategoryEnum keyValue : EnumSet.allOf(CategoryEnum.class))
			if (keyValue.getKey().equals(option))
				return keyValue;
		return null;
	}
}
