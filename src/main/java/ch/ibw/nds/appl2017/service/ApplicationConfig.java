package ch.ibw.nds.appl2017.service;

import org.glassfish.jersey.jackson.JacksonFeature;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

// wichtige Info: die JacksonFeature.class kommt nicht von uns, muss aber zwingend hier fuer Jersey
// registriert werden, sonst ist es nicht moeglich eine JSON Response zu generieren

@ApplicationPath("/rest")
public class ApplicationConfig extends Application {
    public Set<Class<?>> getClasses() {
        return new HashSet<Class<?>>(Arrays.asList(RESTfulCorrelation.class, RESTfulPerformance.class, RESTfulPerformance2.class, JacksonFeature.class));
    }
}