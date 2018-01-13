package ch.ibw.nds.appl2017.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ComparisonOutput {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComparisonOutput.class);

    private List<ComparisonOutputElement> comparisonOutputElements;

    private ComparisonOutput(List<ComparisonOutputElement> comparisonOutputElements) {
        this.comparisonOutputElements = comparisonOutputElements;
        LOGGER.debug(this.toString());
    }

    public static ComparisonOutput create(List<ComparisonOutputElement> comparisonOutputElements) {
        return new ComparisonOutput(comparisonOutputElements);
    }



    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Comparison-Output: ");
        stringBuilder.append(this.comparisonOutputElements);
        return stringBuilder.toString();
    }

    public List<ComparisonOutputElement> getComparisonOutputElements() {
        return comparisonOutputElements;
    }

    public void setComparisonOutputElements(List<ComparisonOutputElement> comparisonOutputElements) {
        this.comparisonOutputElements = comparisonOutputElements;
    }
}
