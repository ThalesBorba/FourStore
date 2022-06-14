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

    public static List<Stock> updateStock(List<Stock> stockList, CreateTransactionDTO createTransactionDTO) throws InvalidParametersException {
        HashMap<String, Integer> cart = createTransactionDTO.getCart();
        for (Map.Entry<String, Integer> purchases : cart.entrySet()) {
            for (Stock stock : stockList) {
                if(purchases.getKey().equals(stock.getProduct().getSku())) {
                    stock.setQuantity(stock.getQuantity() - purchases.getValue());
                }
            }
        }return stockList;
    }


    public static Double retornaLucro(HashMap<Product, Integer> cart, Integer paymentMethod) throws
            InvalidParametersException {
        Double lucro = 0.0;
        Double discount = PaymentMethodEnum.getDiscountByPaymentMethodId(paymentMethod);
        if (discount == null) { throw new InvalidParametersException();}
        for (Map.Entry<Product,Integer> products : cart.entrySet()) {
            Product product = products.getKey();
            Double lucroIndividual = (product.getSellPrice() * (1 - (discount))) - product.getBuyPrice();
            lucro += lucroIndividual * products.getValue();
        }
        return lucro;
    }
    }
