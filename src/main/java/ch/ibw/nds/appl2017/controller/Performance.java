package ch.ibw.nds.appl2017.controller;

import ch.ibw.nds.appl2017.model.ComparisonOutput;
import ch.ibw.nds.appl2017.model.ComparisonOutputElement;
import ch.ibw.nds.appl2017.model.Stock;
import ch.ibw.nds.appl2017.model.TimeSerie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class Performance extends ComparisonTemplate {

    private static final Logger LOGGER = LoggerFactory.getLogger(Performance.class);
    private List<ComparisonOutputElement> comparisonOutputElements = new ArrayList<>();

    private Performance() {

    }

    public static Performance create() {
        return new Performance();
    }

    @Override
    public ComparisonOutput calculate(List<Stock> stocks) {
        comparisonOutputElements = new ArrayList<>();
        stocks.forEach(this::addPerformanceElement);
        comparisonOutputElements.sort(Comparator.comparing(ComparisonOutputElement::getResultValue).reversed());
        return ComparisonOutput.create(comparisonOutputElements);
    }

    public void addPerformanceElement(Stock stock) {
        Optional<TimeSerie> timeSerie1 = stock.getTimeSeries().stream()
                .reduce((a, b) -> a.getCloseDate().before(b.getCloseDate()) ? a : b);
        Optional<TimeSerie> timeSerie2 = stock.getTimeSeries().stream()
                .reduce((a, b) -> a.getCloseDate().after(b.getCloseDate()) ? a : b);
        Double perf = getPerformance(timeSerie1, timeSerie2);
        LOGGER.info("Symbol {} has a performance of {}", stock.getSymbol(), perf);
        comparisonOutputElements.add(ComparisonOutputElement.create(stock, perf));
    }

    public Double getPerformance(Optional<TimeSerie> timeSerie1, Optional<TimeSerie> timeSerie2) {
        Double perf = 0d;
        if (timeSerie1.isPresent() && timeSerie2.isPresent()) {
            Double price1 = timeSerie1.get().getClosePrice();
            Double price2 = timeSerie2.get().getClosePrice();
            perf = calculatePerformance(price1, price2);
        }
        return perf;
    }

    public Double calculatePerformance(Double price1, Double price2) {
        if (price1 == null || price1.equals(0d)) {
            throw new IllegalArgumentException("price1 must not be zero");
        }
        return (price2 - price1) / price1 * 100;
    }

    public List<ComparisonOutputElement> getComparisonOutputElements() {
        return comparisonOutputElements;
    }
}
