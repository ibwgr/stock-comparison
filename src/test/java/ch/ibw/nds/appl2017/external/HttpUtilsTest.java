package ch.ibw.nds.appl2017.external;

import com.mscharhag.oleaster.runner.OleasterRunner;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONObject;
import org.junit.runner.RunWith;

import javax.ws.rs.WebApplicationException;

import static com.mscharhag.oleaster.matcher.Matchers.expect;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.describe;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.it;

@RunWith(OleasterRunner.class)
public class HttpUtilsTest {{

    describe("getJsonFromResponse", () -> {
        it("should throw an error for no JSON in response", () -> {

            HttpUtils httpUtils = HttpUtils.create();

            URIBuilder uriBuilder = new URIBuilder()
                    .setScheme("https")
                    .setHost("www.google.com");

            HttpUriRequest request = new HttpGet(uriBuilder.toString());
            HttpResponse httpResponse = httpUtils.getHttpResponse(request);

            expect(() -> httpUtils.getJsonFromResponse(httpResponse))
                    .toThrow(WebApplicationException.class, "Parsing JSON response failed");
        });

        it("should return a JSONObject", () -> {

            HttpUtils httpUtils = HttpUtils.create();

            URIBuilder uriBuilder = new URIBuilder()
                    .setScheme("https")
                    .setHost("www.alphavantage.co")
                    .setPath("query")
                    .addParameter("function", "TIME_SERIES_DAILY")
                    .addParameter("symbol", "COKE")
                    .addParameter("apikey", "IC2ZRP67FJJQ79DV");

            HttpUriRequest request = new HttpGet(uriBuilder.toString());
            HttpResponse httpResponse = httpUtils.getHttpResponse(request);

            JSONObject jsonObject = httpUtils.getJsonFromResponse(httpResponse);

            expect(jsonObject).toBeNotNull();
        });
    });

    describe("getHttpResponse", () -> {
        it("should httpResponse for the request", () -> {

            HttpUtils httpUtils = HttpUtils.create();

            URIBuilder uriBuilder = new URIBuilder()
                    .setScheme("https")
                    .setHost("www.google.com");

            HttpUriRequest request = new HttpGet(uriBuilder.toString());

            HttpResponse result = httpUtils.getHttpResponse(request);

            expect(result.getStatusLine().getStatusCode()).toEqual(HttpStatus.SC_OK);
        });

        it("should throw error for wrong uri / request failure", () -> {

            HttpUtils httpUtils = HttpUtils.create();

            URIBuilder uriBuilder = new URIBuilder()
                    .setScheme("htts")
                    .setHost("www.ggle.co");

            HttpUriRequest request = new HttpGet(uriBuilder.toString());

            expect(() -> httpUtils.getHttpResponse(request))
                    .toThrow(WebApplicationException.class, "Http request failed");
        });
    });

    describe("getJsonFromUriRequest", () -> {
        it("should return the JSONObject for the symbol", () -> {

            HttpUtils httpUtils = HttpUtils.create();

            URIBuilder uriBuilder = new URIBuilder()
                    .setScheme("https")
                    .setHost("www.alphavantage.co")
                    .setPath("query")
                    .addParameter("function", "TIME_SERIES_DAILY")
                    .addParameter("symbol", "COKE")
                    .addParameter("apikey", "IC2ZRP67FJJQ79DV");

            JSONObject result = httpUtils.getJsonFromUriRequest(uriBuilder);

            expect(result.getString("Time Series (Daily)")).toBeNotNull();
        });
    });

}}


