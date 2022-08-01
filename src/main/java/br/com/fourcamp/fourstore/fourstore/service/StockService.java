package br.com.fourcamp.fourstore.fourstore.service;

import br.com.fourcamp.fourstore.fourstore.dto.request.CreateStockDTO;
import br.com.fourcamp.fourstore.fourstore.dto.request.CreateTransactionDTO;
import br.com.fourcamp.fourstore.fourstore.dto.response.MessageResponseDTO;
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

    public MessageResponseDTO createStock(CreateStockDTO createStockDTO) throws InvalidParametersException,
            InvalidSellValueException, InvalidSkuException, ProductAlreadyInStockException {
        if (createStockDTO.getQuantity() <= 0) {
            throw new InvalidParametersException();
        }
        if (Boolean.TRUE.equals(verifyIfStockOfProductExists(createStockDTO.getProduct()))) {
            throw new ProductAlreadyInStockException();
        }
        Stock savedStock = setStock(createStockDTO);
        return createMessageResponse(savedStock.getId(), "Criado ");

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

    public void updateByTransaction(CreateTransactionDTO createTransactionDTO) throws StockInsufficientException {
        List<Stock> updatedStockList = CartMethods.updateStock(stockRepository.findAll(), createTransactionDTO);
        for (Stock stock : updatedStockList) {
            if (stock.getQuantity() < 0) {
                throw new StockInsufficientException();
            } else {
                stockRepository.save(stock);
            }
        }
    }

    public MessageResponseDTO addProductsToStock(String sku, Integer quantity) throws ProductNotFoundException {
        Stock stock = findBySku(sku);
        stock.setQuantity(stock.getQuantity() + quantity);
        stockRepository.save(stock);
        return createMessageResponse(stock.getId(), "Adicionados produtos ao ");
    }


    public MessageResponseDTO updateProductPrice(String sku, Double buyPrice, Double sellPrice) throws ProductNotFoundException {
        Stock stock = findBySku(sku);
        stock.getProduct().setBuyPrice(buyPrice);
        stock.getProduct().setSellPrice(sellPrice);
        stockRepository.save(stock);
        return createMessageResponse(stock.getId(), "Atualizado ");
    }

    public List<Stock> listAll() {
        return stockRepository.findAll();
    }

    public MessageResponseDTO delete(String sku) throws ProductNotFoundException {
        Integer id = findBySku(sku).getId();
        stockRepository.deleteById(id);
        return createMessageResponse(id, "Deletado ");
    }

    private MessageResponseDTO createMessageResponse(Integer id, String s) {
        return MessageResponseDTO.builder().message(s + "estoque com a id " + id).build();
    }

    private Stock setStock(CreateStockDTO createStockDTO) throws InvalidSellValueException, InvalidSkuException {
        validateStockProduct(createStockDTO);
        Stock stock = new Stock();
        BeanUtils.copyProperties(createStockDTO, stock);
        return stockRepository.save(stock);
    }

    public Stock findBySku(String sku) throws ProductNotFoundException {
        Product product = productRepository.findBySku(sku).orElseThrow(() -> new ProductNotFoundException(sku));
        return stockRepository.findByProduct(product);
    }

    private void validateStockProduct(CreateStockDTO createStockDTO) throws InvalidSellValueException,
            InvalidSkuException {
        if (createStockDTO.getProduct().getSellPrice() <= createStockDTO.getProduct().getBuyPrice() * 1.25 ||
                createStockDTO.getProduct().getSellPrice() < 0) {
            throw new InvalidSellValueException();
        } else if (SkuValidations.validateSku(createStockDTO.getProduct().getSku()).equals(false)) {
            throw new InvalidSkuException();
        }
    }

}