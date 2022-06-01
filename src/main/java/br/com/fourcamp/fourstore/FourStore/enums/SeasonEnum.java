package br.com.fourcamp.fourstore.FourStore.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum SeasonEnum {

	SUMMER(40, "Verão"),
	WINTER(41, "Inverno"),
	FALL(42, "Outono"),
	SPRING(43, "Primavera");

	public Integer key;
	public String description;

	public Integer getKey() {
		return key;
	}

	public String getDescription() {
		return description;
	}

	SeasonEnum(Integer key, String description) {
		this.key = key;
		this.description = description;
	}

	private static final Map<Integer, SeasonEnum> Lookup = new HashMap<Integer, SeasonEnum>();

	static {
		for (SeasonEnum keyValue : EnumSet.allOf(SeasonEnum.class))
			Lookup.put(keyValue.getKey(), keyValue);
	}

	public static SeasonEnum get(Integer key) {
		return Lookup.get(key);
	}

	public static SeasonEnum getByDescription(String description) {
		for (SeasonEnum keyValue : EnumSet.allOf(SeasonEnum.class))
			if (keyValue.getDescription().equals(description))
				return keyValue;
		return null;
	}

	public static SeasonEnum getByKey(Integer seanson) {
		for (SeasonEnum keyValue : EnumSet.allOf(SeasonEnum.class)) {
			if (keyValue.getKey().equals(seanson))
				return keyValue;
		}
		return null;
	}
}
