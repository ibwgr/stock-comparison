package ch.ibw.nds.appl2017.controller;

import ch.ibw.nds.appl2017.model.ComparisonOutput;
import ch.ibw.nds.appl2017.model.ComparisonOutputElement;
import ch.ibw.nds.appl2017.model.Stock;
import ch.ibw.nds.appl2017.model.TimeSerie;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class Correlation extends ComparisonTemplate {

    private static final Logger LOGGER = LoggerFactory.getLogger(Correlation.class);

    private Correlation() {
    }

    public static Correlation create() {
        return new Correlation();
    }

    @Override
    public ComparisonOutput calculate(List<Stock> stocks) {
        super.calculate(stocks);
        List<ComparisonOutputElement> comparisonOutputElements = new ArrayList<>();
        for (int i = 0; i < stocks.size()-1; i++) {
            if (stocks.get(i).getTimeSeries() != null) {
                double[] x = stocks.get(i)
                        .getTimeSeries().stream()
                        .map(TimeSerie::getClosePrice)
                        .mapToDouble(Double::doubleValue).toArray();
                for (int j = i+1; j < stocks.size(); j++) {
                    double[] y = stocks.get(j)
                            .getTimeSeries().stream()
                            .map(TimeSerie::getClosePrice)
                            .mapToDouble(Double::doubleValue)
                            .toArray();
                    double corr = 0d;
                    if (x.length > 1 && y.length > 1) {
                        corr = new PearsonsCorrelation().correlation(x, y);
                        if (((Double) corr).isNaN()) {
                            corr = 0d;
                        }
                    }
                    LOGGER.info("Symbol {} and {} have a correlation of {}", stocks.get(i).getSymbol(), stocks.get(j).getSymbol(), corr);
                    comparisonOutputElements.add(ComparisonOutputElement.createComparisonOutputElement(stocks.get(i), stocks.get(j), corr));
                }
            }
        }
        return ComparisonOutput.createComparisonOutput(comparisonOutputElements);
    }


}
