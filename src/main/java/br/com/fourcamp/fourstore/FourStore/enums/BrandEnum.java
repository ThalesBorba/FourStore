package br.com.fourcamp.fourstore.FourStore.enums;

import java.util.EnumSet;

public enum BrandEnum {
	KOSAIR("KSR", "Kosair", 1),
    HERING("HRG", "Hering", 2),
    KARL("KRL", "Karl", 3),
    MALWEE("MLW", "Malwee", 4),
    CALVINKLEIN("CKL", "Calvin Klein", 5),
    LACOSTE("LCO", "Lacoste", 6),
    DIESEL("DSL", "Diesel", 7),
    NIKE("NKE", "Nike", 8),
    ADIDAS("ADI", "Adidas", 9),
    PUMA("PMA", "Puma", 10),
    OLYMPIKUS("OLP", "Olympikus", 11),
    OBOTICARIO("OBT", "O Boticário", 12),
    NATURA("NTR", "Natura", 13),
    AVON("AVN", "Avon", 14),
    CHANEL5("CNL", "Chanel #5", 15),
    ABIPROJECT("ABI", "Abi Project", 16),
    BOSS("BOS", "Hugo Boss", 17),
    REVAMPP("RVP", "Revamp", 18),
    NAAU("NAA", "NAAU", 19),
    ELEPHANT("ELP", "Elephant", 20),
    HIGHERSTORE("HRS", "Higher Store", 21),
    SANTABOUTIQUE("STB", "Santa Boutique", 22);

	private String key;
    private String description;
    private Integer option;

    BrandEnum(String key, String description, int option) {
    	this.key = key;
    	this.description = description;
    	this.option = option;
    }

    public String getKey() {
        return key;
    }

    public String getDescription() {
        return description;
    }

    public static String getDescriptionByKey(String key) {
        for (BrandEnum keyValue : EnumSet.allOf(BrandEnum.class))
            if (keyValue.getKey().equals(key))
                return keyValue.getDescription();
        return null;
    }

    public static BrandEnum getByKey(String brand) {
        for (BrandEnum keyValue : EnumSet.allOf(BrandEnum.class))
            if (keyValue.getKey().equals(brand))
                return keyValue;
        return null;
    }
}
