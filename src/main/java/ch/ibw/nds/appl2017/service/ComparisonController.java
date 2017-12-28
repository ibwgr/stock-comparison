package ch.ibw.nds.appl2017.service;

import ch.ibw.nds.appl2017.model.ComparisonInput;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.annotation.JacksonFeatures;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/comparison")
public class ComparisonController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComparisonController.class);

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
        // todo call berechnung
        // todo hier natuerlich comparisonOutput
        return Response.status(200).entity(comparisonInput).build();
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
        // todo call berechnung
        // todo hier natuerlich comparisonOutput
        return Response.status(200).entity(comparisonInput).build();
    }

}