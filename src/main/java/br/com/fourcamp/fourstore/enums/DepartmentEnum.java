package br.com.fourcamp.fourstore.enums;

import java.util.EnumSet;

public enum DepartmentEnum {

	CLOTHING("50", "Vestuário"),
	SHOES("51", "Calçados"),
	PERFUMERY("52", "Perfumaria"),
	ACCESSORIES("53", "Acessórios");

	private String key;
	private String description;

	public String getKey() {
		return key;
	}

	public String getDescription() {
		return description;
	}

	DepartmentEnum(String key, String description) {
		this.key = key;
		this.description = description;
	}

	public static String getDescriptionByKey(String key) {
		for (DepartmentEnum keyValue : EnumSet.allOf(DepartmentEnum.class)) {
			if (keyValue.getKey().equals(key))
				return keyValue.getDescription();
		}
		return null;
	}

	public static DepartmentEnum getByKey(String department) {
		for (DepartmentEnum keyValue : EnumSet.allOf(DepartmentEnum.class)) {
			if (keyValue.getKey().equals(department))
				return keyValue;
		}
		return null;
	}
}
