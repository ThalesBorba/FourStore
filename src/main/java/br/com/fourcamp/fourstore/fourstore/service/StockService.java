package br.com.fourcamp.fourstore.fourstore.service;

import br.com.fourcamp.fourstore.fourstore.dto.request.CreateStockDTO;
import br.com.fourcamp.fourstore.fourstore.dto.request.CreateTransactionDTO;
import br.com.fourcamp.fourstore.fourstore.entities.Product;
import br.com.fourcamp.fourstore.fourstore.entities.Stock;
import br.com.fourcamp.fourstore.fourstore.exceptions.*;
import br.com.fourcamp.fourstore.fourstore.repositories.ProductRepository;
import br.com.fourcamp.fourstore.fourstore.repositories.StockRepository;
import br.com.fourcamp.fourstore.fourstore.util.CartMethods;
import br.com.fourcamp.fourstore.fourstore.util.SkuValidations;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService {

    @Autowired
    private StockRepository stockRepository;
    @Autowired
    private ProductRepository productRepository;

    public Stock createStock(CreateStockDTO createStockDTO) {
        if (createStockDTO.getQuantity() <= 0) {
            throw new InvalidParametersException();
        }
        if (Boolean.TRUE.equals(verifyIfStockOfProductExists(createStockDTO.getProduct()))) {
            throw new ProductAlreadyInStockException();
        }
        return setStock(createStockDTO);

    }

    private Boolean verifyIfStockOfProductExists(Product product) {
        List<Stock> allStocks = stockRepository.findAll();
        for (Stock stock : allStocks) {
            if (stock.getProduct().equals(product)) {
                return true;
            }
        }
        return false;
    }

    public void updateByTransaction(CreateTransactionDTO createTransactionDTO) {
        List<Stock> updatedStockList = CartMethods.updateStock(stockRepository.findAll(), createTransactionDTO);
        for (Stock stock : updatedStockList) {
            if (stock.getQuantity() < 0) {
                throw new StockInsufficientException();
            } else {
                stockRepository.save(stock);
            }
        }
    }

    public void addProductsToStock(String sku, Integer quantity) {
        Stock stock = findBySku(sku);
        stock.setQuantity(stock.getQuantity() + quantity);
        stockRepository.save(stock);
    }


    public void updateProductPrice(String sku, Double buyPrice, Double sellPrice) {
        Stock stock = findBySku(sku);
        stock.getProduct().setBuyPrice(buyPrice);
        stock.getProduct().setSellPrice(sellPrice);
        if (stock.getProduct().getSellPrice() <= stock.getProduct().getBuyPrice() * 1.25 ||
                stock.getProduct().getSellPrice() < 0) {
            throw new InvalidSellValueException();
        }
        stockRepository.save(stock);
    }

    public List<Stock> listAll() {
        return stockRepository.findAll();
    }

    public void delete(String sku) {
        Integer id = findBySku(sku).getId();
        stockRepository.deleteById(id);
    }

    private Stock setStock(CreateStockDTO createStockDTO){
        validateStockProduct(createStockDTO);
        Stock stock = new Stock();
        BeanUtils.copyProperties(createStockDTO, stock);
        return stockRepository.save(stock);
    }

    public Stock findBySku(String sku) {
        Product product = productRepository.findBySku(sku).orElseThrow(() -> new ProductNotFoundException(sku));
        return stockRepository.findByProduct(product);
    }

    private void validateStockProduct(CreateStockDTO createStockDTO) {
        if (createStockDTO.getProduct().getSellPrice() <= createStockDTO.getProduct().getBuyPrice() * 1.25 ||
                createStockDTO.getProduct().getSellPrice() < 0) {
            throw new InvalidSellValueException();
        } else if (SkuValidations.validateSku(createStockDTO.getProduct().getSku()).equals(false)) {
            throw new InvalidSkuException();
        }
    }

}