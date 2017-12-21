package ch.ibw.nds.appl2017.service;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.*;


public class ComparisonControllerIntegrationTest {

    private static final String SERVICE_URL
            = "http://localhost:8080/stock-comparison-1.0-SNAPSHOT/rest/performance2?stock=NESN&stock=GOOGL&stock=ORCL&stock=LISN&dateFrom=20130313&dateTo=20171231";


    // todo : https://blog.codecentric.de/en/2012/05/writing-lightweight-rest-integration-tests-with-the-jersey-test-framework/

    @Test
    @Ignore   // todo
    public void givenGetAllEmployees_whenCorrectRequest_thenResponseCodeSuccess()
            throws ClientProtocolException, IOException {

        HttpUriRequest request = new HttpGet(SERVICE_URL);

        HttpResponse httpResponse = HttpClientBuilder
                .create()
                .build()
                .execute(request);

        // todo temp.
        HttpEntity entity = httpResponse.getEntity();
        String responseString = EntityUtils.toString(entity, "UTF-8");
        System.out.println(responseString);

        assertEquals(httpResponse
                .getStatusLine()
                .getStatusCode(), HttpStatus.SC_OK);
    }

}
