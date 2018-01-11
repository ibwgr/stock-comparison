package ch.ibw.nds.appl2017.external;

import ch.ibw.nds.appl2017.model.Stock;

import java.util.Date;

public interface StockDataService {

    void getStock(Stock stock, Date dateFrom, Date dateTo);

}
