package br.com.fourcamp.fourstore.FourStore.entities;

import br.com.fourcamp.fourstore.FourStore.dto.request.CreateTransactionDTO;
import br.com.fourcamp.fourstore.FourStore.enums.PaymentMethodEnum;
import br.com.fourcamp.fourstore.FourStore.exceptions.InvalidParametersException;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class Cart {

    public static HashMap<Product, Integer> cart;

    public static List<Stock> updateStock(CreateTransactionDTO transaction) throws InvalidParametersException {
        List<Stock> stockList = new ArrayList<Stock>();
        HashMap<Product, Integer> cart = Cart.cart;
        for (Map.Entry<Product, Integer> purchases : cart.entrySet()) {
            stockList.add(new Stock(purchases.getKey(), purchases.getValue()));
        }return stockList;
    }


    public static Double retornaLucro(HashMap<Product, Integer> cart, Integer paymentMethod) throws
            InvalidParametersException {
        Double lucro = 0.0;
        Double discount = PaymentMethodEnum.getDiscountByPaymentMethodId(paymentMethod);
        if (discount.isNaN()) { throw new InvalidParametersException();}
        for (Map.Entry<Product,Integer> products : cart.entrySet()) {
            Product product = products.getKey();
            Double lucroIndividual = (product.getSellPrice() * (1 - (discount))) - product.getBuyPrice();
            lucro += lucroIndividual * products.getValue();
        }
        return lucro;
    }
    }
