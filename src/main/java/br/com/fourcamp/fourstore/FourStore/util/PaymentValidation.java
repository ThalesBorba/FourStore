package br.com.fourcamp.fourstore.FourStore.util;

import br.com.fourcamp.fourstore.FourStore.enums.CategoryEnum;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PaymentValidation {

    public static Boolean validateCards(String card) {
    String regex = "(^4[0-9]{12}(?:[0-9]{3})?$)|(^(?:5[1-5][0-9]{2}|222[1-9]|22[3-9][0-9]|2[3-6][0-9]{2}|27[01][0-9]|" +
            "2720)[0-9]{12}$)|(3[47][0-9]{13})|(^3(?:0[0-5]|[68][0-9])[0-9]{11}$)|(^6(?:011|5[0-9]{2})[0-9]{12}$)|" +
            "(^(?:2131|1800|35\\d{3})\\d{11}$)";
    Pattern cardPattern = Pattern.compile(regex);
    card = card.replaceAll("[-. ]", "");
    Matcher matcher = cardPattern.matcher(card);
    if(matcher.matches()) return true;
    else return false;
    }

    public static Boolean validatePix(String pix) {
        Pattern email = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Pattern cellphone = Pattern.compile("^\\+[1-9]{1}[0-9]{3,14}$");
        Pattern cpfCnpj = Pattern.compile("([0-9]{2}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[\\/]?[0-9]{4}[-]?[0-9]{2})|" +
                "([0-9]{3}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[-]?[0-9]{2})");
        if(email.matcher(pix).matches() || cellphone.matcher(pix).matches() ||
                cpfCnpj.matcher(pix).matches()) return true;
        else return false;
    }


    public boolean paymentMethodValidation(String paymentMethod) {
        Boolean validation = CategoryEnum.getByKey(paymentMethod) != null;
        return validation;
    }


}
