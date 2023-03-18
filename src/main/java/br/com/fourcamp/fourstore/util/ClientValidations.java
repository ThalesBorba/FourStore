package br.com.fourcamp.fourstore.util;

import lombok.experimental.UtilityClass;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@UtilityClass
public class ClientValidations {

    public static Boolean validateCards(String card) {
    String regex = "(^4\\d{12}(?:\\d{3})?$)|(^(?:5[1-5]\\d{2}|222[1-9]|22[3-9]\\d|2[3-6]\\d{2}|27[01]\\d|" +
            "2720)\\d{12}$)|(3[47]\\d{13})|(^3(?:0[0-5]|[68]\\d)\\d{11}$)|(^6(?:011|5\\d{2})\\d{12}$)|" +
            "(^(?:2131|1800|35\\d{3})\\d{11}$)";
    Pattern cardPattern = Pattern.compile(regex);
    card = card.replaceAll("[-. ]", "");
    Matcher matcher = cardPattern.matcher(card);
        return matcher.matches();
    }

    public static Boolean validatePix(String pix) {
        Pattern email = Pattern.compile("^[A-Z\\d._%+-]+@[A-Z\\d.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Pattern cellphone = Pattern.compile("^\\+[1-9]\\d{3,14}$");
        Pattern cpfCnpj = Pattern.compile("(\\d{2}[.]?\\d{3}\\.?\\d{3}/?\\d{4}-?\\d{2})|(\\d{3}[.]?\\d{3}[.]?\\d{3}-?\\d{2})");
        return email.matcher(pix).matches() || cellphone.matcher(pix).matches() ||
                cpfCnpj.matcher(pix).matches();
    }

    public static Boolean validateCpf(String cpf) {
        Pattern cpfPattern = Pattern.compile("^(\\d{3}\\.?){3}-?\\d{2}$");
        return cpfPattern.matcher(cpf).matches();
    }


    public static boolean paymentMethodValidation(Integer paymentMethod, String paymentData) {
        return switch (paymentMethod) {
            case 1, 2, 6 -> true;
            case 3, 4 -> validateCards(paymentData);
            case 5 -> validatePix(paymentData);
            default -> false;
        };
    }


}
