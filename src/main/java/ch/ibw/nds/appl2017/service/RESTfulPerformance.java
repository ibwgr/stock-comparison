package ch.ibw.nds.appl2017.service;

import ch.ibw.nds.appl2017.model.ComparisonInput;
import ch.ibw.nds.appl2017.model.Stock;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/performance")
public class RESTfulPerformance {

    // http://localhost:8080/web_war_exploded/rest/correlation?stock1=NESN&stock2=GOOGL&stock3=ORCL&stock4=LISN&dateFrom=20130313&dateTo=20171231
    // http://localhost:8080/web_war_exploded/rest/performance?stock1=NESN&stock2=GOOGL&stock3=ORCL&stock4=LISN&dateFrom=20130313&dateTo=20171231

    // http://localhost:8080/stock-comparison-1.0-SNAPSHOT/rest/correlation?stock1=NESN&stock2=GOOGL&stock3=ORCL&stock4=LISN&dateFrom=20130313&dateTo=20171231
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMessage(
    //public Stock getMessage(
            @QueryParam("stock1") String stockSymbol1,
            @QueryParam("stock2") String stockSymbol2,
            @QueryParam("stock3") String stockSymbol3,
            @QueryParam("stock4") String stockSymbol4,
            @QueryParam("dateFrom") String dateFromString,
            @QueryParam("dateTo") String dateToString) {

        String output =  "***************** Ressource:Performance, stock1:" + stockSymbol1 +", stock2:" + stockSymbol2
                +", stock3:" + stockSymbol3+", stock3:" + stockSymbol3
                +", from:" + dateFromString +", to:" + dateToString;
        System.out.println(output);

        Stock stock1 = new Stock(stockSymbol1);
//        Stock stock1 = Stock.createStock(stockSymbol1);
//        Stock stock2 = Stock.createStock(stockSymbol2);
//        Stock stock3 = Stock.createStock(stockSymbol3);
//        Stock stock4 = Stock.createStock(stockSymbol4);
//        ComparisonInput comparisonInput= new ComparisonInput();
//        comparisonInput.getStocks().add(stock1);
//        comparisonInput.getStocks().add(stock2);
//        comparisonInput.getStocks().add(stock3);
//        comparisonInput.getStocks().add(stock4);

        //return stock1;
        return Response.status(200).entity(stock1).build();
        //return Response.status(200).entity(output).build();
    }

}