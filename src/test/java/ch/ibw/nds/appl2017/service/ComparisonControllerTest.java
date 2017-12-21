package ch.ibw.nds.appl2017.service;

import ch.ibw.nds.appl2017.model.Stock;
import com.mscharhag.oleaster.runner.OleasterRunner;
import org.junit.runner.RunWith;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.mscharhag.oleaster.matcher.Matchers.expect;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.describe;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.it;
import static java.util.Calendar.DECEMBER;

/**
 * Created by dieterbiedermann on 18.12.17.
 */
@RunWith(OleasterRunner.class)
public class ComparisonControllerTest {{

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
    });


    describe("Test Stock Help Methods", () -> {
        it("should transform a Stock-Symbol List to a Stock-Object List", () -> {

            String stockString1 = "NESN";
            String stockString2 = "GOOGL";
            ArrayList<String> stockStringList = new ArrayList<>();
            stockStringList.add((stockString1));
            stockStringList.add((stockString2));

            Stock stock1 = Stock.createStock("NESN");
            Stock stock2 = Stock.createStock("GOOGL");
            ArrayList<Stock> stockList = new ArrayList();
            stockList.add(stock1);
            stockList.add(stock2);

            expect(ComparisonController.convertToStockList(stockStringList).size()).toEqual(stockList.size());
        });
    });

}}