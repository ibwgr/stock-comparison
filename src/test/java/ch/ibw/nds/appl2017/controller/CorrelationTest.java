package ch.ibw.nds.appl2017.controller;

import ch.ibw.nds.appl2017.model.ComparisonOutput;
import ch.ibw.nds.appl2017.model.Stock;
import ch.ibw.nds.appl2017.model.TimeSerie;
import ch.ibw.nds.appl2017.service.Const;
import com.mscharhag.oleaster.runner.OleasterRunner;
import jersey.repackaged.com.google.common.collect.Lists;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static com.mscharhag.oleaster.matcher.Matchers.expect;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.describe;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.it;

@RunWith(OleasterRunner.class)
public class CorrelationTest {{

    describe("calculate", () -> {
        it("should calculate correlation for 2 stocks with 1 timeseries", () -> {
            List<Stock> stocks = new ArrayList<>();
            Stock stock1 = Stock.createStock("MSFT");
            stock1.setTimeSeries(Lists.newArrayList(
                    TimeSerie.create(23d, Const.ALPHA_DATEFORMAT.parse("2017-12-27"))
            ));
            stocks.add(stock1);

            Stock stock2 = Stock.createStock("COKE");
            stock2.setTimeSeries(Lists.newArrayList(
                    TimeSerie.create(29d, Const.ALPHA_DATEFORMAT.parse("2017-12-27"))
            ));
            stocks.add(stock2);

            Correlation correlation = Correlation.create();
            ComparisonOutput comparisonOutput = correlation.calculate(stocks);

            expect(comparisonOutput.getComparisonOutputElements().size()).toEqual(1);
            expect(comparisonOutput.getComparisonOutputElements().get(0).getResultValue()).toEqual(0d);
        });

        it("should calculate correlation for 2 stocks", () -> {
            List<Stock> stocks = new ArrayList<>();
            Stock stock1 = Stock.createStock("MSFT");
            stock1.setTimeSeries(Lists.newArrayList(
                    TimeSerie.create(24d, Const.ALPHA_DATEFORMAT.parse("2017-12-26")),
                    TimeSerie.create(23d, Const.ALPHA_DATEFORMAT.parse("2017-12-27"))
            ));
            stocks.add(stock1);

            Stock stock2 = Stock.createStock("COKE");
            stock2.setTimeSeries(Lists.newArrayList(
                    TimeSerie.create(29d, Const.ALPHA_DATEFORMAT.parse("2017-12-26")),
                    TimeSerie.create(29d, Const.ALPHA_DATEFORMAT.parse("2017-12-27"))
            ));
            stocks.add(stock2);

            Correlation correlation = Correlation.create();
            ComparisonOutput comparisonOutput = correlation.calculate(stocks);

            expect(comparisonOutput.getComparisonOutputElements().size()).toEqual(1);
            expect(comparisonOutput.getComparisonOutputElements().get(0).getResultValue()).toEqual(0d);
        });

        it("should calculate correlation for 4 stocks in correct order", () -> {
            List<Stock> stocks = new ArrayList<>();
            Stock stock1 = Stock.createStock("MSFT");
            stock1.setTimeSeries(Lists.newArrayList(
                    TimeSerie.create(24d, Const.ALPHA_DATEFORMAT.parse("2017-12-26")),
                    TimeSerie.create(23d, Const.ALPHA_DATEFORMAT.parse("2017-12-27")),
                    TimeSerie.create(24d, Const.ALPHA_DATEFORMAT.parse("2017-12-28")),
                    TimeSerie.create(27d, Const.ALPHA_DATEFORMAT.parse("2017-12-29"))
            ));
            stocks.add(stock1);

            Stock stock2 = Stock.createStock("COKE");
            stock2.setTimeSeries(Lists.newArrayList(
                    TimeSerie.create(29d, Const.ALPHA_DATEFORMAT.parse("2017-12-26")),
                    TimeSerie.create(29d, Const.ALPHA_DATEFORMAT.parse("2017-12-27")),
                    TimeSerie.create(27d, Const.ALPHA_DATEFORMAT.parse("2017-12-28")),
                    TimeSerie.create(23d, Const.ALPHA_DATEFORMAT.parse("2017-12-29"))
            ));
            stocks.add(stock2);

            Stock stock3 = Stock.createStock("FLOW");
            stock3.setTimeSeries(Lists.newArrayList(
                    TimeSerie.create(4d, Const.ALPHA_DATEFORMAT.parse("2017-12-26")),
                    TimeSerie.create(8d, Const.ALPHA_DATEFORMAT.parse("2017-12-27")),
                    TimeSerie.create(8d, Const.ALPHA_DATEFORMAT.parse("2017-12-28")),
                    TimeSerie.create(9d, Const.ALPHA_DATEFORMAT.parse("2017-12-29"))
            ));
            stocks.add(stock3);

            Stock stock4 = Stock.createStock("XOXO");
            stock4.setTimeSeries(Lists.newArrayList(
                    TimeSerie.create(8d, Const.ALPHA_DATEFORMAT.parse("2017-12-26")),
                    TimeSerie.create(9d, Const.ALPHA_DATEFORMAT.parse("2017-12-27")),
                    TimeSerie.create(9d, Const.ALPHA_DATEFORMAT.parse("2017-12-28")),
                    TimeSerie.create(8d, Const.ALPHA_DATEFORMAT.parse("2017-12-29"))
            ));
            stocks.add(stock4);

            Correlation correlation = Correlation.create();
            ComparisonOutput comparisonOutput = correlation.calculate(stocks);

            expect(comparisonOutput.getComparisonOutputElements().size()).toEqual(6);
            expect(comparisonOutput.getComparisonOutputElements().get(0).getResultValue()).toEqual(0.4082482904638627d);
            expect(comparisonOutput.getComparisonOutputElements().get(1).getResultValue()).toEqual(0.3905667329424721d);
            expect(comparisonOutput.getComparisonOutputElements().get(2).getResultValue()).toEqual(0.39056673294247146d);
            expect(comparisonOutput.getComparisonOutputElements().get(3).getResultValue()).toEqual(-0.6377928041432805d);
            expect(comparisonOutput.getComparisonOutputElements().get(4).getResultValue()).toEqual(-0.6666666666666665d);
            expect(comparisonOutput.getComparisonOutputElements().get(5).getResultValue()).toEqual(-0.9525793444156802d);
        });
    });

