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


    public static Double retornaLucro(Map<Product, Integer> cart, Integer paymentMethod) throws
            InvalidParametersException {
        Double lucro = 0.0;
        Double discount = PaymentMethodEnum.getDiscountByPaymentMethodId(paymentMethod);
        if (discount == null) { throw new InvalidParametersException();}
        for (Map.Entry<Product,Integer> products : cart.entrySet()) {
            Product product = products.getKey();
            if (product == null) {
                throw new InvalidParametersException();
            }
            Double lucroIndividual = (product.getSellPrice() * (1 - (discount))) - product.getBuyPrice();
            lucro += lucroIndividual * products.getValue();
        }
        return lucro;
    }
    }
