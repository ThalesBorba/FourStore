package br.com.fourcamp.fourstore.FourStore.service;

import br.com.fourcamp.fourstore.FourStore.dto.response.MessageResponseDTO;
import br.com.fourcamp.fourstore.FourStore.entities.Cart;
import br.com.fourcamp.fourstore.FourStore.entities.Stock;
import br.com.fourcamp.fourstore.FourStore.entities.Transaction;
import br.com.fourcamp.fourstore.FourStore.exceptions.InvalidParametersException;
import br.com.fourcamp.fourstore.FourStore.exceptions.StockInsufficientException;
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

    public MessageResponseDTO createStock(Stock stock) throws InvalidParametersException {
        if (stock.getQuantity() <= 0) {
            throw new InvalidParametersException();
        } else {
            Stock savedStock = setStock(stock);
            return createMessageResponse(savedStock.getId(), "Criado");
        }
    }

    public void updateByTransaction(Transaction transaction) throws StockNotFoundException,
            InvalidParametersException, StockInsufficientException {
        List<Stock> updatedStockList = Cart.updateStock(transaction);
        for (Stock stock : updatedStockList) {
            Stock stocktoUpdate = findById(stock.getProduct().getSku());
            Integer newQuantity = stocktoUpdate.getQuantity() - stock.getQuantity();
            if (newQuantity < 0) {
                throw new StockInsufficientException();
            } else {
                stocktoUpdate.setQuantity(newQuantity);
                stockRepository.save(stocktoUpdate);
            }
        }
    }

    public MessageResponseDTO updateById(String sku, Stock stock) throws StockNotFoundException
            {
        verifyIfExists(sku);
        Stock currentStock = findById(sku);
        Integer finalQuantity;
        finalQuantity = currentStock.getQuantity() + stock.getQuantity();
        Stock updatedStock = setStock(new Stock(stock.getProduct(), finalQuantity));
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




}
