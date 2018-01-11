package ch.ibw.nds.appl2017.external;

import ch.ibw.nds.appl2017.model.Stock;
import ch.ibw.nds.appl2017.model.TimeSerie;
import ch.ibw.nds.appl2017.service.Const;
import com.mscharhag.oleaster.runner.OleasterRunner;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.runner.RunWith;

import javax.ws.rs.WebApplicationException;

import static com.mscharhag.oleaster.matcher.Matchers.expect;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.describe;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.it;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by dieterbiedermann on 18.12.17.
 */
@RunWith(OleasterRunner.class)
public class AlphaVantageTest {{

    describe("getStock", () -> {
        it("should add TimeSeries to Stock for symbol", () -> {
            AlphaVantage alphaVantage = AlphaVantage.create();
            Stock stock = Stock.createStock("msft");
            Date dateFrom = Const.ALPHA_DATEFORMAT.parse("2017-12-18");
            Date dateTo = Const.ALPHA_DATEFORMAT.parse("2017-12-20");

            alphaVantage.getStock(stock, dateFrom, dateTo);

            expect(stock.getTimeSeries().size()).toEqual(3);
        });

        it("should return no timeseries for not existing symbol", () -> {
            AlphaVantage alphaVantage = AlphaVantage.create();
            Stock stock = Stock.createStock("aaaaa");
            Date dateFrom = Const.ALPHA_DATEFORMAT.parse("2017-12-18");
            Date dateTo = Const.ALPHA_DATEFORMAT.parse("2017-12-20");

            expect(() -> alphaVantage.getStock(stock, dateFrom, dateTo))
                    .toThrow(WebApplicationException.class);
        });
    });

    describe("callApi", () -> {
        it("should return timeseries data for correct symbol", () -> {
            AlphaVantage alphaVantage = AlphaVantage.create();

            JSONObject jsonObject = alphaVantage.callApi("MSFT");

            expect(jsonObject.getJSONObject("Meta Data").getString("2. Symbol")).toEqual("MSFT");
        });
    });

    describe("findTimeSeries", () -> {
        it("should create timeseries", () -> {
            AlphaVantage alphaVantage = AlphaVantage.create();
            Date dateFrom = Const.ALPHA_DATEFORMAT.parse("2017-12-18");
            Date dateTo = Const.ALPHA_DATEFORMAT.parse("2017-12-20");

            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject("{\n" +
                        "    \"Meta Data\": {\n" +
                        "        \"1. Information\": \"Daily Prices (open, high, low, close) and Volumes\",\n" +
                        "        \"2. Symbol\": \"MSFT\",\n" +
                        "        \"3. Last Refreshed\": \"2017-12-22\",\n" +
                        "        \"4. Output Size\": \"Compact\",\n" +
                        "        \"5. Time Zone\": \"US/Eastern\"\n" +
                        "    },\n" +
                        "    \"Time Series (Daily)\": {\n" +
                        "        \"2017-12-22\": {\n" +
                        "            \"1. open\": \"85.4000\",\n" +
                        "            \"2. high\": \"85.6300\",\n" +
                        "            \"3. low\": \"84.9200\",\n" +
                        "            \"4. close\": \"85.5100\",\n" +
                        "            \"5. volume\": \"14081831\"\n" +
                        "        },\n" +
                        "        \"2017-12-21\": {\n" +
                        "            \"1. open\": \"86.0500\",\n" +
                        "            \"2. high\": \"86.1000\",\n" +
                        "            \"3. low\": \"85.4000\",\n" +
                        "            \"4. close\": \"85.5000\",\n" +
                        "            \"5. volume\": \"17939766\"\n" +
                        "        },\n" +
                        "        \"2017-12-20\": {\n" +
                        "            \"1. open\": \"86.2000\",\n" +
                        "            \"2. high\": \"86.3000\",\n" +
                        "            \"3. low\": \"84.7100\",\n" +
                        "            \"4. close\": \"85.5200\",\n" +
                        "            \"5. volume\": \"23425009\"\n" +
                        "        },\n" +
                        "        \"2017-12-19\": {\n" +
                        "            \"1. open\": \"86.3500\",\n" +
                        "            \"2. high\": \"86.3500\",\n" +
                        "            \"3. low\": \"85.2700\",\n" +
                        "            \"4. close\": \"85.8300\",\n" +
                        "            \"5. volume\": \"23241979\"\n" +
                        "        },\n" +
                        "        \"2017-12-18\": {\n" +
                        "            \"1. open\": \"87.1200\",\n" +
                        "            \"2. high\": \"87.4999\",\n" +
                        "            \"3. low\": \"86.2300\",\n" +
                        "            \"4. close\": \"86.3800\",\n" +
                        "            \"5. volume\": \"21551076\"\n" +
                        "        },\n" +
                        "        \"2017-12-15\": {\n" +
                        "            \"1. open\": \"85.2600\",\n" +
                        "            \"2. high\": \"87.0900\",\n" +
                        "            \"3. low\": \"84.8800\",\n" +
                        "            \"4. close\": \"86.8500\",\n" +
                        "            \"5. volume\": \"52430167\"\n" +
                        "        }\n" +
                        "    }\n" +
                        "}");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            List<TimeSerie> result = alphaVantage.findTimeSeries(dateFrom, dateTo, jsonObject);

            expect(result.size()).toEqual(3);
            expect(result.get(2).getCloseDate()).toEqual(Const.ALPHA_DATEFORMAT.parse("2017-12-18"));
            expect(result.get(2).getClosePrice()).toEqual(86.38d);
            expect(result.get(1).getCloseDate()).toEqual(Const.ALPHA_DATEFORMAT.parse("2017-12-19"));
            expect(result.get(1).getClosePrice()).toEqual(85.83d);
            expect(result.get(0).getCloseDate()).toEqual(Const.ALPHA_DATEFORMAT.parse("2017-12-20"));
            expect(result.get(0).getClosePrice()).toEqual(85.52d);
        });

        it("should return no time series", () -> {
            AlphaVantage alphaVantage = AlphaVantage.create();
            Date dateFrom = Const.ALPHA_DATEFORMAT.parse("2017-12-18");
            Date dateTo = Const.ALPHA_DATEFORMAT.parse("2017-12-20");

            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject("{\n" +
                        "    \"Meta Data\": {\n" +
                        "        \"1. Information\": \"Daily Prices (open, high, low, close) and Volumes\",\n" +
                        "        \"2. Symbol\": \"MSFT\",\n" +
                        "        \"3. Last Refreshed\": \"2017-12-22\",\n" +
                        "        \"4. Output Size\": \"Compact\",\n" +
                        "        \"5. Time Zone\": \"US/Eastern\"\n" +
                        "    }\n" +
                        "}");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JSONObject finalJsonObject = jsonObject;
            expect(() -> alphaVantage.findTimeSeries(dateFrom, dateTo, finalJsonObject))
                    .toThrow(WebApplicationException.class);

        });
    });

