package br.com.fourcamp.fourstore.FourStore.dto.response;

import br.com.fourcamp.fourstore.FourStore.entities.Product;
import br.com.fourcamp.fourstore.FourStore.enums.*;

public class ReturnProductDetailsDTO {

    private String sku;
    private String description;
    private Double buyPrice;
    private Double sellPrice;
    private String brand;
    private String type;
    private String size;
    private String color;
    private String category;
    private String season;
    private String department;

    public ReturnProductDetailsDTO(Product product) {
        this.sku = product.getSku();
        this.description = product.getDescription();
        this.buyPrice = product.getBuyPrice();
        this.sellPrice = product.getSellPrice();
        this.brand = BrandEnum.getDescriptionByKey(product.getSku().substring(0, 3));
        this.type = TypeOfMerchandiseEnum.getDescriptionByKey(product.getSku().substring(11, 14));
        this.size = SizeEnum.getDescriptionByKey(product.getSku().substring(3, 5));
        this.color = ColorEnum.getDescriptionByKey(product.getSku().substring(14, 16));
        this.category = CategoryEnum.getDescriptionByKey(product.getSku().substring(5, 7));
        this.season = SeasonEnum.getDescriptionByKey(product.getSku().substring(7, 9));
        this.department = DepartmentEnum.getDescriptionByKey(product.getSku().substring(9, 11));
    }

}
