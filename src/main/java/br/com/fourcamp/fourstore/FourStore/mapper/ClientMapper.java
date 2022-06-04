package br.com.fourcamp.fourstore.FourStore.mapper;

import br.com.fourcamp.fourstore.FourStore.dto.response.ReturnClientDTO;
import br.com.fourcamp.fourstore.FourStore.entities.Client;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    ReturnClientDTO toDTO(Client client);


}






