package br.com.fourcamp.fourstore.FourStore.service;

import br.com.fourcamp.fourstore.FourStore.dto.request.CreateStockDTO;
import br.com.fourcamp.fourstore.FourStore.dto.request.CreateTransactionDTO;
import br.com.fourcamp.fourstore.FourStore.dto.response.MessageResponseDTO;
import br.com.fourcamp.fourstore.FourStore.dto.response.ReturnProductDetailsDTO;
import br.com.fourcamp.fourstore.FourStore.dto.response.ReturnStockDTO;
import br.com.fourcamp.fourstore.FourStore.entities.Product;
import br.com.fourcamp.fourstore.FourStore.exceptions.*;
import br.com.fourcamp.fourstore.FourStore.repositories.ProductRepository;
import br.com.fourcamp.fourstore.FourStore.util.CartMethods;
import br.com.fourcamp.fourstore.FourStore.entities.Stock;
import br.com.fourcamp.fourstore.FourStore.mapper.StockMapper;
import br.com.fourcamp.fourstore.FourStore.repositories.StockRepository;
import br.com.fourcamp.fourstore.FourStore.util.SkuValidations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StockService {

    private StockRepository stockRepository;

    private ProductRepository productRepository = ProductService.productRepository;

    private StockMapper stockMapper = StockMapper.INSTANCE;

    @Autowired
    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public MessageResponseDTO createStock(CreateStockDTO createStockDTO) throws InvalidParametersException,
            InvalidSellValueException, InvalidSkuException {
        if ((createStockDTO.getQuantity() <= 0) || verifyIfStockOfProductExists(createStockDTO.getProduct())) {
            throw new InvalidParametersException();
        } else {
            Stock savedStock = setStock(createStockDTO);
            return createMessageResponse(savedStock.getId(), "Criado ");
        }
    }

    private Boolean verifyIfStockOfProductExists(Product product) {
        List<Stock> allStocks = stockRepository.findAll();
        for (Stock stock : allStocks) {
            if (stock.getProduct().equals(product)) {
                return true;
            }
        }
        return false;
    };

    public void updateByTransaction(CreateTransactionDTO createTransactionDTO) throws StockNotFoundException,
            InvalidParametersException, StockInsufficientException {
        List<Stock> stockListToBeUpdated = stockRepository.findAll();
        List<Stock> updatedStockList = CartMethods.updateStock(stockListToBeUpdated, createTransactionDTO);
        for (Stock stock : updatedStockList) {
            Stock stockToUpdate = verifyIfExists(stock.getProduct().getSku());
            if (stockToUpdate.getQuantity() < 0) {
                throw new StockInsufficientException();
            } else {
                stockToUpdate.getProduct().setBuyPrice(stock.getProduct().getBuyPrice());
                stockToUpdate.getProduct().setSellPrice(stock.getProduct().getSellPrice());
                stockRepository.save(stockToUpdate);
            }
        }
    }

    public MessageResponseDTO updateBySku(String sku, CreateStockDTO createStockDTO) throws StockNotFoundException, InvalidSellValueException, InvalidSkuException {
        validateStockProduct(createStockDTO);
        ReturnStockDTO currentStock = findBySku(sku);
        Integer finalQuantity = currentStock.getQuantity() + createStockDTO.getQuantity();
        currentStock.setQuantity(finalQuantity);
        Stock updatedStock = stockMapper.toModel(currentStock);
        updatedStock.getProduct().setSellPrice(createStockDTO.getProduct().getSellPrice());
        updatedStock.getProduct().setBuyPrice(createStockDTO.getProduct().getBuyPrice());
        stockRepository.save(updatedStock);
        return createMessageResponse(updatedStock.getId(), "Atualizado ");
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

    public MessageResponseDTO delete(String sku) throws StockNotFoundException {
        Stock stockToDelete = verifyIfExists(sku);
        Integer id = stockToDelete.getId();
        stockRepository.deleteById(stockToDelete.getId());
        return createMessageResponse(id, "Deletado ");
    }

    private MessageResponseDTO createMessageResponse(Integer id, String s) {
        return MessageResponseDTO.builder().message(s + "estoque com a id " + id).build();
    }

    private Stock verifyIfExists(String sku) throws StockNotFoundException {
        List<Stock> allStocks = stockRepository.findAll();
        Stock stockToReturn = null;
        for (Stock stock : allStocks) {
            if (stock.getProduct().getSku().equals(sku)) {
                stockToReturn = stock;
            }
        }
        if (stockToReturn != null) {
            return stockToReturn;
        } else {
            throw new StockNotFoundException(sku);
        }
    }

    private Stock setStock(CreateStockDTO createStockDTO) throws InvalidSellValueException, InvalidSkuException {
        validateStockProduct(createStockDTO);
        Stock stock = stockMapper.toModel(createStockDTO);
        return stockRepository.save(stock);
    }

    public ReturnStockDTO findBySku(String sku) throws StockNotFoundException {
        Stock stock = verifyIfExists(sku);
        return stockMapper.toDTO(stock);
    }

    private CreateStockDTO validateStockProduct(CreateStockDTO createStockDTO) throws InvalidSellValueException,
            InvalidSkuException {
        if (createStockDTO.getProduct().getSellPrice() <= createStockDTO.getProduct().getBuyPrice() * 1.25 ||
                createStockDTO.getProduct().getSellPrice() < 0) {
            throw new InvalidSellValueException();
        } else if (SkuValidations.validateSku(createStockDTO.getProduct().getSku()).equals(false)) {
            throw new InvalidSkuException();
        } else {
            return createStockDTO;
        }
    }

}