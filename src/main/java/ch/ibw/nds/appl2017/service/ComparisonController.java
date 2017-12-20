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
    // http://localhost:8080/stock-comparison-1.0-SNAPSHOT/rest/correlation?stock1=NESN&stock2=GOOGL&stock3=ORCL&stock4=LISN&dateFrom=20130313&dateTo=20171231

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @JacksonFeatures(serializationEnable = { SerializationFeature.INDENT_OUTPUT })
    public Response getPerformance(
            @QueryParam("stock") final List<String> stocks,
            @QueryParam("dateFrom") String dateFromString,
            @QueryParam("dateTo") String dateToString) {

//        String output =  "***************** Ressource:Performance, stock1:" + stocks.get(0) +", stock2:" + stocks.get(1)
//                +", stock3:" + stocks.get(2)+", stock3:" + stocks.get(3)
//                +", from:" + dateFromString +", to:" + dateToString;
//        System.out.println(output);

        Date dateFrom = null;
        Date dateTo = null;
        DateFormat format = new SimpleDateFormat("yyyymmdd");
        try {
            dateFrom = format.parse(dateFromString);
            dateTo = format.parse(dateToString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Stock stock1 = Stock.createStock( stocks.get(0));
        Stock stock2 = Stock.createStock( stocks.get(1));
        Stock stock3 = Stock.createStock( stocks.get(2));
        Stock stock4 = Stock.createStock( stocks.get(3));
        ArrayList stockList = new ArrayList();
        stockList.add(stock1);
        stockList.add(stock2);
        stockList.add(stock3);
        stockList.add(stock4);

        ComparisonInput comparisonInput = ComparisonInput.createComparisonInput(stockList,dateFrom,dateTo);

        // todo hier natuerlich comparisonOutput
        return Response.status(200).entity(comparisonInput).build();
    }

}