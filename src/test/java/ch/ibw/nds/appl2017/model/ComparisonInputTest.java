package ch.ibw.nds.appl2017.model;

import com.mscharhag.oleaster.runner.OleasterRunner;
import org.junit.runner.RunWith;

import javax.ws.rs.WebApplicationException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.mscharhag.oleaster.matcher.Matchers.expect;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.describe;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.it;
import static java.util.Calendar.DECEMBER;

@RunWith(OleasterRunner.class)
public class ComparisonInputTest {{

    String stockString1 = "NESN";
    String stockString2 = "GOOGL";
    String stockString3 = "AI";

    List<String> stockStringList = new ArrayList<>();
    stockStringList.add((stockString1));
    stockStringList.add((stockString2));

    Stock stock1 = Stock.create(stockString1);
    Stock stock2 = Stock.create(stockString2);
    List<Stock> stockList = new ArrayList();
    stockList.add(stock1);
    stockList.add(stock2);

    String fromDateString = "20170101";
    String toDateString = "20171231";


    describe("Test ComparisonInput Building Methods", () -> {
        it("should bundle Elements for a ComparisonInput Object", () -> {
            ComparisonInput comparisonInput = ComparisonInput.create(stockStringList, fromDateString, toDateString);
            System.out.println(comparisonInput);
            expect(comparisonInput.getStocks().size()).toEqual(stockStringList.size());
            expect(comparisonInput.getStocks().get(0).getSymbol()).toEqual(stockString1);
        });
    });

    describe("Test Technical Helper Methods", () -> {
        it("should transform API String to Date", () -> {
            Date apiDatum = ComparisonInput.getDateFromApiDateString("20171231");
            Calendar cal = Calendar.getInstance();
            cal.setTime(apiDatum);
            System.out.println("Calendar's Year: " + cal.get(Calendar.YEAR));
            System.out.println("Calendar's Day: " + cal.get(Calendar.DATE));
            expect(cal.get(Calendar.YEAR)).toEqual(2017);
            expect(cal.get(Calendar.MONTH)).toEqual(DECEMBER);
            expect(cal.get(Calendar.DATE)).toEqual(31);
        });
        it("should throw WebApplicationException because of wrong date input string", () -> {
            expect(() -> {
                ComparisonInput.getDateFromApiDateString("a");
            }).toThrow(WebApplicationException.class);
        });
        it("should throw WebApplicationException because of wrong date format ddmmyyyy instead of yyyymmdd", () -> {
            expect(() -> {
                ComparisonInput.getDateFromApiDateString("31121999");
            }).toThrow(WebApplicationException.class);
        });
    });


    describe("Test Stock/ComparisonInput Building Methods", () -> {
        it("should transform a Stock-Symbol List to a Stock-Object List", () -> {
            expect(ComparisonInput.convertToStockList(stockStringList).size()).toEqual(stockList.size());
        });
    });

}}