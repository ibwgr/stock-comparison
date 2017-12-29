package ch.ibw.nds.appl2017.controller;

import ch.ibw.nds.appl2017.external.StockData;
import ch.ibw.nds.appl2017.model.ComparisonInput;
import ch.ibw.nds.appl2017.model.ComparisonOutput;
import ch.ibw.nds.appl2017.model.Stock;

import java.util.List;

public abstract class ComparisonTemplate {

    public ComparisonTemplate() {

    }

    public ComparisonOutput compare(ComparisonInput comparisonInput) {
        List<Stock> stocks = getAllStocks(comparisonInput);
        return calculate(stocks);
    }

    private List<Stock> getAllStocks(ComparisonInput comparisonInput) {
        StockData stockData = StockData.create();
        return stockData.getAllStocks(comparisonInput);
    }

    public ComparisonOutput calculate(List<Stock> stocks) {
        return null;
    }

}
