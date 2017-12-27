package ch.ibw.nds.appl2017.external;

import ch.ibw.nds.appl2017.model.Stock;
import ch.ibw.nds.appl2017.model.TimeSerie;
import ch.ibw.nds.appl2017.service.Const;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by dieterbiedermann on 18.12.17.
 */
public class AlphaVantage {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlphaVantage.class);

    private static final String HTTPS = "https";
    private static final String HOST_NAME = "www.alphavantage.co";
    private static final String REST_PATH = "query";
    private static final String FUNCTION = "function";
    private static final String TIME_SERIES_DAILY = "TIME_SERIES_DAILY";
    private static final String SYMBOL = "symbol";
    private static final String OUTPUTSIZE = "outputsize";
    private static final String OUTPUTSIZE_FULL = "full";
    private static final String APIKEY = "apikey";
    private static final String APIKEY_VALUE = "IC2ZRP67FJJQ79DV";
    private static final String UTF_8 = "UTF-8";
    private static final String JSON_TIME_SERIES_DAILY1 = "Time Series (Daily)";
    private static final String JSON_CLOSE_PRICE = "4. close";

    private AlphaVantage() {

    }

    public static AlphaVantage create() {
        return new AlphaVantage();
    }

    public void getStock(Stock stock, Date dateFrom, Date dateTo) {
        JSONObject jsonObject = callApi(stock.getSymbol());
        if (isErrorResponse(jsonObject)){
            return;
        }
        List<TimeSerie> timeSeries = findTimeSeries(dateFrom, dateTo, jsonObject);
        stock.setTimeSeries(timeSeries);
    }

    private boolean isErrorResponse(JSONObject jsonObject) {
        boolean hasErrorMessage = jsonObject.has("Error Message");
        if (hasErrorMessage) {
            logErrorMessage(jsonObject);
            return true;
        }
        return false;
    }

    private void logErrorMessage(JSONObject jsonObject) {
        try {
            LOGGER.error("Response Error: {}", jsonObject.getString("Error Message"));
        } catch (JSONException e) {
            LOGGER.trace("Parsing Error Message from JSON response failed", e);
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

    private JSONObject getJsonTimeSeries(JSONObject jsonObject) {
        JSONObject jsonTimeSeries = new JSONObject();
        try {
            jsonTimeSeries = jsonObject.getJSONObject(JSON_TIME_SERIES_DAILY1);
        } catch (JSONException e) {
            LOGGER.trace("Parsing time series from JSON response failed", e);
        }
        return jsonTimeSeries;
    }

    private Date getCloseDateFromKey(String key) {
        Date closeDate = null;
        try {
            closeDate = Const.ALPHA_DATEFORMAT.parse(key);
        } catch (ParseException e) {
            LOGGER.trace("Parsing date from JSON response failed", e);
        }
        return closeDate;
    }

    private Double getClosePriceFromJson(JSONObject jsonTimeSeries, String key) {
        Double closePrice = 0d;
        try {
            Object jsonTimeSeriesKey = jsonTimeSeries.get(key);
            if (jsonTimeSeriesKey instanceof JSONObject) {
                closePrice = ((JSONObject) jsonTimeSeriesKey).getDouble(JSON_CLOSE_PRICE);
            }
        } catch (JSONException e) {
            LOGGER.trace("Parsing closing price from JSON response failed", e);
        }
        return closePrice;
    }

    public void checkDateAndAddTimeSeries(Date dateFrom, Date dateTo, List<TimeSerie> timeSeries, Double closePrice, Date closeDate) {
        Date dateFromMinus1 = DateUtils.asDate(DateUtils.asLocalDate(dateFrom).minusDays(1));
        Date dateToPlus1 = DateUtils.asDate(DateUtils.asLocalDate(dateTo).plusDays(1));
        if (closeDate.after(dateFromMinus1) && closeDate.before(dateToPlus1)) {
            LOGGER.info("Add TimeSeries with date {} and price {}", closeDate, closePrice);
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

        HttpUriRequest request = new HttpGet(uriBuilder.toString());
        HttpResponse httpResponse = getHttpResponse(request);
        return getJsonResponse(httpResponse);
    }

    private JSONObject getJsonResponse(HttpResponse httpResponse) {
        JSONObject responseJson = new JSONObject();
        HttpEntity entity = httpResponse.getEntity();
        try {
            String responseString = EntityUtils.toString(entity, UTF_8);
            responseJson = new JSONObject(responseString);
        } catch (IOException | JSONException e) {
            LOGGER.trace("Parsing JSON response failed", e);
        }
        return responseJson;
    }

    private HttpResponse getHttpResponse(HttpUriRequest request) {
        HttpResponse httpResponse = null;
        try {
            httpResponse = HttpClientBuilder
                    .create()
                    .build()
                    .execute(request);
        } catch (IOException e) {
            LOGGER.trace("Http request failed", e);
        }
        return httpResponse;
    }
}
