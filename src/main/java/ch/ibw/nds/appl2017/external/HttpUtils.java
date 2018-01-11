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

import javax.ws.rs.WebApplicationException;
import java.io.IOException;

public class HttpUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpUtils.class);

    private static final String UTF_8 = "UTF-8";

    private HttpUtils() {

    }

    public static HttpUtils create() {
        return new HttpUtils();
    }

    public JSONObject getJsonFromResponse(HttpResponse httpResponse) {
        JSONObject responseJson = new JSONObject();
        HttpEntity entity = httpResponse.getEntity();
        if (entity != null) {
            try {
                String responseString = EntityUtils.toString(entity, UTF_8);
                responseJson = new JSONObject(responseString);
            } catch (IOException | JSONException e) {
                String errorMessage = "Parsing JSON response failed";
                LOGGER.error(errorMessage);
                throw new WebApplicationException(errorMessage, e);
            }
        }
        return responseJson;
    }

    public HttpResponse getHttpResponse(HttpUriRequest request) {
        DefaultHttpResponseFactory factory = new DefaultHttpResponseFactory();
        factory.newHttpResponse(
                new BasicStatusLine(HttpVersion.HTTP_1_1
                        , HttpStatus.SC_OK
                        , null
                )
                , null
        );
        HttpResponse httpResponse;
        try {
            httpResponse = HttpClientBuilder
                    .create()
                    .build()
                    .execute(request);
        } catch (IOException e) {
            String errorMessage = "Http request failed";
            LOGGER.error(errorMessage, e);
            throw new WebApplicationException(errorMessage, e);
        }
        return httpResponse;
    }

    public JSONObject getJsonFromUriRequest(URIBuilder uriBuilder) {
        HttpUriRequest request = new HttpGet(uriBuilder.toString());
        HttpResponse httpResponse = getHttpResponse(request);
        return getJsonFromResponse(httpResponse);
    }
}
