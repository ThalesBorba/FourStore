package br.com.fourcamp.fourstore.FourStore.mapper;

import br.com.fourcamp.fourstore.FourStore.dto.request.CreateStockDTO;
import br.com.fourcamp.fourstore.FourStore.dto.response.ReturnStockDTO;
import br.com.fourcamp.fourstore.FourStore.entities.Stock;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StockMapper {

    StockMapper INSTANCE = Mappers.getMapper(StockMapper.class);
    Stock toModel(CreateStockDTO createStockDTO);

    Stock toModel(ReturnStockDTO returnStockDTO);

    @Mapping(target = "productDescription", source = "stock.product.description")
    @Mapping(target = "sku", source = "stock.product.sku")
    ReturnStockDTO toDTO(Stock stock);
}
