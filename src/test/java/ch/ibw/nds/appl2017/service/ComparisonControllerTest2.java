package ch.ibw.nds.appl2017.service;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

public class ComparisonControllerTest2 extends JerseyTest {

    @Override
    public Application configure() {
        return new ResourceConfig(ComparisonController.class);
    }

    // todo funktioniert nicht
//    @Test
//    public void test() {
//        Response output = target("/rest/comparison/performance?stock=NESN&stock=GOOGL&stock=ORCL&stock=LISN&dateFrom=20130313&dateTo=20171231").request().get();
//        assertEquals("should return status 200", 200, output.getStatus());
//        assertNotNull("Should return list", output.getEntity());
//    }

}