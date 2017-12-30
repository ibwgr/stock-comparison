package ch.ibw.nds.appl2017.external;

import com.mscharhag.oleaster.runner.OleasterRunner;
import org.apache.http.client.utils.URIBuilder;
import org.json.JSONObject;
import org.junit.runner.RunWith;

import static com.mscharhag.oleaster.matcher.Matchers.expect;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.describe;
import static com.mscharhag.oleaster.runner.StaticRunnerSupport.it;

@RunWith(OleasterRunner.class)
public class HttpUtilsTest {{

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


