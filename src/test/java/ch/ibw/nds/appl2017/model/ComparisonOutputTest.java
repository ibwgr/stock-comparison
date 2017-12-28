package ch.ibw.nds.appl2017.model;

import com.mscharhag.oleaster.runner.OleasterRunner;
import org.junit.runner.RunWith;

import java.util.Arrays;

import static com.mscharhag.oleaster.matcher.Matchers.expect;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.describe;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.it;

@RunWith(OleasterRunner.class)
public class ComparisonOutputTest {{

    describe("Test Creation of Comparison Output Object)", () -> {
        it("should create a simple comparison ouput object", () -> {
            ComparisonOutput comparisonOutput = ComparisonOutput.createComparisonOutput(
                    Arrays.asList(
                            ComparisonOutputElement.createComparisonOutputElement(Stock.createStock("NESN"), 1.15) ,
                            ComparisonOutputElement.createComparisonOutputElement(Stock.createStock("GOOGL"), 1.07) ,
                            ComparisonOutputElement.createComparisonOutputElement(Stock.createStock("AI"), 1.01)
                    )
            );
            System.out.println(comparisonOutput);
            expect(comparisonOutput.getComparisonOutputElements().size()).toEqual(3);
            expect(comparisonOutput.getComparisonOutputElements().get(0).getStock().getSymbol()).toEqual("NESN");
        });
        it("should create a comparison ouput object containing two stocks on each output-element", () -> {
            ComparisonOutput comparisonOutput = ComparisonOutput.createComparisonOutput(
                    Arrays.asList(
                            ComparisonOutputElement.createComparisonOutputElement(Stock.createStock("NESN"), Stock.createStock("GOOGL"), 0.99) ,
                            ComparisonOutputElement.createComparisonOutputElement(Stock.createStock("AI"), Stock.createStock("ABB"), 0.52) ,
                            ComparisonOutputElement.createComparisonOutputElement(Stock.createStock("MAN"), Stock.createStock("ORCL"),0.27)
                    )
            );
            System.out.println(comparisonOutput);
            expect(comparisonOutput.getComparisonOutputElements().size()).toEqual(3);
            expect(comparisonOutput.getComparisonOutputElements().get(0).getStock().getSymbol()).toEqual("NESN");
            expect(comparisonOutput.getComparisonOutputElements().get(0).getStock2().getSymbol()).toEqual("GOOGL");
        });
    });

}}