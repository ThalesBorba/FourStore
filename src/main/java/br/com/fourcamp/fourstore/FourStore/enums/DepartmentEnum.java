package br.com.fourcamp.fourstore.FourStore.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum DepartmentEnum {

	CLOTHING("50", "Vestuário"),
	SHOES("51", "Calçados"),
	PERFUMERY("52", "Perfumaria"),
	ACCESSORIES("53", "Acessórios");

	public String key;
	public String description;

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

	private static final Map<String, DepartmentEnum> Lookup = new HashMap<String, DepartmentEnum>();

	static {
		for (DepartmentEnum keyValue : EnumSet.allOf(DepartmentEnum.class))
			Lookup.put(keyValue.getKey(), keyValue);
	}

	public static DepartmentEnum get(String key) {
		return Lookup.get(key);
	}

	public static DepartmentEnum getByDescription(String description) {
		for (DepartmentEnum keyValue : EnumSet.allOf(DepartmentEnum.class)) {
			if (keyValue.getDescription().equals(description))
				return keyValue;
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
