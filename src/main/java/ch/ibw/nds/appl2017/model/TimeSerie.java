package ch.ibw.nds.appl2017.model;

import java.util.Date;

public class TimeSerie {
    private double closePrice;
    private Date closeDate;


//    private TimeSerie(double closePrice, Date closeDate){
//        this.closePrice = closePrice;
//        this.closeDate = closeDate;
//    }
//
//    public static TimeSerie createTimeSerie(double closePrice, Date closeDate) {
//        return new TimeSerie(closePrice,closeDate);
//    }


    public double getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(double closePrice) {
        this.closePrice = closePrice;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }
}
