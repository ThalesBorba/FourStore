package br.com.fourcamp.fourstore.FourStore.service;

import br.com.fourcamp.fourstore.FourStore.dto.request.CreateStockDTO;
import br.com.fourcamp.fourstore.FourStore.dto.response.MessageResponseDTO;
import br.com.fourcamp.fourstore.FourStore.entities.Product;
import br.com.fourcamp.fourstore.FourStore.entities.Stock;
import br.com.fourcamp.fourstore.FourStore.exceptions.StockNotFoundException;
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
        Stock savedStock = setStock(stock);
        return createMessageResponse(savedStock.getId(), "Criado");
    }

    public MessageResponseDTO updateById(String sku, Stock stock) throws StockNotFoundException {
        //validações
        verifyIfExists(sku);
        Stock updatedStock = setStock(stock);
        return createMessageResponse(updatedStock.getId(), "Updated");
    }

    public List<Stock> listAll() {
        List<Stock> allStocks = stockRepository.findAll();
        return allStocks;
    }

    public void delete(String sku) throws StockNotFoundException {
        verifyIfExists(sku);
        stockRepository.deleteById(sku);
    }

    private MessageResponseDTO createMessageResponse(Long id, String s) {
        return MessageResponseDTO.builder().message(s + "estoque com a id" + id).build();
    }

    private Stock verifyIfExists(String sku) throws StockNotFoundException {
        //trocar por find by product

        return stockRepository.findById(sku)
                .orElseThrow(() -> new StockNotFoundException(sku));
    }

    private Stock setStock(Stock stock) {
        return stockRepository.save(stock);
    }

    public Stock findById(String sku) throws StockNotFoundException {
        List<Stock> stockList = stockRepository.findAll();
        for(Stock stock : stockList) {
            if (stock.getProduct().getSku().equals(sku)) {
                return stock;
            }
        }throw new StockNotFoundException(sku);
    }

    private CreateStockDTO validStock(CreateStockDTO createStockDTO) {
        //verificar se a quantidade pedida é menor que a em estoque
        return createStockDTO;
    }


}
