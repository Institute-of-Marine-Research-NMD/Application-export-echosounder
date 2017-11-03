package no.imr.nmdapi.client.loader.config;

import java.util.ArrayList;
import java.util.List;
import no.imr.nmdapi.client.loader.route.GenerateAll;
import no.imr.nmdapi.client.loader.route.GenerateUpdated;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spring.javaconfig.CamelConfiguration;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for camel
 *
 * @author sjurl
 */
@Configuration
public class CamelConfig extends CamelConfiguration implements InitializingBean {

    @Autowired
    private GenerateAll generateAllRoute;

    @Autowired
    private GenerateUpdated generateUpdatedRoute;

    @Override
    public List<RouteBuilder> routes() {
        List<RouteBuilder> routes = new ArrayList<>();
        routes.add(generateAllRoute);
        routes.add(generateUpdatedRoute);
        return routes;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // no properties loaded so not used
    }

}
