package br.com.fourcamp.fourstore.FourStore.mapper;

import br.com.fourcamp.fourstore.FourStore.dto.request.CreateProductDTO;
import br.com.fourcamp.fourstore.FourStore.dto.response.ReturnProductDTO;
import br.com.fourcamp.fourstore.FourStore.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {

        ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

        Product toModel(CreateProductDTO createProductDTO);

        //Destrinchar sku Para o return Product Details

        ReturnProductDTO toDTO(Product product);

}
