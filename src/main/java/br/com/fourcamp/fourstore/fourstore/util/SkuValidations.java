package br.com.fourcamp.fourstore.fourstore.util;

import br.com.fourcamp.fourstore.fourstore.enums.*;

import java.util.ArrayList;
import java.util.List;

public class SkuValidations {

    public static boolean brandValidation(String brand) {
        return BrandEnum.getByKey(brand) != null;
    }

    public static boolean categoryValidation(String category) {
        return CategoryEnum.getByKey(category) != null;
    }

    public static boolean colorValidation(String color) {
        return ColorEnum.getByKey(color) != null;
    }

    public static boolean departmentValidation(String department) {
        return DepartmentEnum.getByKey(department) != null;
    }

    public static boolean seasonValidation(String season) {
        return SeasonEnum.getByKey(season) != null;
    }

    public static boolean sizeValidation(String size) {
        return SizeEnum.getByKey(size) != null;
    }

    public static boolean typeOfMerchandiseValidation(String typeOfMerchandise) {
        return TypeOfMerchandiseEnum.getByKey(typeOfMerchandise) != null;
    }

    public static Boolean validateSku(String sku) {
        List<Boolean> skuValidations = new ArrayList<>();
        skuValidations.add(brandValidation(sku.substring(0, 3)));
        skuValidations.add(sizeValidation(sku.substring(3, 5)));
        skuValidations.add(categoryValidation(sku.substring(5, 7)));
        skuValidations.add(seasonValidation(sku.substring(7, 9)));
        skuValidations.add(departmentValidation(sku.substring(9, 11)));
        skuValidations.add(typeOfMerchandiseValidation(sku.substring(11, 14)));
        skuValidations.add(colorValidation(sku.substring(14, 16)));

        for (boolean value : skuValidations) {
            if (!value)
                return false;
        }
        return true;
    }

}
