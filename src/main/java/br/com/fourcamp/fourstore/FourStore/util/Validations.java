package br.com.fourcamp.fourstore.FourStore.util;

import br.com.fourcamp.fourstore.FourStore.enums.*;

import java.util.ArrayList;
import java.util.List;

public class Validations {

    public boolean brandValidation(String brand) {
        Boolean validation = BrandEnum.getByKey(brand) != null;
        return validation;
    }

    public boolean categoryValidation(String category) {
        Boolean validation = CategoryEnum.getByKey(category) != null;
        return validation;
    }

    public boolean colorValidation(String color) {
        Boolean validation = ColorEnum.getByKey(color) != null;
        return validation;
    }

    public boolean departmentValidation(String department) {
        Boolean validation = DepartmentEnum.getByKey(department) != null;
        return validation;
    }

    public boolean seasonValidation(String season) {
        Boolean validation = SeasonEnum.getByKey(season) != null;
        return validation;
    }

    public boolean sizeValidation(String size) {
        Boolean validation = SizeEnum.getByKey(size) != null;
        return validation;
    }

    public boolean typeOfMerchandiseValidation(String typeOfMerchandise) {
        Boolean validation = TypeOfMerchandiseEnum.getByKey(typeOfMerchandise) != null;
        return validation;
    }

    public Boolean validateSku(String sku) {
        List<Boolean> skuValidations = new ArrayList<>();
        skuValidations.add(brandValidation(sku.substring(0, 3)));
        skuValidations.add(sizeValidation(sku.substring(3, 5)));
        skuValidations.add(categoryValidation(sku.substring(5, 7)));
        skuValidations.add(seasonValidation(sku.substring(7, 9)));
        skuValidations.add(departmentValidation(sku.substring(9, 11)));
        skuValidations.add(typeOfMerchandiseValidation(sku.substring(11, 14)));
        skuValidations.add(colorValidation(sku.substring(14, 16)));

        for (Boolean value : skuValidations) {
            if (!value)
                return false;
        }
        return true;
    }

    public boolean paymentMethodValidation(String paymentMethod) {
        Boolean validation = CategoryEnum.getByKey(paymentMethod) != null;
        return validation;
    }
}
