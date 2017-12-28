package ch.ibw.nds.appl2017.model;

import com.mscharhag.oleaster.runner.OleasterRunner;
import org.junit.runner.RunWith;

import static com.mscharhag.oleaster.matcher.Matchers.expect;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.describe;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.it;

@RunWith(OleasterRunner.class)
public class StockTest {{

    describe("Test Creation of Simple Stock Objects (Without TimeSeries)", () -> {
        it("should create a simple stock object", () -> {
           String stockString1 = "NESN";
           Stock stock1 = Stock.createStock(stockString1);
           System.out.println(stock1);
           expect(stock1.getSymbol()).toEqual(stockString1);
        });
    });

}}