package br.com.fourcamp.fourstore.FourStore.service;

import br.com.fourcamp.fourstore.FourStore.dto.request.CreateStockDTO;
import br.com.fourcamp.fourstore.FourStore.dto.request.CreateTransactionDTO;
import br.com.fourcamp.fourstore.FourStore.dto.response.MessageResponseDTO;
import br.com.fourcamp.fourstore.FourStore.dto.response.ReturnStockDTO;
import br.com.fourcamp.fourstore.FourStore.entities.Cart;
import br.com.fourcamp.fourstore.FourStore.entities.Stock;
import br.com.fourcamp.fourstore.FourStore.exceptions.InvalidParametersException;
import br.com.fourcamp.fourstore.FourStore.exceptions.StockInsufficientException;
import br.com.fourcamp.fourstore.FourStore.exceptions.StockNotFoundException;
import br.com.fourcamp.fourstore.FourStore.mapper.StockMapper;
import br.com.fourcamp.fourstore.FourStore.repositories.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StockService {

    private StockRepository stockRepository;

    private StockMapper stockMapper;

    @Autowired
    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public MessageResponseDTO createStock(CreateStockDTO createStockDTO) throws InvalidParametersException {
        if (createStockDTO.getQuantity() <= 0) {
            throw new InvalidParametersException();
            //verificar se tem em produtos
            //ver se esse produto já existe no estoque
        } else {
            Stock savedStock = setStock(createStockDTO);
            return createMessageResponse(savedStock.getId(), "Criado");
        }
    }

    public void updateByTransaction(CreateTransactionDTO createTransactionDTO) throws StockNotFoundException,
            InvalidParametersException, StockInsufficientException {
        List<Stock> updatedStockList = Cart.updateStock(createTransactionDTO);
        for (Stock stock : updatedStockList) {
            ReturnStockDTO stocktoUpdate = findById(stock.getProduct().getSku());
            Integer newQuantity = stocktoUpdate.getQuantity() - stock.getQuantity();
            if (newQuantity < 0) {
                throw new StockInsufficientException();
            } else {
                stocktoUpdate.setQuantity(newQuantity);
                Stock stockToSave = stockMapper.toModel(stocktoUpdate);
                stockRepository.save(stockToSave);
            }
        }
    }

    public MessageResponseDTO updateById(String sku, CreateStockDTO createStockDTO) throws StockNotFoundException
    {
        verifyIfExists(sku);
        ReturnStockDTO currentStock = findById(sku);
        Integer finalQuantity = currentStock.getQuantity() + createStockDTO.getQuantity();
        Stock updatedStock = setStock(new CreateStockDTO(createStockDTO.getProduct(), finalQuantity));
        stockRepository.save(updatedStock);
        return createMessageResponse(updatedStock.getId(), "Updated");
    }

    public List<ReturnStockDTO> listAll() {
        List<Stock> allStocks = stockRepository.findAll();
        List<ReturnStockDTO> returnStockDTOList = new ArrayList<>();
        for (Stock stock : allStocks) {
            ReturnStockDTO returnStockDTO = stockMapper.toDTO(stock);
            returnStockDTOList.add(returnStockDTO);
        }
        return returnStockDTOList;
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

    private Stock setStock(CreateStockDTO createStockDTO) {
        Stock stock = stockMapper.toModel(createStockDTO);
        return stockRepository.save(stock);
    }

    public ReturnStockDTO findById(String sku) throws StockNotFoundException {
        List<Stock> stockList = stockRepository.findAll();
        for(Stock stock : stockList) {
            if (stock.getProduct().getSku().equals(sku)) {
                return stockMapper.toDTO(stock);
            }
        }throw new StockNotFoundException(sku);
    }




}