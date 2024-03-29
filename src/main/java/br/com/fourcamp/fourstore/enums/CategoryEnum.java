package br.com.fourcamp.fourstore.enums;

import java.util.EnumSet;

public enum CategoryEnum {

	MALE("10", "Masculino"),
	FEMALE("11", "Feminino"),
	BABY("12", "Moda Bebê");

	private String key;
	private String description;

	public String getKey() {
		return key;
	}

	public String getDescription() {
		return description;
	}

	CategoryEnum(String key, String description) {
		this.key = key;
		this.description = description;
	}

	public static String getDescriptionByKey(String key) {
		for (CategoryEnum keyValue : EnumSet.allOf(CategoryEnum.class)) {
			if (keyValue.getKey().equals(key))
				return keyValue.getDescription();
		}
		return null;
	}

	public static CategoryEnum getByKey(String option) {
		for (CategoryEnum keyValue : EnumSet.allOf(CategoryEnum.class))
			if (keyValue.getKey().equals(option))
				return keyValue;
		return null;
	}
}
