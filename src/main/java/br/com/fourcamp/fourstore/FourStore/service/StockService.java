package br.com.fourcamp.fourstore.FourStore.service;

import br.com.fourcamp.fourstore.FourStore.dto.request.CreateStockDTO;
import br.com.fourcamp.fourstore.FourStore.dto.request.CreateTransactionDTO;
import br.com.fourcamp.fourstore.FourStore.dto.response.MessageResponseDTO;
import br.com.fourcamp.fourstore.FourStore.dto.response.ReturnProductDetailsDTO;
import br.com.fourcamp.fourstore.FourStore.dto.response.ReturnStockDTO;
import br.com.fourcamp.fourstore.FourStore.entities.Product;
import br.com.fourcamp.fourstore.FourStore.repositories.ProductRepository;
import br.com.fourcamp.fourstore.FourStore.util.CartMethods;
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

    private ProductRepository productRepository = ProductService.productRepository;

    private StockMapper stockMapper = StockMapper.INSTANCE;

    @Autowired
    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public MessageResponseDTO createStock(CreateStockDTO createStockDTO) throws InvalidParametersException {
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
            ReturnStockDTO stockToUpdate = findBySku(stock.getProduct().getSku());
            Integer newQuantity = stockToUpdate.getQuantity() - stock.getQuantity();
            if (newQuantity < 0) {
                throw new StockInsufficientException();
            } else {
                stockToUpdate.setQuantity(newQuantity);
                Stock stockToSave = stockMapper.toModel(stockToUpdate);
                stockRepository.save(stockToSave);
            }
        }
    }

    public MessageResponseDTO updateBySku(String sku, CreateStockDTO createStockDTO) throws StockNotFoundException {
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

    private Stock setStock(CreateStockDTO createStockDTO) {
        Stock stock = stockMapper.toModel(createStockDTO);
        return stockRepository.save(stock);
    }

    public ReturnStockDTO findBySku(String sku) throws StockNotFoundException {
        Stock stock = verifyIfExists(sku);
        return stockMapper.toDTO(stock);
    }
}

/*

    public MessageResponseDTO updateBySku(String sku, CreateProductDTO createProductDTO) throws ProductNotFoundException,
            InvalidSellValueException, InvalidSkuException {
        verifyIfExists(sku);
        CreateProductDTO validProduct = validProduct(createProductDTO);
        Product updatedProduct = productMapper.toModel(validProduct);
        productRepository.save(updatedProduct);
        return createMessageResponse(updatedProduct.getSku(), "Updated");
    }

    private Product setProduct(CreateProductDTO createProductDTO) throws InvalidSellValueException, InvalidSkuException {
        CreateProductDTO validProduct = validProduct(createProductDTO);
        Product productToSave = productMapper.toModel(validProduct);
        return productRepository.save(productToSave);
    }

    public ReturnProductDTO findBySku(String sku) throws ProductNotFoundException {
        Product product = verifyIfExists(sku);
        return productMapper.toDTO(product);
    }

    private CreateProductDTO validProduct(CreateProductDTO createProductDTO) throws InvalidSellValueException,
            InvalidSkuException {
        if (createProductDTO.getSellPrice() * 1.25 <= createProductDTO.getBuyPrice() ||
                createProductDTO.getSellPrice() < 0) {
            throw new InvalidSellValueException();
        } else if (SkuValidations.validateSku(createProductDTO.getSku()).equals(false)) {
            throw new InvalidSkuException();
        } else {
            return createProductDTO;
        }
    } */