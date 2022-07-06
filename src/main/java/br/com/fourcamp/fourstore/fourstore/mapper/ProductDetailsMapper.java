package br.com.fourcamp.fourstore.fourstore.mapper;

import br.com.fourcamp.fourstore.fourstore.dto.response.ReturnProductDetailsDTO;
import br.com.fourcamp.fourstore.fourstore.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductDetailsMapper {

    ProductDetailsMapper INSTANCE = Mappers.getMapper(ProductDetailsMapper.class);

    ReturnProductDetailsDTO toDTO(Product product);

    }