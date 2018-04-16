package edu.itba.paw.jimi;

import edu.itba.paw.jimi.Exceptions.LessStockAvailableException;

import java.util.HashMap;
import java.util.Map;

public class Stock {

    private Map<Product, Integer> stockMap;
    private static final Stock singleton = new Stock();

    private Stock() {
        stockMap = new HashMap<Product, Integer>();
    }

    public Stock getInstance() {
        return singleton;
    }

    /**
     *
     * @param val refers to number of items that should be increased.
     * @param product  refers to the id of the product.
     * @return boolean true if the value is setted. False other.
     */
    public boolean incrementStock(Integer val, Product product) {
        Integer stockVal = stockMap.get(product);

        if(val <= 0 || stockVal == null) {
            throw new IllegalArgumentException();
        }
        stockMap.put(product, stockVal + val);
        return true;
    }

    public boolean decrementStock(Product product, Integer val) throws LessStockAvailableException {
        Integer stockVal = stockMap.get(product);

        if(stockVal == null) {
            throw new IllegalArgumentException();
        }
        if(stockVal > val) {
            throw new LessStockAvailableException();
        }
        stockMap.put(product, stockVal - val);
        return true;
    }

}
