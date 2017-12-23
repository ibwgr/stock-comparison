package ch.ibw.nds.appl2017.model;

import ch.ibw.nds.appl2017.service.Const;

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
