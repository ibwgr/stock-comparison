package ch.ibw.nds.appl2017.controller;

import ch.ibw.nds.appl2017.external.StockData;
import ch.ibw.nds.appl2017.model.ComparisonInput;
import ch.ibw.nds.appl2017.model.ComparisonOutput;
import ch.ibw.nds.appl2017.model.Stock;

import java.util.Collections;
import java.util.List;

public abstract class ComparisonTemplate {

    public ComparisonTemplate() {

    }

    public ComparisonOutput compare(ComparisonInput comparisonInput) {


        return ComparisonOutput.createComparisonOutput(Collections.EMPTY_LIST);
    }

    private List<Stock> getAllStocks(ComparisonInput comparisonInput) {
        StockData stockData = StockData.create();
        return stockData.getAllStocks(comparisonInput);
    }

    public ComparisonOutput calculate(List<Stock> stocks) {
        return null;
    }

}
