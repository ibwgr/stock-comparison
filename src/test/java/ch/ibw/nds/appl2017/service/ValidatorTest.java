package ch.ibw.nds.appl2017.service;

import ch.ibw.nds.appl2017.model.Stock;
import com.mscharhag.oleaster.runner.OleasterRunner;
import org.junit.runner.RunWith;

import javax.ws.rs.WebApplicationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mscharhag.oleaster.matcher.Matchers.expect;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.describe;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.it;

@RunWith(OleasterRunner.class)
public class ValidatorTest {{

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


    describe("Test Security Methods Positive", () -> {
        it("should successfully validate input with default testdata", () -> {
            Validator.validateInput(stockStringList, fromDateString, toDateString);
        });
        it("should successfully validate input with 1 stock", () -> {
            List<String> tempStockStringList = new ArrayList<String>(
                    Arrays.asList(stockString1));
            Validator.validateInput(tempStockStringList, fromDateString, toDateString);
        });
        it("should successfully validate input with 2 stock", () -> {
            List<String> tempStockStringList = new ArrayList<String>(
                    Arrays.asList(stockString1,stockString2));
            Validator.validateInput(tempStockStringList, fromDateString, toDateString);
        });
        it("should successfully validate input with 3 stock", () -> {
            List<String> tempStockStringList = new ArrayList<String>(
                    Arrays.asList(stockString1,stockString2,stockString3));
            Validator.validateInput(tempStockStringList, fromDateString, toDateString);
        });
    });

    describe("Test Security Methods Negative", () -> {
        it("should throw WebApplicationException because of a too long stock symbol (fromDate ok, toDate ok)", () -> {
            expect(() -> {
                Validator.validateInput(Arrays.asList("THISISAVERYLONGSYMBOL"),  fromDateString, toDateString);
            }).toThrow(WebApplicationException.class);
        });
        it("should throw WebApplicationException because of missing stocklist (fromDate ok, toDate ok)", () -> {
            expect(() -> {
                Validator.validateInput(null,  fromDateString, toDateString);
            }).toThrow(WebApplicationException.class);
        });
        it("should throw WebApplicationException because of missing fromDate (stocklist ok, toDate ok)", () -> {
            expect(() -> {
                Validator.validateInput(stockStringList,  null, toDateString);
            }).toThrow(WebApplicationException.class);
        });
        it("should throw WebApplicationException because of missing toDate (stocklist ok, fromDate ok)", () -> {
            expect(() -> {
                Validator.validateInput(stockStringList,  fromDateString, null);
            }).toThrow(WebApplicationException.class);
        });
        it("should throw WebApplicationException because of dangerous input on fromDateString", () -> {
            expect(() -> {
                Validator.validateInput(stockStringList, "<script type=\"text/javascript\">alert(\"hello!\");</script>", toDateString);
            }).toThrow(WebApplicationException.class);
        });
        it("should throw WebApplicationException because of dangerous input on toDateString", () -> {
            expect(() -> {
                Validator.validateInput(stockStringList, fromDateString,"<script type=\"text/javascript\">alert(\"hello!\");</script>");
            }).toThrow(WebApplicationException.class);
        });
    });


}}