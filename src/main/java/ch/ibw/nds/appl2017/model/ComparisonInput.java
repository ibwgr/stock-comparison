package ch.ibw.nds.appl2017.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ComparisonInput {

    private List<Stock> stocks;
    private Date dateFrom;
    private Date dateTo;

    private ComparisonInput(List<Stock> stocks, Date dateFrom, Date dateTo) {
        this.stocks = stocks;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
    }

    public static ComparisonInput createComparisonInput (List<Stock> stocks, Date dateFrom, Date dateTo) {
        return new  ComparisonInput(stocks, dateFrom,  dateTo);
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
