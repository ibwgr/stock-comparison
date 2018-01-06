package ch.ibw.nds.appl2017.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComparisonOutputElement {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComparisonOutputElement.class);

    private String stockSymbol;
    private String stockSymbol2;
    private Double resultValue;

    private ComparisonOutputElement(String stockSymbol, String stockSymbol2, Double resultValue) {
        this.stockSymbol = stockSymbol;
        this.stockSymbol2 = stockSymbol2;
        this.resultValue = resultValue;
        LOGGER.debug(this.toString());
    }

    public static ComparisonOutputElement createComparisonOutputElement(Stock stock, Double resultValue) {
        return new ComparisonOutputElement(stock.getSymbol(), null, resultValue);
    }

    public static ComparisonOutputElement createComparisonOutputElement(Stock stock, Stock stock2, Double resultValue) {
        return new ComparisonOutputElement(stock.getSymbol(), stock2.getSymbol(), resultValue);
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Comparison-Output Element: ");
        stringBuilder.append(this.stockSymbol);
        stringBuilder.append(": ");
        stringBuilder.append(this.resultValue);
        return stringBuilder.toString();
    }

    public String getStockSymbol() {
        return stockSymbol;
    }

    public void setStockSymbol(String stockSymbol) {
        this.stockSymbol = stockSymbol;
    }

    public String getStockSymbol2() {
        return stockSymbol2;
    }

    public void setStockSymbol2(String stockSymbol2) {
        this.stockSymbol2 = stockSymbol2;
    }

    public Double getResultValue() {
        return resultValue;
    }

    public void setResultValue(Double resultValue) {
        this.resultValue = resultValue;
    }
}
