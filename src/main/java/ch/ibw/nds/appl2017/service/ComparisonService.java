package ch.ibw.nds.appl2017.service;

import ch.ibw.nds.appl2017.controller.Correlation;
import ch.ibw.nds.appl2017.model.ComparisonInput;
import ch.ibw.nds.appl2017.model.ComparisonOutput;
import ch.ibw.nds.appl2017.model.ComparisonOutputElement;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.annotation.JacksonFeatures;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;

@Path("/comparison")
public class ComparisonService {

    @Path("/correlation")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @JacksonFeatures(serializationEnable = {SerializationFeature.INDENT_OUTPUT})
    public Response getCorrelation(
            @QueryParam("stock") final List<String> stockSymbols,
            @QueryParam("dateFrom") final String fromDateString,
            @QueryParam("dateTo") final String toDateString) {
        Validator.validateInput(stockSymbols, fromDateString, toDateString);
        ComparisonInput comparisonInput = ComparisonInput.createComparisonInput(stockSymbols, fromDateString, toDateString);
        Response response = getComparisonCorrelationOutputResponse(comparisonInput);
        return response;
    }

    public Response getComparisonCorrelationOutputResponse(ComparisonInput comparisonInput) {
        try {
            Correlation correlation = Correlation.create();
            ComparisonOutput comparisonOutput = correlation.compare(comparisonInput);
            return Response.status(200).entity(comparisonOutput).build();
        } catch (Exception e) {
            return Response.status(502).entity(ErrorMessage.createErrorMessage("internal-error")).build();
        }
    }


    @Path("/performance")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @JacksonFeatures(serializationEnable = {SerializationFeature.INDENT_OUTPUT})
    public Response getPerformance(
            @QueryParam("stock") final List<String> stockSymbols,
            @QueryParam("dateFrom") final String fromDateString,
            @QueryParam("dateTo") final String toDateString) {
        Validator.validateInput(stockSymbols, fromDateString, toDateString);
        ComparisonInput comparisonInput = ComparisonInput.createComparisonInput(stockSymbols, fromDateString, toDateString);
        Response response = getComparisonPerformanceOutputResponse(comparisonInput);
        return response;
    }

    public Response getComparisonPerformanceOutputResponse(ComparisonInput comparisonInput) {
        try {
            // todo call berechnung
            // todo das hier ist nur zu Testzwecken (Annahme dass 4 Stocksymbole uebergeben werden)
            ComparisonOutput comparisonOutput = ComparisonOutput.createComparisonOutput(
                    Arrays.asList(
                            ComparisonOutputElement.createComparisonOutputElement(comparisonInput.getStocks().get(0), comparisonInput.getStocks().get(1),2.15) ,
                            ComparisonOutputElement.createComparisonOutputElement(comparisonInput.getStocks().get(2), comparisonInput.getStocks().get(3),1.07)
                    )
            );
            return Response.status(200).entity(comparisonOutput).build();
        } catch (Exception e) {
            return Response.status(502).entity(ErrorMessage.createErrorMessage("internal-error")).build();
        }
    }

}