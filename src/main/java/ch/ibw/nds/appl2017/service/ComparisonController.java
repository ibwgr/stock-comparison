package ch.ibw.nds.appl2017.service;

import ch.ibw.nds.appl2017.model.ComparisonInput;
import ch.ibw.nds.appl2017.model.Stock;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.annotation.JacksonFeatures;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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