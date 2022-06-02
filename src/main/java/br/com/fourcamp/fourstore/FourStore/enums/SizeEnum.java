package br.com.fourcamp.fourstore.FourStore.enums;

import java.util.EnumSet;

public enum SizeEnum {

	RN("10", "Tamanho RN"),
	PP("32", "Tamanho PP"),
	P("35", "Tamanho P"),
	M("37", "Tamanho M"),
	G("41", "Tamanho G"),
	GG("43", "Tamanho GG"),
	XG("45", "Tamanho XG"),
	XXG("48", "Tamanho XXG"),
	G1("50", "Tamanho G1"),
	G2("52", "Tamanho G2"),
	G3("54", "Tamanho G3");

	public String key;
	public String description;

	public String getKey() {
		return key;
	}

	public String getDescription() {
		return description;
	}

	SizeEnum(String key, String description) {
		this.key = key;
		this.description = description;
	}

	public static String getDescriptionByKey(String key) {
		for (SizeEnum keyValue : EnumSet.allOf(SizeEnum.class))
			if (keyValue.getKey().equals(key))
				return keyValue.getDescription();
		return null;
	}

	public static SizeEnum getByKey(String size) {
		for (SizeEnum keyValue : EnumSet.allOf(SizeEnum.class)) {
			if (keyValue.getKey().equals(size))
				return keyValue;
		}
		return null;
	}
}
