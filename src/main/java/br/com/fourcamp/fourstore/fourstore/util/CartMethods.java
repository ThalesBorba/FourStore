package br.com.fourcamp.fourstore.fourstore.util;

import br.com.fourcamp.fourstore.fourstore.dto.request.CreateTransactionDTO;
import br.com.fourcamp.fourstore.fourstore.entities.Product;
import br.com.fourcamp.fourstore.fourstore.entities.Stock;
import br.com.fourcamp.fourstore.fourstore.enums.PaymentMethodEnum;
import br.com.fourcamp.fourstore.fourstore.exceptions.InvalidParametersException;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class CartMethods {

    public static List<Stock> updateStock(List<Stock> stockList, CreateTransactionDTO createTransactionDTO) {
        HashMap<String, Integer> cart = createTransactionDTO.getCart();
        for (Map.Entry<String, Integer> purchases : cart.entrySet()) {
            for (Stock stock : stockList) {
                if(purchases.getKey().equals(stock.getProduct().getSku())) {
                    stock.setQuantity(stock.getQuantity() - purchases.getValue());
                }
            }
        }return stockList;
    }


    public static Double retornaLucro(HashMap<Product, Integer> cart, Integer paymentMethod) {
        Double lucro = 0.0;
        Double discount = PaymentMethodEnum.getDiscountByPaymentMethodId(paymentMethod);
        for (Map.Entry<Product,Integer> products : cart.entrySet()) {
            Product product = products.getKey();
            Double lucroIndividual = (product.getSellPrice() * (1 - (discount))) - product.getBuyPrice();
            lucro += lucroIndividual * products.getValue();
        }
        return lucro;
    }
    }
