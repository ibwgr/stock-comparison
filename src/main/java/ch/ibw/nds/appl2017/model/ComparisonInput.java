package ch.ibw.nds.appl2017.model;

import ch.ibw.nds.appl2017.service.Const;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ComparisonInput {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComparisonInput.class);

    private List<Stock> stocks;
    private Date dateFrom;
    private Date dateTo;

    private ComparisonInput(List<Stock> stocks, Date dateFrom, Date dateTo) {
        this.stocks = stocks;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        LOGGER.debug(this.toString());
    }

    public static ComparisonInput createComparisonInput (List<Stock> stocks, Date dateFrom, Date dateTo) {
        return new ComparisonInput(stocks, dateFrom,  dateTo);
    }

    public static ComparisonInput createComparisonInput(List<String> stockSymbols, String fromDateString, String toDateString) {
        Date dateFrom = getDateFromApiDateString(fromDateString);
        Date dateTo = getDateFromApiDateString(toDateString);
        List<Stock> stockList = convertToStockList(stockSymbols);
        return new ComparisonInput(stockList,dateFrom,dateTo);
    }


    public static List convertToStockList(List<String> stockSymbols) {
        List<Stock> stockList = new ArrayList();
        for (String stockSymbol: stockSymbols) {
            stockList.add(Stock.createStock(stockSymbol));
        }
        return stockList;
    }

    public static Date getDateFromApiDateString(String dateString) {
        Date dateToBeConverted = null;
        try {
            dateToBeConverted = Const.REST_API_DATEFORMAT.parse(dateString);
        }
        catch (ParseException e) {
            LOGGER.warn("Wrong Date Format: {0}", dateString);
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        if (!Const.REST_API_DATEFORMAT.format(dateToBeConverted).equals(dateString)) {
            LOGGER.warn("Wrong Date Format: {0}", dateString);
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        return dateToBeConverted;
    }



    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Comparison-Input: ");
        for(Stock stock : this.stocks) {
            stringBuilder.append(stock);
            stringBuilder.append(" ");
        }
        stringBuilder.append(" DateFrom: " +Const.REST_API_DATEFORMAT.format(this.dateFrom));
        stringBuilder.append(" DateTo: " +Const.REST_API_DATEFORMAT.format(this.dateTo));
        return stringBuilder.toString();
    }

    public List<Stock> getStocks() {
        return stocks;
    }

    public void setStocks(List<Stock> stocks) {
        this.stocks = stocks;
    }

    public Date getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(Date dateFrom) {
        this.dateFrom = dateFrom;
    }

    public Date getDateTo() {
        return dateTo;
    }

    public void setDateTo(Date dateTo) {
        this.dateTo = dateTo;
    }
}
