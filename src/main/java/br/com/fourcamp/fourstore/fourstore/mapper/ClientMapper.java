package br.com.fourcamp.fourstore.fourstore.mapper;

import br.com.fourcamp.fourstore.fourstore.dto.request.CreateClientDTO;
import br.com.fourcamp.fourstore.fourstore.dto.response.ReturnClientDTO;
import br.com.fourcamp.fourstore.fourstore.entities.Client;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientMapper INSTANCE = Mappers.getMapper(ClientMapper.class);

    Client toModel(CreateClientDTO createClientDTO);
    ReturnClientDTO toDTO(Client client);


}






