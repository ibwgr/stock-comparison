package ch.ibw.nds.appl2017.service;

import ch.ibw.nds.appl2017.controller.ComparisonTemplate;
import ch.ibw.nds.appl2017.controller.Correlation;
import ch.ibw.nds.appl2017.controller.Performance;
import ch.ibw.nds.appl2017.model.ComparisonInput;
import ch.ibw.nds.appl2017.model.ComparisonOutput;
import ch.ibw.nds.appl2017.model.ComparisonOutputElement;
import ch.ibw.nds.appl2017.model.Stock;
import com.mscharhag.oleaster.runner.OleasterRunner;
import org.apache.http.HttpStatus;
import org.junit.runner.RunWith;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

import static com.mscharhag.oleaster.matcher.Matchers.expect;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.describe;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.it;

@RunWith(OleasterRunner.class)
public class ComparisonServiceTest {{

    String stockString1 = "COKE";
    String stockString2 = "GOOGL";
    String stockString3 = "AI";
    String stockString4 = "ORCL";
    List<String> stockStringList = Arrays.asList(stockString1,stockString2,stockString3,stockString4);
    String fromDateString = "20170101";
    String toDateString = "20171231";

    describe("Test Service with mocked business logic", () -> {
        it("should return http 200 on getPerformance", () -> {
            ComparisonOutput comparisonOutput = ComparisonOutput.createComparisonOutput(
                    Arrays.asList(
                            ComparisonOutputElement.createComparisonOutputElement(Stock.createStock("NESN"), 1.15) ,
                            ComparisonOutputElement.createComparisonOutputElement(Stock.createStock("GOOGL"), 1.07) ,
                            ComparisonOutputElement.createComparisonOutputElement(Stock.createStock("AI"), 1.01)
                    )
            );
            ComparisonService comparisonService = new ComparisonService();
            ComparisonService spiedComparisonService = Mockito.spy(comparisonService);
            Mockito.when(spiedComparisonService.getComparisonOutputResponse(Mockito.any(ComparisonInput.class),Mockito.any(ComparisonTemplate.class)))
                    .thenReturn(
                            Response.status(HttpStatus.SC_OK).entity(comparisonOutput).build()
                    );
            Response response = spiedComparisonService.getPerformance(stockStringList, fromDateString, toDateString);
            expect(response.getStatus()).toEqual(HttpStatus.SC_OK);
        });

        it("should return http 200 on getCorrelation", () -> {
            ComparisonOutput comparisonOutput = ComparisonOutput.createComparisonOutput(
                    Arrays.asList(
                            ComparisonOutputElement.createComparisonOutputElement(Stock.createStock("NESN"), 1.15) ,
                            ComparisonOutputElement.createComparisonOutputElement(Stock.createStock("GOOGL"), 1.07) ,
                            ComparisonOutputElement.createComparisonOutputElement(Stock.createStock("AI"), 1.01)
                    )
            );
            ComparisonService comparisonService = new ComparisonService();
            ComparisonService spiedComparisonService = Mockito.spy(comparisonService);
            Mockito.when(spiedComparisonService.getComparisonOutputResponse(Mockito.any(ComparisonInput.class),Mockito.any(ComparisonTemplate.class)))
                    .thenReturn(
                            Response.status(HttpStatus.SC_OK).entity(comparisonOutput).build()
                    );
            Response response = spiedComparisonService.getCorrelation(stockStringList, fromDateString, toDateString);
            expect(response.getStatus()).toEqual(HttpStatus.SC_OK);
        });



        it("should return http 502, mocking the business logic call for Correlation, assuming an exeption occured", () -> {
            ComparisonInput comparisonInput = ComparisonInput.createComparisonInput(stockStringList,fromDateString, toDateString);
            ComparisonTemplate spiedComparisonTemplate = Mockito.mock(Correlation.class);
            Mockito.when(spiedComparisonTemplate.compare(Mockito.any(ComparisonInput.class)))
                    .thenThrow(new RuntimeException()
                    );
            ComparisonService comparisonService = new ComparisonService();
            Response response = comparisonService.getComparisonOutputResponse(comparisonInput, spiedComparisonTemplate);
            expect(response.getStatus()).toEqual(HttpStatus.SC_BAD_GATEWAY);
        });

        it("should return http 502, mocking the business logic call for Performance, assuming an exeption occured", () -> {
            ComparisonInput comparisonInput = ComparisonInput.createComparisonInput(stockStringList,fromDateString, toDateString);
            ComparisonTemplate spiedComparisonTemplate = Mockito.mock(Performance.class);
            Mockito.when(spiedComparisonTemplate.compare(Mockito.any(ComparisonInput.class)))
                    .thenThrow(new RuntimeException()
                    );
            ComparisonService comparisonService = new ComparisonService();
            Response response = comparisonService.getComparisonOutputResponse(comparisonInput, spiedComparisonTemplate);
            expect(response.getStatus()).toEqual(HttpStatus.SC_BAD_GATEWAY);
        });
    });

}}