package br.com.fourcamp.fourstore.FourStore.util;

import br.com.fourcamp.fourstore.FourStore.enums.BrandEnum;
import br.com.fourcamp.fourstore.FourStore.enums.CategoryEnum;
import br.com.fourcamp.fourstore.FourStore.enums.ColorEnum;
import br.com.fourcamp.fourstore.FourStore.enums.DepartmentEnum;

public class Validations {

    public boolean EnumValidation(Integer brand) {
        Boolean validation = BrandEnum.getByOption(brand) != null;
        return validation;
    }

    public boolean categoryValidation(Integer category) {
        Boolean validation = CategoryEnum.getByKey(category) != null;
        return validation;
    }

    public boolean colorValidation(Integer color) {
        Boolean validation = ColorEnum.getByKey(color) != null;
        return validation;
    }

    public boolean departmentValidation(Integer department) {
        Boolean validation = DepartmentEnum.getByKey(department) != null;
        return validation;
    }

    public boolean paymentMethodValidation(Integer paymentMethod) {
        Boolean validation = CategoryEnum.getByKey(paymentMethod) != null;
        return validation;
    }

    public boolean seasonValidation(Integer season) {
        Boolean validation = CategoryEnum.getByKey(season) != null;
        return validation;
    }

    public boolean sizeValidation(Integer size) {
        Boolean validation = CategoryEnum.getByKey(size) != null;
        return validation;
    }

    public boolean TypeOfMerchandiseValidation(Integer TypeOfMerchandise) {
        Boolean validation = CategoryEnum.getByKey(TypeOfMerchandise) != null;
        return validation;
    }
}
