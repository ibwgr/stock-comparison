package ch.ibw.nds.appl2017.external;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.DefaultHttpResponseFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicStatusLine;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class HttpUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtils.class);

    private static final String UTF_8 = "UTF-8";

    private HttpUtils() {

    }

    public static HttpUtils create() {
        return new HttpUtils();
    }

    private JSONObject getJsonFromResponse(HttpResponse httpResponse) {
        JSONObject responseJson = new JSONObject();
        HttpEntity entity = httpResponse.getEntity();
        if (entity != null) {
            try {
                String responseString = EntityUtils.toString(entity, UTF_8);
                responseJson = new JSONObject(responseString);
            } catch (IOException | JSONException e) {
                LOGGER.trace("Parsing JSON response failed", e);
            }
        }
        return responseJson;
    }

    private HttpResponse getHttpResponse(HttpUriRequest request) {
        DefaultHttpResponseFactory factory = new DefaultHttpResponseFactory();
        HttpResponse httpResponse = factory.newHttpResponse(
                new BasicStatusLine(HttpVersion.HTTP_1_1
                        ,HttpStatus.SC_OK
                        ,null
                )
                ,null
        );
        try {

         // System.setProperty("javax.net.debug", "ssl");
         // System.setProperty("javax.net.ssl.trustStore", "H:\\DATEN\\IntelliJ-Play\\stock-comparison\\src\\main\\java\\ch\\ibw\\nds\\appl2017\\external\\www.alphavantage.ibw.jks");

            httpResponse = HttpClientBuilder
                    .create()
                    .build()
                    .execute(request);
        } catch (IOException e) {
            LOGGER.trace("Http request failed", e);
        }
        return httpResponse;
    }

    public JSONObject getJsonFromUriRequest(URIBuilder uriBuilder) {
        HttpUriRequest request = new HttpGet(uriBuilder.toString());
        HttpResponse httpResponse = getHttpResponse(request);
        return getJsonFromResponse(httpResponse);
    }
}
