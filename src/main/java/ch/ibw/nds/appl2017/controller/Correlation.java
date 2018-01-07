package ch.ibw.nds.appl2017.controller;

import ch.ibw.nds.appl2017.model.ComparisonOutput;
import ch.ibw.nds.appl2017.model.ComparisonOutputElement;
import ch.ibw.nds.appl2017.model.Stock;
import ch.ibw.nds.appl2017.model.TimeSerie;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Correlation extends ComparisonTemplate {

    private static final Logger LOGGER = LoggerFactory.getLogger(Correlation.class);
    private List<ComparisonOutputElement> comparisonOutputElements = new ArrayList<>();

    private Correlation() {
    }

    public static Correlation create() {
        return new Correlation();
    }

    @Override
    public ComparisonOutput calculate(List<Stock> stocks) {
        comparisonOutputElements = new ArrayList<>();

        for (int i = 0; i < stocks.size()-1; i++) {
            if (stocks.get(i).getTimeSeries() != null) {
                double[] x = getClosePrices(stocks.get(i));
                for (int j = i+1; j < stocks.size(); j++) {
                    double[] y = getClosePrices(stocks.get(j));
                    addCorrelationElement(stocks.get(i), x, stocks.get(j), y);
                }
            }
        }
        comparisonOutputElements.sort(Comparator.comparing(ComparisonOutputElement::getResultValue).reversed());
        return ComparisonOutput.createComparisonOutput(comparisonOutputElements);
    }

    public void addCorrelationElement(Stock stockX, double[] x, Stock stockY, double[] y) {
        double corr = getCorrelation(x, y);
        LOGGER.info("Symbol {} and {} have a correlation of {}", stockX.getSymbol(), stockY.getSymbol(), corr);
        comparisonOutputElements.add(ComparisonOutputElement.createComparisonOutputElement(stockX, stockY, corr));
    }

    public double getCorrelation(double[] x, double[] y) {
        double corr = 0d;
        if (x.length > 1 && y.length > 1) {
            corr = new PearsonsCorrelation().correlation(x, y);
            if (((Double) corr).isNaN()) {
                corr = 0d;
            }
        }
        return corr;
    }

    public double[] getClosePrices(Stock stock) {
        return stock
                .getTimeSeries().stream()
                .map(TimeSerie::getClosePrice)
                .mapToDouble(Double::doubleValue).toArray();
    }

    public List<ComparisonOutputElement> getComparisonOutputElements() {
        return comparisonOutputElements;
    }
}