    describe("checkDateAndAddTimeSeries", () -> {
        it("should add timeseries with correct date and price", () -> {
            List<TimeSerie> timeSeries = new ArrayList<>();
            Date dateFrom = Const.ALPHA_DATEFORMAT.parse("2017-12-18");
            Date dateTo = Const.ALPHA_DATEFORMAT.parse("2017-12-20");
            Double closePrice = 12d;
            Date closeDate = Const.ALPHA_DATEFORMAT.parse("2017-12-20");

            AlphaVantage alphaVantage = AlphaVantage.create();

            alphaVantage.checkDateAndAddTimeSeries(dateFrom, dateTo, timeSeries, closePrice, closeDate);

            expect(timeSeries.get(0).getClosePrice()).toEqual(closePrice);
            expect(timeSeries.get(0).getCloseDate()).toEqual(closeDate);

        });

        it("should not add timeseries", () -> {
            List<TimeSerie> timeSeries = new ArrayList<>();
            Date dateFrom = Const.ALPHA_DATEFORMAT.parse("2017-12-18");
            Date dateTo = Const.ALPHA_DATEFORMAT.parse("2017-12-20");
            Double closePrice = 12d;
            Date closeDate = Const.ALPHA_DATEFORMAT.parse("2017-12-15");

            AlphaVantage alphaVantage = AlphaVantage.create();

            alphaVantage.checkDateAndAddTimeSeries(dateFrom, dateTo, timeSeries, closePrice, closeDate);

            expect(timeSeries.size()).toEqual(0);
        });
    });

    describe("throwErrorMessage", () -> {
        it("should throw an error with parsing error in message", () -> {
            AlphaVantage alphaVantage = AlphaVantage.create();

            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject("{\n" +
                        "    \"Meta Data\": {\n" +
                        "        \"1. Information\": \"Daily Prices (open, high, low, close) and Volumes\",\n" +
                        "        \"2. Symbol\": \"MSFT\",\n" +
                        "        \"3. Last Refreshed\": \"2017-12-22\",\n" +
                        "        \"4. Output Size\": \"Compact\",\n" +
                        "        \"5. Time Zone\": \"US/Eastern\"\n" +
                        "    }\n" +
                        "}");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JSONObject finalJsonObject = jsonObject;
            expect(() -> alphaVantage.throwErrorMessage(finalJsonObject))
                    .toThrow(WebApplicationException.class, "Parsing error message from JSON response failed");
        });

        it("should throw an error with the error message of the response", () -> {
            AlphaVantage alphaVantage = AlphaVantage.create();

            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject("{\n" +
                        "    \"Error Message\": \"example text\"\n" +
                        "}");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JSONObject finalJsonObject = jsonObject;
            expect(() -> alphaVantage.throwErrorMessage(finalJsonObject))
                    .toThrow(WebApplicationException.class, "Response error: example text");
        });
    });

