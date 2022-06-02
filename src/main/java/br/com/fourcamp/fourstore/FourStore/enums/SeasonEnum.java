package br.com.fourcamp.fourstore.FourStore.enums;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public enum SeasonEnum {

	SUMMER("40", "Verão"),
	WINTER("41", "Inverno"),
	FALL("42", "Outono"),
	SPRING("43", "Primavera");

	public String key;
	public String description;

	public String getKey() {
		return key;
	}

	public String getDescription() {
		return description;
	}

	SeasonEnum(String key, String description) {
		this.key = key;
		this.description = description;
	}

	private static final Map<String, SeasonEnum> Lookup = new HashMap<String, SeasonEnum>();

	static {
		for (SeasonEnum keyValue : EnumSet.allOf(SeasonEnum.class))
			Lookup.put(keyValue.getKey(), keyValue);
	}

	public static SeasonEnum get(String key) {
		return Lookup.get(key);
	}

	public static String getDescriptionByKey(String key) {
		for (SeasonEnum keyValue : EnumSet.allOf(SeasonEnum.class))
			if (keyValue.getKey().equals(key))
				return keyValue.getDescription();
		return null;
	}

	public static SeasonEnum getByKey(String seanson) {
		for (SeasonEnum keyValue : EnumSet.allOf(SeasonEnum.class)) {
			if (keyValue.getKey().equals(seanson))
				return keyValue;
		}
		return null;
	}
}
