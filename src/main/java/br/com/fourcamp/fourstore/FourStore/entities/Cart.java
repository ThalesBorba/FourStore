package br.com.fourcamp.fourstore.FourStore.entities;

import java.util.HashMap;
import java.util.Map;

public class Cart {

    static HashMap<Product, Integer> cart;

    public Double retornaLucro(Product product, Integer quantity) {
        Double lucro = 0.0;
        for (Map.Entry<Product,Integer> products : cart.entrySet()) {
            Double lucroIndividual = product.getSellPrice() - product.getBuyPrice();
            lucro += lucroIndividual * products.getValue();
        }
        return lucro;
    }
    }
