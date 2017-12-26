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

    // V2 : hier eine unbestimmte Anzahl Stocks, ist so vom Design her eher fuer zuk. Anwendungen offen
    // http://localhost:8080/stock-comparison-1.0-SNAPSHOT/rest/comparison/performance?stock=NESN&stock=GOOGL&stock=ORCL&stock=LISN&dateFrom=20130313&dateTo=20171231
    // http://localhost:8080/stock-comparison-1.0-SNAPSHOT/rest/comparison/correlation?stock=NESN&stock=GOOGL&stock=ORCL&stock=LISN&dateFrom=20130313&dateTo=20171231

    @Path("/correlation")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @JacksonFeatures(serializationEnable = {SerializationFeature.INDENT_OUTPUT})
    public Response getCorrelation(
            @QueryParam("stock") final List<String> stockSymbols,
            @QueryParam("dateFrom") final String fromDateString,
            @QueryParam("dateTo") final String toDateString) {

        validateInput(stockSymbols, fromDateString, toDateString);
        ComparisonInput comparisonInput = buildComparisonInput(stockSymbols, fromDateString, toDateString);
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

        validateInput(stockSymbols, fromDateString, toDateString);
        ComparisonInput comparisonInput = buildComparisonInput(stockSymbols, fromDateString, toDateString);
        // todo call berechnung
        // todo hier natuerlich comparisonOutput
        return Response.status(200).entity(comparisonInput).build();
    }


    // todo unschoen
    public void validateInput(List<String> stockSymbols, String fromDateString, String toDateString) {

        if (fromDateString == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        if (!fromDateString.matches(Const.REST_API_DATEFORMAT_REGEX_PATTERN)) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        if (toDateString == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        if (!toDateString.matches(Const.REST_API_DATEFORMAT_REGEX_PATTERN)) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }

        if (stockSymbols == null) {
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        for (String stockSymbol : stockSymbols) {
            if (!stockSymbol.matches("^[A-Z]{1,5}$")) {
                throw new WebApplicationException(Response.Status.BAD_REQUEST);
            }
        }
    }


    public ComparisonInput buildComparisonInput(List<String> stockSymbols, String fromDateString, String toDateString) {
        Date dateFrom = getDateFromApiDateString(fromDateString);
        Date dateTo = getDateFromApiDateString(toDateString);
        List<Stock> stockList = convertToStockList(stockSymbols);

        ComparisonInput comparisonInput = ComparisonInput.createComparisonInput(stockList,dateFrom,dateTo);
        LOGGER.debug(comparisonInput.toString());
        return comparisonInput;
    }

    public static List convertToStockList(List<String> stockSymbols) {
        List<Stock> stockList = new ArrayList();
        for (String stockSymbol: stockSymbols) {
            stockList.add(Stock.createStock(stockSymbol));
        }
        return stockList;
    }

    public static Date getDateFromApiDateString(String dateString) {
        Date testDate = null;
        try {
            testDate = Const.REST_API_DATEFORMAT.parse(dateString);
        }
        catch (ParseException e) {
            LOGGER.warn("Wrong Date Format: " +dateString);
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        if (!Const.REST_API_DATEFORMAT.format(testDate).equals(dateString)) {
            LOGGER.warn("Wrong Date Format: " +dateString);
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        return testDate;
    }

}