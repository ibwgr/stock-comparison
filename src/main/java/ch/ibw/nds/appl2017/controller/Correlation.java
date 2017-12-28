package ch.ibw.nds.appl2017.controller;

import ch.ibw.nds.appl2017.model.ComparisonOutput;
import ch.ibw.nds.appl2017.model.Stock;

import java.util.List;

public class Correlation extends ComparisonTemplate {

    private Correlation() {
    }

    public static Correlation create() {
        return new Correlation();
    }

    @Override
    public ComparisonOutput calculate(List<Stock> stocks) {
        super.calculate(stocks);

        // correlation berechnung hier...




        return null;
    }
}
