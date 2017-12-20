package ch.ibw.nds.appl2017.service;

import ch.ibw.nds.appl2017.model.ComparisonInput;
import ch.ibw.nds.appl2017.model.Stock;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.annotation.JacksonFeatures;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Path("/performance2")
public class ComparisonController {

    // V2 : hier eine unbestimmte Anzahl Stocks, ist so vom Design her eher fuer zuk. Anwendungen offen
    // http://localhost:8080/stock-comparison-1.0-SNAPSHOT/rest/performance2?stock=NESN&stock=GOOGL&stock=ORCL&stock=LISN&dateFrom=20130313&dateTo=20171231

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @JacksonFeatures(serializationEnable = { SerializationFeature.INDENT_OUTPUT })
    public Response getPerformance(
            @QueryParam("stock") final List<String> stockSymbols,
            @QueryParam("dateFrom") String fromDateString,
            @QueryParam("dateTo") String toDateString) {

        // todo input validation

        Date dateFrom = getDateFromApiDateString(fromDateString);
        Date dateTo = getDateFromApiDateString(toDateString);
        ArrayList stockList = convertToStockList(stockSymbols);

        ComparisonInput comparisonInput = ComparisonInput.createComparisonInput(stockList,dateFrom,dateTo);
        System.out.println(comparisonInput);

        // todo hier natuerlich comparisonOutput
        return Response.status(200).entity(comparisonInput).build();
    }


    public static ArrayList convertToStockList(List<String> stockSymbols) {
        ArrayList stockList = new ArrayList();
        for (String stockSymbol: stockSymbols) {
            stockList.add(Stock.createStock(stockSymbol));
        }
        return stockList;
    }

    public static Date getDateFromApiDateString(String dateString) {
        try {
            return Const.REST_API_DATEFORMAT.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null; // todo null ist nicht gut
    }



}