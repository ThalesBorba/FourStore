package br.com.fourcamp.fourstore.FourStore.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientValidations {

    public static Boolean validateCards(String card) {
    String regex = "(^4[0-9]{12}(?:[0-9]{3})?$)|(^(?:5[1-5][0-9]{2}|222[1-9]|22[3-9][0-9]|2[3-6][0-9]{2}|27[01][0-9]|" +
            "2720)[0-9]{12}$)|(3[47][0-9]{13})|(^3(?:0[0-5]|[68][0-9])[0-9]{11}$)|(^6(?:011|5[0-9]{2})[0-9]{12}$)|" +
            "(^(?:2131|1800|35\\d{3})\\d{11}$)";
    Pattern cardPattern = Pattern.compile(regex);
    card = card.replaceAll("[-. ]", "");
    Matcher matcher = cardPattern.matcher(card);
        return matcher.matches();
    }

    public static Boolean validatePix(String pix) {
        Pattern email = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Pattern cellphone = Pattern.compile("^\\+[1-9]{1}[0-9]{3,14}$");
        Pattern cpfCnpj = Pattern.compile("([0-9]{2}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[\\/]?[0-9]{4}[-]?[0-9]{2})|" +
                "([0-9]{3}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[-]?[0-9]{2})");
        return email.matcher(pix).matches() || cellphone.matcher(pix).matches() ||
                cpfCnpj.matcher(pix).matches();
    }

    public static Boolean validateCpf(String cpf) {
        Pattern cpfPattern = Pattern.compile("^([0-9]{3}\\.?){3}-?[0-9]{2}$");
        return cpfPattern.matcher(cpf).matches();
    }


    public static boolean paymentMethodValidation(Integer paymentMethod, String paymentData) {
        Boolean validation = false;
        switch (paymentMethod) {
            case 1, 2, 6 -> validation = true;
            case 3, 4 -> validation = validateCards(paymentData);
            case 5 -> validation = validatePix(paymentData);
            default -> validation = false;
        }
        return validation;
    }


}
