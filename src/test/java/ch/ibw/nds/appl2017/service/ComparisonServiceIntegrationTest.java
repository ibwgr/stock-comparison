package ch.ibw.nds.appl2017.service;

import ch.ibw.nds.appl2017.controller.Performance;
import ch.ibw.nds.appl2017.model.ComparisonInput;
import com.mscharhag.oleaster.runner.OleasterRunner;
import org.apache.http.HttpStatus;
import org.junit.runner.RunWith;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

import static com.mscharhag.oleaster.matcher.Matchers.expect;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.describe;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.it;

@RunWith(OleasterRunner.class)
public class ComparisonServiceIntegrationTest {{

    String stockString1 = "COKE";
    String stockString2 = "GOOGL";
    String stockString3 = "AI";
    String stockString4 = "ORCL";
    String stockStringFail1 = "YYYYY";
    String stockStringFail2 = "YYYYYY";
    List<String> stockStringList = Arrays.asList(stockString1,stockString2,stockString3,stockString4);
    List<String> stockStringFailList = Arrays.asList(stockStringFail1, stockStringFail2);
    List<String> stockStringListSmall = Arrays.asList(stockString1,stockString2);
    String fromDateString = "20170101";
    String toDateString = "20171231";

    describe("Test Service Method With Good Parameters", () -> {
        it("should return http response code 200 (ok) on getPerformance", () -> {
            ComparisonService comparisonService = new ComparisonService();
            Response response = comparisonService.getPerformance(stockStringList, fromDateString, toDateString);
            System.out.println(response.getStatus());
            System.out.println(response.toString());
            System.out.println(response.getEntity().toString());
            expect(response.getStatus()).toEqual(HttpStatus.SC_OK);
        });
        it("should return http response code 200 (ok) on getCorrelation", () -> {
            ComparisonService comparisonService = new ComparisonService();
            Response response = comparisonService.getCorrelation(stockStringList, fromDateString, toDateString);
            System.out.println(response.getStatus());
            System.out.println(response.toString());
            System.out.println(response.getEntity().toString());
            expect(response.getStatus()).toEqual(HttpStatus.SC_OK);
        });
    });
    describe("Test Service Method With Bad Parameters", () -> {
        it("should throw WebApplicationException", () -> {
            expect(() -> {
                ComparisonService comparisonService = new ComparisonService();
                Response response = comparisonService.getPerformance(Arrays.asList("*"), "Bad-Date1", "No-date2");
            }).toThrow(WebApplicationException.class);
        });
    });

    describe("getComparisonOutputResponse", () -> {
        it("should return status 200 for correct symbol", () -> {
            ComparisonService comparisonService = new ComparisonService();
            ComparisonInput comparisonInput = ComparisonInput.create(stockStringListSmall,fromDateString, toDateString);

            Response response = comparisonService.getComparisonOutputResponse(comparisonInput, Performance.create());

            expect(response.getStatus()).toEqual(HttpStatus.SC_OK);
        });

        it("should return status 502 for wrong symbol", () -> {
            ComparisonService comparisonService = new ComparisonService();
            ComparisonInput comparisonInput = ComparisonInput.create(stockStringFailList,fromDateString, toDateString);

            Response response = comparisonService.getComparisonOutputResponse(comparisonInput, Performance.create());

            expect(response.getStatus()).toEqual(HttpStatus.SC_BAD_GATEWAY);
        });
    });
}}