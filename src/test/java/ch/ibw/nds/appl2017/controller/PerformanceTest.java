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
import java.util.Optional;

import static com.mscharhag.oleaster.matcher.Matchers.expect;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.describe;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.it;

@RunWith(OleasterRunner.class)
public class PerformanceTest {{


    describe("calculatePerformance", () -> {
        it("should return 100% for a change from 10 to 20", () -> {
            Performance performance = Performance.create();

            Double price1 = 10d;
            Double price2 = 20d;
            Double result = performance.calculatePerformance(price1, price2);

            expect(result).toEqual(100d);
        });

        it("should return 50% for a change from 8 to 12", () -> {
            Performance performance = Performance.create();

            Double price1 = 8d;
            Double price2 = 12d;
            Double result = performance.calculatePerformance(price1, price2);

            expect(result).toEqual(50d);
        });

        it("should return 10% for a change from 550 to 605", () -> {
            Performance performance = Performance.create();

            Double price1 = 550d;
            Double price2 = 605d;
            Double result = performance.calculatePerformance(price1, price2);

            expect(result).toEqual(10d);
        });

        it("should return -50% for a change from 40 to 20", () -> {
            Performance performance = Performance.create();

            Double price1 = 40d;
            Double price2 = 20d;
            Double result = performance.calculatePerformance(price1, price2);

            expect(result).toEqual(-50d);
        });

        it("should return -100% for a change from 15 to 0", () -> {
            Performance performance = Performance.create();

            Double price1 = 15d;
            Double price2 = 0d;
            Double result = performance.calculatePerformance(price1, price2);

            expect(result).toEqual(-100d);
        });

        it("should throw an error for price1 0", () -> {
            Performance performance = Performance.create();

            Double price1 = 0d;
            Double price2 = 10d;

            expect(() -> {
                Double result = performance.calculatePerformance(price1, price2);
            }).toThrow(IllegalArgumentException.class);
        });
    });

    describe("getPerformance", () -> {
        it("should return 100% for a change from 10 to 20", () -> {
            Optional<TimeSerie> timeSerie1 = Optional.of(TimeSerie.create(10d, Const.ALPHA_DATEFORMAT.parse("2017-12-26")));
            Optional<TimeSerie> timeSerie2 = Optional.of(TimeSerie.create(20d, Const.ALPHA_DATEFORMAT.parse("2017-12-27")));

            Performance performance = Performance.create();
            Double result = performance.getPerformance(timeSerie1, timeSerie2);

            expect(result).toEqual(100d);
        });

        it("should return -10% for a change from 100 to 90", () -> {
            Optional<TimeSerie> timeSerie1 = Optional.of(TimeSerie.create(100d, Const.ALPHA_DATEFORMAT.parse("2017-12-21")));
            Optional<TimeSerie> timeSerie2 = Optional.of(TimeSerie.create(90d, Const.ALPHA_DATEFORMAT.parse("2017-12-27")));

            Performance performance = Performance.create();
            Double result = performance.getPerformance(timeSerie1, timeSerie2);

            expect(result).toEqual(-10d);
        });
    });

    describe("calculate", () -> {
        it("should return the ComparisonOutput for 1 stock with 2 timeSeries", () -> {
            List<Stock> stocks = new ArrayList<>();
            Stock stock1 = Stock.createStock("MSFT");
            stock1.setTimeSeries(Lists.newArrayList(
                    TimeSerie.create(20d, Const.ALPHA_DATEFORMAT.parse("2017-12-26")),
                    TimeSerie.create(40d, Const.ALPHA_DATEFORMAT.parse("2017-12-27"))
            ));
            stocks.add(stock1);

            Performance performance = Performance.create();
            ComparisonOutput comparisonOutput = performance.calculate(stocks);

            expect(comparisonOutput.getComparisonOutputElements().size()).toEqual(1);
            expect(comparisonOutput.getComparisonOutputElements().get(0).getResultValue()).toEqual(100d);
        });

        it("should return the ComparisonOutput for 1 stock with 3 timeSeries", () -> {
            List<Stock> stocks = new ArrayList<>();
            Stock stock1 = Stock.createStock("MSFT");
            stock1.setTimeSeries(Lists.newArrayList(
                    TimeSerie.create(20d, Const.ALPHA_DATEFORMAT.parse("2017-12-25")),
                    TimeSerie.create(30d, Const.ALPHA_DATEFORMAT.parse("2017-12-26")),
                    TimeSerie.create(40d, Const.ALPHA_DATEFORMAT.parse("2017-12-27"))
            ));
            stocks.add(stock1);

            Performance performance = Performance.create();
            ComparisonOutput comparisonOutput = performance.calculate(stocks);

            expect(comparisonOutput.getComparisonOutputElements().size()).toEqual(1);
            expect(comparisonOutput.getComparisonOutputElements().get(0).getResultValue()).toEqual(100d);
        });

        it("should return the ComparisonOutput for 1 stock with 5 timeSeries", () -> {
            List<Stock> stocks = new ArrayList<>();
            Stock stock1 = Stock.createStock("MSFT");
            stock1.setTimeSeries(Lists.newArrayList(
                    TimeSerie.create(20d, Const.ALPHA_DATEFORMAT.parse("2017-12-21")),
                    TimeSerie.create(10d, Const.ALPHA_DATEFORMAT.parse("2017-12-20")),
                    TimeSerie.create(30d, Const.ALPHA_DATEFORMAT.parse("2017-12-23")),
                    TimeSerie.create(50d, Const.ALPHA_DATEFORMAT.parse("2017-12-27")),
                    TimeSerie.create(40d, Const.ALPHA_DATEFORMAT.parse("2017-12-26"))
            ));
            stocks.add(stock1);

            Performance performance = Performance.create();
            ComparisonOutput comparisonOutput = performance.calculate(stocks);

            expect(comparisonOutput.getComparisonOutputElements().size()).toEqual(1);
            expect(comparisonOutput.getComparisonOutputElements().get(0).getResultValue()).toEqual(400d);
        });

        it("should return the ComparisonOutputs for 2 stock with 2 timeSeries in correct order", () -> {
            List<Stock> stocks = new ArrayList<>();
            Stock stock1 = Stock.createStock("MSFT");
            stock1.setTimeSeries(Lists.newArrayList(
                    TimeSerie.create(1000d, Const.ALPHA_DATEFORMAT.parse("2017-12-21")),
                    TimeSerie.create(1010d, Const.ALPHA_DATEFORMAT.parse("2017-12-26"))
            ));
            stocks.add(stock1);

            Stock stock2 = Stock.createStock("COKE");
            stock2.setTimeSeries(Lists.newArrayList(
                    TimeSerie.create(100d, Const.ALPHA_DATEFORMAT.parse("2017-12-21")),
                    TimeSerie.create(110d, Const.ALPHA_DATEFORMAT.parse("2017-12-26"))
            ));
            stocks.add(stock2);

            Performance performance = Performance.create();
            ComparisonOutput comparisonOutput = performance.calculate(stocks);

            expect(comparisonOutput.getComparisonOutputElements().size()).toEqual(2);
            expect(comparisonOutput.getComparisonOutputElements().get(0).getResultValue()).toEqual(10d);
            expect(comparisonOutput.getComparisonOutputElements().get(1).getResultValue()).toEqual(1d);
        });
    });

}}