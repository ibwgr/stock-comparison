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
            ComparisonOutput comparisonOutput = ComparisonOutput.create(
                    Arrays.asList(
                            ComparisonOutputElement.create(Stock.create("NESN"), 1.15) ,
                            ComparisonOutputElement.create(Stock.create("GOOGL"), 1.07) ,
                            ComparisonOutputElement.create(Stock.create("AI"), 1.01)
                    )
            );
            System.out.println(comparisonOutput);
            expect(comparisonOutput.getComparisonOutputElements().size()).toEqual(3);
            expect(comparisonOutput.getComparisonOutputElements().get(0).getStockSymbol()).toEqual("NESN");
        });
        it("should create a comparison ouput object containing two stocks on each output-element", () -> {
            ComparisonOutput comparisonOutput = ComparisonOutput.create(
                    Arrays.asList(
                            ComparisonOutputElement.create(Stock.create("NESN"), Stock.create("GOOGL"), 0.99) ,
                            ComparisonOutputElement.create(Stock.create("AI"), Stock.create("ABB"), 0.52) ,
                            ComparisonOutputElement.create(Stock.create("MAN"), Stock.create("ORCL"),0.27)
                    )
            );
            System.out.println(comparisonOutput);
            expect(comparisonOutput.getComparisonOutputElements().size()).toEqual(3);
            expect(comparisonOutput.getComparisonOutputElements().get(0).getStockSymbol()).toEqual("NESN");
            expect(comparisonOutput.getComparisonOutputElements().get(0).getStockSymbol2()).toEqual("GOOGL");
        });
    });

}}