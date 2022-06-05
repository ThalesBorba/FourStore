package br.com.fourcamp.fourstore.FourStore.mapper;

import br.com.fourcamp.fourstore.FourStore.dto.request.CreateTransactionDTO;
import br.com.fourcamp.fourstore.FourStore.dto.response.ReturnTransactionDTO;
import br.com.fourcamp.fourstore.FourStore.entities.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);
    Transaction toModel(CreateTransactionDTO createTransactionDTO);
    ReturnTransactionDTO toDTO(Transaction transaction);

}
