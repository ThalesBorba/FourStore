package br.com.fourcamp.fourstore.fourstore.mapper;

import br.com.fourcamp.fourstore.fourstore.dto.request.CreateTransactionDTO;
import br.com.fourcamp.fourstore.fourstore.dto.response.ReturnTransactionDTO;
import br.com.fourcamp.fourstore.fourstore.entities.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);
    Transaction toModel(CreateTransactionDTO createTransactionDTO);

    @Mapping(target = "clientName", source = "transaction.client.name")
    @Mapping(target = "clientCpf", source = "transaction.client.cpf")
    ReturnTransactionDTO toDTO(Transaction transaction);

}