package br.com.fourcamp.fourstore.FourStore.dto.response;

import br.com.fourcamp.fourstore.FourStore.entities.Stock;

public class ReturnStockDTO {

    private Long id;
    private String productDescription;
    private String sku;
    private Integer quantity;

    public ReturnStockDTO(Stock stock) {
        this.id = stock.getId();
        this.productDescription = stock.getProduct().getDescription();
        this.sku = stock.getProduct().getSku();
        this.quantity = stock.getQuantity();
    }

}
