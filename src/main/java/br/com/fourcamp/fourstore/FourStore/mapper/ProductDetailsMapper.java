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
    /*
    @Mapping(target = "brand", source = "BrandEnum.getDescriptionByKey(product.getSku().substring(0, 3))")
   @Mapping(target = "size", source = "SizeEnum.getDescriptionByKey(product.getSku().substring(3, 5))")
   @Mapping(target = "category", source = "CategoryEnum.getDescriptionByKey(product.getSku().substring(5, 7))")
   @Mapping(target = "season", source = "SeasonEnum.getDescriptionByKey(product.getSku().substring(7, 9))")
   @Mapping(target = "department", source = "DepartmentEnum.getDescriptionByKey(product.getSku().substring(9, 11))")
   @Mapping(target = "type", source = "TypeOfMerchandiseEnum.getDescriptionByKey(product.getSku().substring(11, 14))")
   @Mapping(target = "color", source = "ColorEnum.getDescriptionByKey(product.getSku().substring(14, 16))")*/
    ReturnProductDetailsDTO toDTO(Product product);

    }