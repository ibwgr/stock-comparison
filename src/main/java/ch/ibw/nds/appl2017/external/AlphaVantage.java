package ch.ibw.nds.appl2017.external;

import ch.ibw.nds.appl2017.model.Stock;
import ch.ibw.nds.appl2017.model.TimeSerie;
import ch.ibw.nds.appl2017.service.Const;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.WebApplicationException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class AlphaVantage {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlphaVantage.class);
    private static final HttpUtils httpUtils = HttpUtils.create();

    private static final String HTTPS = "http";
    private static final String HOST_NAME = "www.shuntcondition.com/stock/";
    private static final String REST_PATH = "query.php";
    private static final String FUNCTION = "function";
    private static final String TIME_SERIES_DAILY = "TIME_SERIES_DAILY";
    private static final String SYMBOL = "symbol";
    private static final String OUTPUTSIZE = "outputsize";
    private static final String OUTPUTSIZE_FULL = "compact";
    private static final String APIKEY = "apikey";
    private static final String APIKEY_VALUE = "IC2ZRP67FJJQ79DV";
    private static final String JSON_TIME_SERIES_DAILY1 = "Time Series (Daily)";
    private static final String JSON_CLOSE_PRICE = "4. close";

    private AlphaVantage() {

    }

    public static AlphaVantage create() {
        return new AlphaVantage();
    }

    public void getStock(Stock stock, Date dateFrom, Date dateTo) {
        JSONObject jsonObject = callApi(stock.getSymbol());
        LOGGER.info("JSON for symbol {} received", stock.getSymbol());
        if (isErrorResponse(jsonObject)){
            return;
        }
        List<TimeSerie> timeSeries = findTimeSeries(dateFrom, dateTo, jsonObject);
        stock.setTimeSeries(timeSeries);
    }

    private boolean isErrorResponse(JSONObject jsonObject) {
        boolean hasErrorMessage = jsonObject.has("Error Message");
        if (hasErrorMessage) {
            throwErrorMessage(jsonObject);
            return true;
        }
        return false;
    }

    public void throwErrorMessage(JSONObject jsonObject) {
        try {
            throw new WebApplicationException("Response error: " + jsonObject.getString("Error Message"));
        } catch (JSONException e) {
            String errorMessage = "Parsing error message from JSON response failed";
            LOGGER.error(errorMessage, e);
            throw new WebApplicationException(errorMessage, e);
        }
    }

    public List<TimeSerie> findTimeSeries(Date dateFrom, Date dateTo, JSONObject jsonObject) {
        List<TimeSerie> timeSeries = new ArrayList<>();
        JSONObject jsonTimeSeries = getJsonTimeSeries(jsonObject);
        Iterator<?> keys = jsonTimeSeries.keys();
        while( keys.hasNext() ) {
            String key = (String)keys.next();
            Double closePrice = getClosePriceFromJson(jsonTimeSeries, key);
            Date closeDate = getCloseDateFromKey(key);
            checkDateAndAddTimeSeries(dateFrom, dateTo, timeSeries, closePrice, closeDate);
        }
        return timeSeries;
    }

    public JSONObject getJsonTimeSeries(JSONObject jsonObject) {
        JSONObject jsonTimeSeries = new JSONObject();
        try {
            jsonTimeSeries = jsonObject.getJSONObject(JSON_TIME_SERIES_DAILY1);
        } catch (JSONException e) {
            String errorMessage = "Parsing time series from JSON response failed";
            LOGGER.error(errorMessage, e);
            throw new WebApplicationException(errorMessage, e);
        }
        return jsonTimeSeries;
    }

    public Date getCloseDateFromKey(String key) {
        Date closeDate = null;
        try {
            closeDate = Const.ALPHA_DATEFORMAT.parse(key);
        } catch (ParseException e) {
            String errorMessage = "Parsing date from JSON response failed";
            LOGGER.error(errorMessage, e);
            throw new WebApplicationException(errorMessage, e);
        }
        return closeDate;
    }

    public Double getClosePriceFromJson(JSONObject jsonTimeSeries, String key) {
        Double closePrice = 0d;
        try {
            Object jsonTimeSeriesKey = jsonTimeSeries.get(key);
            if (jsonTimeSeriesKey instanceof JSONObject) {
                closePrice = ((JSONObject) jsonTimeSeriesKey).getDouble(JSON_CLOSE_PRICE);
            }
        } catch (JSONException e) {
            String errorMessage = "Parsing closing price from JSON response failed";
            LOGGER.error(errorMessage, e);
            throw new WebApplicationException(errorMessage, e);
        }
        return closePrice;
    }

    public void checkDateAndAddTimeSeries(Date dateFrom, Date dateTo, List<TimeSerie> timeSeries, Double closePrice, Date closeDate) {
        Date dateFromMinus1 = DateUtils.asDate(DateUtils.asLocalDate(dateFrom).minusDays(1));
        Date dateToPlus1 = DateUtils.asDate(DateUtils.asLocalDate(dateTo).plusDays(1));
        if (closeDate.after(dateFromMinus1) && closeDate.before(dateToPlus1)) {
            timeSeries.add(TimeSerie.create(closePrice, closeDate));
        }
    }

    public JSONObject callApi(String symbol) {
        URIBuilder uriBuilder = new URIBuilder()
                .setScheme(HTTPS)
                .setHost(HOST_NAME)
                .setPath(REST_PATH)
                .addParameter(FUNCTION, TIME_SERIES_DAILY)
                .addParameter(SYMBOL, symbol)
                .addParameter(OUTPUTSIZE, OUTPUTSIZE_FULL)
                .addParameter(APIKEY, APIKEY_VALUE);

        return httpUtils.getJsonFromUriRequest(uriBuilder);
    }

}
