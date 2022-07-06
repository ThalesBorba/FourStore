package br.com.fourcamp.fourstore.fourstore.mapper;

import br.com.fourcamp.fourstore.fourstore.dto.request.CreateProductDTO;
import br.com.fourcamp.fourstore.fourstore.dto.response.ReturnProductDTO;
import br.com.fourcamp.fourstore.fourstore.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {

        ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);
        Product toModel(CreateProductDTO createProductDTO);
        ReturnProductDTO toDTO(Product product);

}
