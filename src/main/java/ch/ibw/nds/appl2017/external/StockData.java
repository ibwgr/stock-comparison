package ch.ibw.nds.appl2017.external;

import ch.ibw.nds.appl2017.model.ComparisonInput;
import ch.ibw.nds.appl2017.model.Stock;

import java.util.List;

public class  StockData {

    private StockDataService stockDataService;

    private StockData(StockDataService stockDataService){
        this.stockDataService = stockDataService;
    }

    public static StockData create(){
        return new StockData(AlphaVantage.create());
    }

    public static StockData create(StockDataService stockDataService){
        return new StockData(stockDataService);
    }

    public List<Stock> getAllStocks(ComparisonInput comparisonInput){
        comparisonInput.getStocks().forEach(stock -> stockDataService.getStock(stock, comparisonInput.getDateFrom(), comparisonInput.getDateTo()));
        return comparisonInput.getStocks();
    }

}