    describe("getCorrelationForAllStocks", () -> {
        it("should calculate correlation for all stocks", () -> {
            List<Stock> stocks = new ArrayList<>();
            Stock stock1 = Stock.createStock("MSFT");
            stock1.setTimeSeries(Lists.newArrayList(
                    TimeSerie.create(23d, Const.ALPHA_DATEFORMAT.parse("2017-12-27")),
                    TimeSerie.create(25d, Const.ALPHA_DATEFORMAT.parse("2017-12-28"))
            ));
            stocks.add(stock1);

            Stock stock2 = Stock.createStock("COKE");
            stock2.setTimeSeries(Lists.newArrayList(
                    TimeSerie.create(29d, Const.ALPHA_DATEFORMAT.parse("2017-12-27")),
                    TimeSerie.create(30d, Const.ALPHA_DATEFORMAT.parse("2017-12-28"))
            ));
            stocks.add(stock2);

            Correlation correlation = Correlation.create();
            correlation.getCorrelationForAllStocks(stocks);

            expect(correlation.getComparisonOutputElements().size()).toEqual(1);
            expect(correlation.getComparisonOutputElements().get(0).getResultValue()).toEqual(1.0d);
        });
    });

    describe("addCorrelationElement", () -> {
        it("should calculate correlation and add the element", () -> {
            Stock stock1 = Stock.createStock("MSFT");
            stock1.setTimeSeries(Lists.newArrayList(
                    TimeSerie.create(23d, Const.ALPHA_DATEFORMAT.parse("2017-12-27")),
                    TimeSerie.create(25d, Const.ALPHA_DATEFORMAT.parse("2017-12-28"))
            ));

            Stock stock2 = Stock.createStock("COKE");
            stock2.setTimeSeries(Lists.newArrayList(
                    TimeSerie.create(29d, Const.ALPHA_DATEFORMAT.parse("2017-12-27")),
                    TimeSerie.create(30d, Const.ALPHA_DATEFORMAT.parse("2017-12-28"))
            ));

            double[] x = {23d,25d};
            double[] y = {29d,30d};

            Correlation correlation = Correlation.create();
            correlation.addCorrelationElement(stock1, x, stock2, y);

            expect(correlation.getComparisonOutputElements().size()).toEqual(1);
            expect(correlation.getComparisonOutputElements().get(0).getResultValue()).toEqual(1.0d);
        });
    });

    describe("getCorrelation", () -> {
        it("should return the correlation 1.0", () -> {
            Correlation correlation = Correlation.create();

            double[] x = {1d,2d};
            double[] y = {2d,3d};

            double result = correlation.getCorrelation(x,y);

            expect(result).toEqual(1.0d);
        });

        it("should return the correlation -1.0", () -> {
            Correlation correlation = Correlation.create();

            double[] x = {1d,2d};
            double[] y = {3d,2d};

            double result = correlation.getCorrelation(x,y);

            expect(result).toEqual(-1.0d);
        });
    });

    describe("getClosePrices", () -> {
        it("should return the close price as an array", () -> {
            Stock stock = Stock.createStock("MSFT");
            stock.setTimeSeries(Lists.newArrayList(
                    TimeSerie.create(23d, Const.ALPHA_DATEFORMAT.parse("2017-12-27"))
            ));

            Correlation correlation = Correlation.create();
            double[] result = correlation.getClosePrices(stock);

            expect(result.length).toEqual(1);
            expect(result[0]).toEqual(23d);
        });
    });

}}