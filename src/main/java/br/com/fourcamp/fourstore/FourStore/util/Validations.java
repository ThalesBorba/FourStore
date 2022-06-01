package br.com.fourcamp.fourstore.FourStore.util;

public class Validations {

    public boolean brandValidation(Integer brand) {
        Boolean validation = (brand >= 1 && brand <= 22);
        return validation;
    }

    public boolean categoryValidation(Integer category) {
        Boolean validation = (category >= 10 && category <= 12);
        return true;
    }

    public boolean colorValidation(Integer color) {
        Boolean validation = (color >= 50 && color <= 58);
        return true;
    }

    public boolean departmentValidation(Integer department) {
        Boolean validation = (department >= 50 && department <= 53);
        return true;
    }

    public boolean paymentMethodValidation(Integer paymentMethod) {
        Boolean validation = (paymentMethod >= 1 && paymentMethod <= 6);
        return true;
    }

    public boolean seasonValidation(Integer season) {
        Boolean validation = (season >= 40 && season <= 43);
        return true;
    }

    public boolean sizeValidation(Integer size) {
        return true;
    }

    public boolean TypeOfMerchandiseValidation(Integer TypeOfMerchandise) {
        return true;
    }
}
