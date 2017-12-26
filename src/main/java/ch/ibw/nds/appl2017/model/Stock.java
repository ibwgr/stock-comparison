package ch.ibw.nds.appl2017.model;

import javax.validation.constraints.Pattern;
import java.util.List;

public class Stock {

    @Pattern(regexp = "^[A-Z]{4,5}$", message = "Invalid stock symbol length (not between 4 and 5)")
    private String symbol;

    private List<TimeSerie> timeSeries;


    private Stock(String symbol, List<TimeSerie> timeSeries) {
        this.symbol = symbol;
        this.timeSeries = timeSeries;
    }

    public static Stock createStock (String symbol, List<TimeSerie> timeSeries) {
        return new Stock(symbol,timeSeries);
    }
    public static Stock createStock (String symbol) {
        return createStock(symbol,null);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Stock: ");
        return stringBuilder.append(this.symbol).toString();
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public List<TimeSerie> getTimeSeries() {
        return timeSeries;
    }

    public void setTimeSeries(List<TimeSerie> timeSeries) {
        this.timeSeries = timeSeries;
    }
}
