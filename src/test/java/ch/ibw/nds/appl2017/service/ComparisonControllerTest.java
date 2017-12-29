package ch.ibw.nds.appl2017.service;

import com.mscharhag.oleaster.runner.OleasterRunner;
import org.junit.runner.RunWith;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

import static com.mscharhag.oleaster.matcher.Matchers.expect;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.describe;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.it;

@RunWith(OleasterRunner.class)
public class ComparisonControllerTest {{

    String stockString1 = "NESN";
    String stockString2 = "GOOGL";
    String stockString3 = "AI";
    String stockString4 = "ORCL";
    List<String> stockStringList = Arrays.asList(stockString1,stockString2,stockString3,stockString4);
    String fromDateString = "20170101";
    String toDateString = "20171231";

    describe("Test Service Method With Good Parameters", () -> {
        it("should return http response code 200 (ok) on getPerformance", () -> {
            ComparisonService comparisonController = new ComparisonService();
            Response response = comparisonController.getPerformance(stockStringList, fromDateString, toDateString);
            System.out.println(response.getStatus());
            System.out.println(response.toString());
            System.out.println(response.getEntity().toString());
            expect(response.getStatus()).toEqual(200);
        });
        it("should return http response code 200 (ok) on getCorrelation", () -> {
            ComparisonService comparisonController = new ComparisonService();
            Response response = comparisonController.getCorrelation(stockStringList, fromDateString, toDateString);
            System.out.println(response.getStatus());
            System.out.println(response.toString());
            System.out.println(response.getEntity().toString());
            expect(response.getStatus()).toEqual(200);
        });
    });
    describe("Test Service Method With Bad Parameters", () -> {
        it("should throw WebApplicationException", () -> {
            expect(() -> {
                ComparisonService comparisonController = new ComparisonService();
                Response response = comparisonController.getPerformance(Arrays.asList("*"), "Bad-Date1", "No-date2");
            }).toThrow(WebApplicationException.class);
        });
    });

}}