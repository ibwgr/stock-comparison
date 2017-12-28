package ch.ibw.nds.appl2017.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComparisonOutputElement {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComparisonOutputElement.class);

    private Stock stock;
    private Stock stock2;
    private Double resultValue;

    private ComparisonOutputElement(Stock stock, Stock stock2, Double resultValue) {
        this.stock = stock;
        this.stock2 = stock2;
        this.resultValue = resultValue;
        LOGGER.debug(this.toString());
    }

    public static ComparisonOutputElement createComparisonOutputElement(Stock stock, Double resultValue) {
        return new ComparisonOutputElement(stock, null, resultValue);
    }

    public static ComparisonOutputElement createComparisonOutputElement(Stock stock, Stock stock2, Double resultValue) {
        return new ComparisonOutputElement(stock, stock2, resultValue);
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Comparison-Output Element: ");
        stringBuilder.append(this.stock);
        stringBuilder.append(": ");
        stringBuilder.append(this.resultValue);
        return stringBuilder.toString();
    }

    public Stock getStock() {
        return stock;
    }

    public void setStock(Stock stock) {
        this.stock = stock;
    }

    public Stock getStock2() {
        return stock2;
    }

    public void setStock2(Stock stock2) {
        this.stock2 = stock2;
    }

    public Double getResultValue() {
        return resultValue;
    }

    public void setResultValue(Double resultValue) {
        this.resultValue = resultValue;
    }
}
