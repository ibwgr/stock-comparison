package ch.ibw.nds.appl2017.service;

import ch.ibw.nds.appl2017.model.ComparisonInput;
import ch.ibw.nds.appl2017.model.Stock;
import com.mscharhag.oleaster.runner.OleasterRunner;
import org.junit.runner.RunWith;

import javax.ws.rs.WebApplicationException;
import java.util.*;

import static com.mscharhag.oleaster.matcher.Matchers.expect;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.describe;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.it;
import static java.util.Calendar.DECEMBER;

/**
 * Created by dieterbiedermann on 18.12.17.
 */
@RunWith(OleasterRunner.class)
public class ComparisonControllerTest {{

    String stockString1 = "NESN";
    String stockString2 = "GOOGL";
    String stockString3 = "AI";

    List<String> stockStringList = new ArrayList<>();
    stockStringList.add((stockString1));
    stockStringList.add((stockString2));

    Stock stock1 = Stock.createStock(stockString1);
    Stock stock2 = Stock.createStock(stockString2);
    List<Stock> stockList = new ArrayList();
    stockList.add(stock1);
    stockList.add(stock2);

    String fromDateString = "20170101";
    String toDateString = "20171231";


    describe("Test Technical Helper Methods", () -> {
        it("should transform API String to Date", () -> {
            Date apiDatum = ComparisonController.getDateFromApiDateString("20171231");
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
                ComparisonController.getDateFromApiDateString("a");
            }).toThrow(WebApplicationException.class);
        });
        it("should throw WebApplicationException because of wrong date format ddmmyyyy instead of yyyymmdd", () -> {
            expect(() -> {
                ComparisonController.getDateFromApiDateString("31121999");
            }).toThrow(WebApplicationException.class);
        });
    });


    describe("Test Stock/ComparisonInput Building Methods", () -> {
        it("should transform a Stock-Symbol List to a Stock-Object List", () -> {
            expect(ComparisonController.convertToStockList(stockStringList).size()).toEqual(stockList.size());
        });

        it("should bundle Elements for a ComparisonInput Object", () -> {
            ComparisonController comparisonController = new ComparisonController();
            ComparisonInput comparisonInput = comparisonController.buildComparisonInput (stockStringList, fromDateString, toDateString);
            expect(comparisonInput.getStocks().size()).toEqual(stockStringList.size());
            expect(comparisonInput.getStocks().get(0).getSymbol()).toEqual(stockString1);
        });
    });

    describe("Test Security Methods Positive", () -> {
        it("should successfully validate input with default testdata", () -> {
            ComparisonController comparisonController = new ComparisonController();
            comparisonController.validateInput(stockStringList, fromDateString, toDateString);
        });
        it("should successfully validate input with 1 stock", () -> {
            ComparisonController comparisonController = new ComparisonController();
            List<String> tempStockStringList = new ArrayList<String>(
                    Arrays.asList(stockString1));
            comparisonController.validateInput(tempStockStringList, fromDateString, toDateString);
        });
        it("should successfully validate input with 2 stock", () -> {
            ComparisonController comparisonController = new ComparisonController();
            List<String> tempStockStringList = new ArrayList<String>(
                    Arrays.asList(stockString1,stockString2));
            comparisonController.validateInput(tempStockStringList, fromDateString, toDateString);
        });
        it("should successfully validate input with 3 stock", () -> {
            ComparisonController comparisonController = new ComparisonController();
            List<String> tempStockStringList = new ArrayList<String>(
                    Arrays.asList(stockString1,stockString2,stockString3));
            comparisonController.validateInput(tempStockStringList, fromDateString, toDateString);
        });
    });

    describe("Test Security Methods Negative", () -> {
        it("should throw WebApplicationException because of a too long stock symbol (fromDate ok, toDate ok)", () -> {
            expect(() -> {
                ComparisonController comparisonController = new ComparisonController();
                comparisonController.validateInput(Arrays.asList("THISISAVERYLONGSYMBOL"),  fromDateString, toDateString);
            }).toThrow(WebApplicationException.class);
        });
        it("should throw WebApplicationException because of missing stocklist (fromDate ok, toDate ok)", () -> {
            expect(() -> {
                ComparisonController comparisonController = new ComparisonController();
                comparisonController.validateInput(null,  fromDateString, toDateString);
            }).toThrow(WebApplicationException.class);
        });
        it("should throw WebApplicationException because of missing fromDate (stocklist ok, toDate ok)", () -> {
            expect(() -> {
                ComparisonController comparisonController = new ComparisonController();
                comparisonController.validateInput(stockStringList,  null, toDateString);
            }).toThrow(WebApplicationException.class);
        });
        it("should throw WebApplicationException because of missing toDate (stocklist ok, fromDate ok)", () -> {
            expect(() -> {
                ComparisonController comparisonController = new ComparisonController();
                comparisonController.validateInput(stockStringList,  fromDateString, null);
            }).toThrow(WebApplicationException.class);
        });
        it("should throw WebApplicationException because of dangerous input", () -> {
            expect(() -> {
                ComparisonController comparisonController = new ComparisonController();
                comparisonController.validateInput(stockStringList, "<script type=\"text/javascript\">alert(\"hello!\");</script>", toDateString);
            }).toThrow(WebApplicationException.class);
        });
    });


}}