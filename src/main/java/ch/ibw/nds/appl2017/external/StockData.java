package ch.ibw.nds.appl2017.external;

import ch.ibw.nds.appl2017.model.ComparisonInput;
import ch.ibw.nds.appl2017.model.Stock;

import java.util.ArrayList;

/**
 * Created by dieterbiedermann on 18.12.17.
 */
public class  StockData {

    private StockData(){

    }

    public static StockData create(){
        return new StockData();
    }

    public ArrayList<Stock> getAllStocks(ComparisonInput comparisonInput){
        return new ArrayList<>();
    }

    public Stock getStock(){
        return Stock.createStock("");
    }

}
