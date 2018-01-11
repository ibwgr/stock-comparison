package ch.ibw.nds.appl2017.service;

import ch.ibw.nds.appl2017.controller.ComparisonTemplate;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.annotation.JacksonFeatures;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ch.ibw.nds.appl2017.controller.Correlation;
import ch.ibw.nds.appl2017.controller.Performance;
import ch.ibw.nds.appl2017.model.ComparisonInput;
import ch.ibw.nds.appl2017.model.ComparisonOutput;

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
        return getComparisonOutputResponse(comparisonInput, Correlation.create());
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
        return getComparisonOutputResponse(comparisonInput, Performance.create());
    }

    public Response getComparisonOutputResponse(ComparisonInput comparisonInput, ComparisonTemplate comparisonTemplate) {
        try {
            ComparisonOutput comparisonOutput = comparisonTemplate.compare(comparisonInput);
            return Response.status(200).entity(comparisonOutput).build();
        } catch (Exception e) {
            return Response.status(502).entity(ErrorMessage.createErrorMessage("internal-error")).build();
        }
    }

}