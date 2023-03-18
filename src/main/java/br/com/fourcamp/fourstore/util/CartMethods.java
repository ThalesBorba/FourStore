package br.com.fourcamp.fourstore.util;

import br.com.fourcamp.fourstore.dto.request.CreateTransactionDTO;
import br.com.fourcamp.fourstore.entities.Product;
import br.com.fourcamp.fourstore.entities.Stock;
import br.com.fourcamp.fourstore.enums.PaymentMethodEnum;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@UtilityClass
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


    public static BigDecimal returnProfit(Map<Product, Integer> cart, Integer paymentMethod) {
        BigDecimal profit = BigDecimal.ZERO;
        Double discount = PaymentMethodEnum.getDiscountByPaymentMethodId(paymentMethod);
        for (Map.Entry<Product,Integer> products : cart.entrySet()) {
            Product product = products.getKey();
            BigDecimal lucroIndividual = product.getSellPrice().multiply(BigDecimal.valueOf((1 - (discount)))).subtract(product.getBuyPrice());
            profit = profit.add(lucroIndividual.multiply(BigDecimal.valueOf(products.getValue())));
        }
        return profit.setScale(2, RoundingMode.HALF_EVEN);
    }
    }
