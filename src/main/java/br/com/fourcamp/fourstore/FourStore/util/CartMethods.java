package br.com.fourcamp.fourstore.FourStore.util;

import br.com.fourcamp.fourstore.FourStore.dto.request.CreateTransactionDTO;
import br.com.fourcamp.fourstore.FourStore.entities.Product;
import br.com.fourcamp.fourstore.FourStore.entities.Stock;
import br.com.fourcamp.fourstore.FourStore.entities.Transaction;
import br.com.fourcamp.fourstore.FourStore.enums.PaymentMethodEnum;
import br.com.fourcamp.fourstore.FourStore.exceptions.InvalidParametersException;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class CartMethods {

    public static List<Stock> updateStock(CreateTransactionDTO createTransactionDTO) throws InvalidParametersException {
        List<Stock> stockList = new ArrayList<Stock>();
        HashMap<Product, Integer> cart = Transaction.getCart();
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
