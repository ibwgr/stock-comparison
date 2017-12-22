package ch.ibw.nds.appl2017.model;

import java.util.List;

public class Stock {
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
