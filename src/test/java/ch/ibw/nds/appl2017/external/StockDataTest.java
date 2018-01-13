package ch.ibw.nds.appl2017.external;

import ch.ibw.nds.appl2017.model.ComparisonInput;
import ch.ibw.nds.appl2017.model.Stock;
import ch.ibw.nds.appl2017.service.Const;
import com.mscharhag.oleaster.runner.OleasterRunner;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.mscharhag.oleaster.matcher.Matchers.expect;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.describe;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.it;

/**
 * Created by dieterbiedermann on 18.12.17.
 */
@RunWith(OleasterRunner.class)
public class StockDataTest {{

    describe("getAllStocks", () -> {
        it("should be possible to get timeseries for all stocks", () -> {
            List<Stock> stocks = new ArrayList<>();
            stocks.add(Stock.create("MSFT"));
            stocks.add(Stock.create("COKE"));
            Date dateFrom = Const.ALPHA_DATEFORMAT.parse("2017-12-18");
            Date dateTo = Const.ALPHA_DATEFORMAT.parse("2017-12-20");

            ComparisonInput comparisonInput = ComparisonInput.create(stocks,dateFrom,dateTo);

            StockData stockData = StockData.create(AlphaVantage.create());
            List<Stock> stocksResult = stockData.getAllStocks(comparisonInput);

            expect(stocksResult.size()).toEqual(2);
        });

        it("should be possible to get timeseries for all stocks with default constructor", () -> {
            List<Stock> stocks = new ArrayList<>();
            stocks.add(Stock.create("MSFT"));
            stocks.add(Stock.create("COKE"));
            Date dateFrom = Const.ALPHA_DATEFORMAT.parse("2017-12-18");
            Date dateTo = Const.ALPHA_DATEFORMAT.parse("2017-12-20");

            ComparisonInput comparisonInput = ComparisonInput.create(stocks,dateFrom,dateTo);

            StockData stockData = StockData.create();
            List<Stock> stocksResult = stockData.getAllStocks(comparisonInput);

            expect(stocksResult.size()).toEqual(2);
        });
    });

}}