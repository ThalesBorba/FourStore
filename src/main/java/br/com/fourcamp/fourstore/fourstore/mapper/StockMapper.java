package br.com.fourcamp.fourstore.fourstore.mapper;

import br.com.fourcamp.fourstore.fourstore.dto.request.CreateStockDTO;
import br.com.fourcamp.fourstore.fourstore.dto.response.ReturnStockDTO;
import br.com.fourcamp.fourstore.fourstore.entities.Stock;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface StockMapper {

    StockMapper INSTANCE = Mappers.getMapper(StockMapper.class);
    Stock toModel(CreateStockDTO createStockDTO);

    @Mapping(target = "product.description", source = "returnStockDTO.productDescription")
    @Mapping(target = "product.sku", source = "returnStockDTO.sku")
    Stock toModel(ReturnStockDTO returnStockDTO);

    @Mapping(target = "productDescription", source = "stock.product.description")
    @Mapping(target = "sku", source = "stock.product.sku")
    ReturnStockDTO toDTO(Stock stock);
}
