package br.com.fourcamp.fourstore.service;

import br.com.fourcamp.fourstore.dto.request.CreateStockDTO;
import br.com.fourcamp.fourstore.dto.request.CreateTransactionDTO;
import br.com.fourcamp.fourstore.entities.Product;
import br.com.fourcamp.fourstore.entities.Stock;
import br.com.fourcamp.fourstore.exceptions.*;
import br.com.fourcamp.fourstore.repositories.ProductRepository;
import br.com.fourcamp.fourstore.repositories.StockRepository;
import br.com.fourcamp.fourstore.util.CartMethods;
import br.com.fourcamp.fourstore.util.SkuValidations;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    public String addProductsToStock(String sku, Integer quantity) {
        Stock stock = findBySku(sku);
        stock.setQuantity(stock.getQuantity() + quantity);
        stockRepository.save(stock);
        return "O produto com a sku " + sku + " agora possui " + stock.getQuantity() + " unidades no estoque";
    }


    public String updateProductPrice(String sku, BigDecimal buyPrice, BigDecimal sellPrice) {
        Stock stock = findBySku(sku);
        stock.getProduct().updatePrices(buyPrice, sellPrice);
        if (profitLessThan25Percent(stock) || sellPriceEqualsZero(stock)) {
            throw new InvalidSellValueException();
        }
        stockRepository.save(stock);
        return "Preços do produto com a sku " + sku + " atualizados. Novos preços: compra: " + stock.getProduct()
                .getBuyPrice() + ", venda: " + stock.getProduct().getSellPrice();
    }

    public List<Stock> listAll() {
        return stockRepository.findAll();
    }

   public String delete(String sku) {
        stockRepository.deleteById(findBySku(sku).getId());
        return "Estoque do produto com a sku " + sku + "removido";
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
        if (profitLessThan25Percent(createStockDTO) || sellPriceEqualsZero(createStockDTO)) {
            throw new InvalidSellValueException();
        } else if (SkuValidations.validateSku(createStockDTO.getProduct().getSku()).equals(false)) {
            throw new InvalidSkuException();
        }
    }
    private boolean sellPriceEqualsZero(Stock stock) {
        return stock.getProduct().getSellPrice().compareTo(BigDecimal.ZERO) < 0;
    }

    private boolean sellPriceEqualsZero(CreateStockDTO createStockDTO) {
        return createStockDTO.getProduct().getSellPrice().compareTo(BigDecimal.ZERO) < 0;
    }

    private boolean profitLessThan25Percent(Stock stock) {
        return stock.getProduct().getSellPrice().compareTo(stock.getProduct().getBuyPrice().multiply(BigDecimal.valueOf(1.25))) < 0;
    }

    private boolean profitLessThan25Percent(CreateStockDTO createStockDTO) {
        return createStockDTO.getProduct().getSellPrice().compareTo(createStockDTO.getProduct().getBuyPrice().multiply(BigDecimal.valueOf(1.25))) < 0;
    }
}