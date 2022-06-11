package br.com.fourcamp.fourstore.FourStore.mapper;

import br.com.fourcamp.fourstore.FourStore.dto.response.ReturnProductDetailsDTO;
import br.com.fourcamp.fourstore.FourStore.entities.Product;
import br.com.fourcamp.fourstore.FourStore.enums.BrandEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductDetailsMapper {

    ProductDetailsMapper INSTANCE = Mappers.getMapper(ProductDetailsMapper.class);

    ReturnProductDetailsDTO toDTO(Product product);

    }