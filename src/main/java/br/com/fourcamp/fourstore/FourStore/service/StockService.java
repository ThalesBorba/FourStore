package br.com.fourcamp.fourstore.FourStore.service;

import br.com.fourcamp.fourstore.FourStore.dto.request.CreateStockDTO;
import br.com.fourcamp.fourstore.FourStore.dto.response.MessageResponseDTO;
import br.com.fourcamp.fourstore.FourStore.entities.Stock;
import br.com.fourcamp.fourstore.FourStore.exceptions.ProductNotFoundException;
import br.com.fourcamp.fourstore.FourStore.repositories.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {

    private StockRepository stockRepository;

    @Autowired
    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public MessageResponseDTO createStock(Stock stock) {
        //validações
        Stock savedStock = setStock(stock);
        return createMessageResponse(savedStock.getId(), "Criado");
    }

    public MessageResponseDTO updateById(Long id, Stock stock) throws ProductNotFoundException {
        //validações
        verifyIfExists(id);
        Stock updatedStock = setStock(stock);
        return createMessageResponse(updatedStock.getId(), "Updated");
    }

    public List<Stock> listAll() {
        List<Stock> allStocks = stockRepository.findAll();
        return allStocks;
    }

    public void delete(Long id) throws ProductNotFoundException {
        verifyIfExists(id);
        stockRepository.deleteById(id);
    }

    private MessageResponseDTO createMessageResponse(Long id, String s) {
        return MessageResponseDTO.builder().message(s + "esotque com a id" + id).build();
    }

    private Stock verifyIfExists(Long id) throws ProductNotFoundException {
        //trocar por find by product
        return stockRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException());
    }

    private Stock setStock(Stock stock) {
        return stockRepository.save(stock);
    }

    public Stock findById(Long id) throws ProductNotFoundException {
        Stock stock = verifyIfExists(id);
        return stock;
    }

    private CreateStockDTO validStock(CreateStockDTO createStockDTO) {
        //validações
        return createStockDTO;
    }


}