    describe("getJsonTimeSeries", () -> {
        it("should throw an error for wrong json", () -> {
            AlphaVantage alphaVantage = AlphaVantage.create();

            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject("{\n" +
                        "    \"test\": \"wrong data\"\n" +
                        "}");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JSONObject finalJsonObject = jsonObject;
            expect(() -> alphaVantage.getJsonTimeSeries(finalJsonObject))
                    .toThrow(WebApplicationException.class, "Parsing time series from JSON response failed");
        });
    });

    describe("getCloseDateFromKey", () -> {
        it("should throw an error for wrong date", () -> {
            AlphaVantage alphaVantage = AlphaVantage.create();

            String date = "123";

            expect(() -> alphaVantage.getCloseDateFromKey(date))
                    .toThrow(WebApplicationException.class, "Parsing date from JSON response failed");
        });

        it("should return the date object", () -> {
            AlphaVantage alphaVantage = AlphaVantage.create();

            String date = "2017-12-24";

            Date result = alphaVantage.getCloseDateFromKey(date);

            expect(DateUtils.asLocalDate(result).getYear()).toEqual(2017);
            expect(DateUtils.asLocalDate(result).getMonthValue()).toEqual(12);
            expect(DateUtils.asLocalDate(result).getDayOfMonth()).toEqual(24);
        });
    });

    describe("getClosePriceFromJson", () -> {
        it("should throw an error for wrong date", () -> {
            AlphaVantage alphaVantage = AlphaVantage.create();

            String date = "123";
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject("{\n" +
                        "        \"2017-12-22\": {\n" +
                        "            \"1. open\": \"85.4000\",\n" +
                        "            \"2. high\": \"85.6300\",\n" +
                        "            \"3. low\": \"84.9200\",\n" +
                        "            \"4. close\": \"85.5100\",\n" +
                        "            \"5. volume\": \"14081831\"\n" +
                        "        },\n" +
                        "        \"2017-12-21\": {\n" +
                        "            \"1. open\": \"86.0500\",\n" +
                        "            \"2. high\": \"86.1000\",\n" +
                        "            \"3. low\": \"85.4000\",\n" +
                        "            \"4. close\": \"85.5000\",\n" +
                        "            \"5. volume\": \"17939766\"\n" +
                        "        }\n" +
                        "}");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JSONObject finalJsonObject = jsonObject;
            expect(() -> alphaVantage.getClosePriceFromJson(finalJsonObject, date))
                    .toThrow(WebApplicationException.class, "Parsing closing price from JSON response failed");
        });

        it("should throw an error for wrong close field", () -> {
            AlphaVantage alphaVantage = AlphaVantage.create();

            String date = "2017-12-22";
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject("{\n" +
                        "        \"2017-12-22\": {\n" +
                        "            \"1. open\": \"85.4000\",\n" +
                        "            \"2. high\": \"85.6300\",\n" +
                        "            \"3. low\": \"84.9200\",\n" +
                        "            \"4. wrong-close\": \"85.5100\",\n" +
                        "            \"5. volume\": \"14081831\"\n" +
                        "        },\n" +
                        "        \"2017-12-21\": {\n" +
                        "            \"1. open\": \"86.0500\",\n" +
                        "            \"2. high\": \"86.1000\",\n" +
                        "            \"3. low\": \"85.4000\",\n" +
                        "            \"4. close\": \"85.5000\",\n" +
                        "            \"5. volume\": \"17939766\"\n" +
                        "        }\n" +
                        "}");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            JSONObject finalJsonObject = jsonObject;
            expect(() -> alphaVantage.getClosePriceFromJson(finalJsonObject, date))
                    .toThrow(WebApplicationException.class, "Parsing closing price from JSON response failed");
        });
        it("should return close price", () -> {
            AlphaVantage alphaVantage = AlphaVantage.create();

            String date = "2017-12-22";
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject("{\n" +
                        "        \"2017-12-22\": {\n" +
                        "            \"1. open\": \"85.4000\",\n" +
                        "            \"2. high\": \"85.6300\",\n" +
                        "            \"3. low\": \"84.9200\",\n" +
                        "            \"4. close\": \"85.5100\",\n" +
                        "            \"5. volume\": \"14081831\"\n" +
                        "        },\n" +
                        "        \"2017-12-21\": {\n" +
                        "            \"1. open\": \"86.0500\",\n" +
                        "            \"2. high\": \"86.1000\",\n" +
                        "            \"3. low\": \"85.4000\",\n" +
                        "            \"4. close\": \"85.5000\",\n" +
                        "            \"5. volume\": \"17939766\"\n" +
                        "        }\n" +
                        "}");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            Double result = alphaVantage.getClosePriceFromJson(jsonObject, date);

            expect(result).toEqual(85.5100d);
        });
    });
}}