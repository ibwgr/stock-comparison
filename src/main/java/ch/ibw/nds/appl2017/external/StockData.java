package ch.ibw.nds.appl2017.external;

import ch.ibw.nds.appl2017.model.ComparisonInput;
import ch.ibw.nds.appl2017.model.Stock;

import java.util.Date;
import java.util.List;

public class  StockData {

    private AlphaVantage alphaVantage = AlphaVantage.create();

    private StockData(){

    }

    public static StockData create(){
        return new StockData();
    }

    public List<Stock> getAllStocks(ComparisonInput comparisonInput){
        comparisonInput.getStocks().forEach(stock -> getStock(stock, comparisonInput.getDateFrom(), comparisonInput.getDateTo()));
        return comparisonInput.getStocks();
    }

    public void getStock(Stock stock, Date dateFrom, Date dateTo){
        alphaVantage.getStock(stock, dateFrom, dateTo);
    }

}
