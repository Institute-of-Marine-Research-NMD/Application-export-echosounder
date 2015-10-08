package no.imr.nmdapi.client.loader.config;

import no.imr.messaging.processor.ExceptionProcessor;
import no.imr.nmdapi.exceptions.CantWriteFileException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.routepolicy.quartz.CronScheduledRoutePolicy;
import org.apache.camel.spring.javaconfig.SingleRouteCamelConfiguration;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for camel
 *
 * @author sjurl
 */
@Configuration
public class CamelConfig extends SingleRouteCamelConfiguration implements InitializingBean {

    @Autowired
    @Qualifier("configuration")
    private org.apache.commons.configuration.Configuration config;

    @Override
    public RouteBuilder route() {
        return new RouteBuilder() {

            @Override
            public void configure() {
                CronScheduledRoutePolicy startPolicy = new CronScheduledRoutePolicy();
                startPolicy.setRouteStartTime(config.getString("cron.activation.time"));

                onException(CantWriteFileException.class).continued(true).process(new ExceptionProcessor(config.getString("application.name"))).to("jms:queue:".concat(config.getString("queue.outgoing.error")));
                from("timer://runOnce?repeatCount=1&delay=5000").routePolicy(startPolicy)//.noAutoStartup()
                        .to("getAllEchosounderDatasets")
                        .split(body())
                        .to("echosounderLoaderService")
                        .to("jms:queue:".concat(config.getString("queue.outgoing.update-dataset")));
            }
        };
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // Do nothing as we don't set any properties
    }

}
