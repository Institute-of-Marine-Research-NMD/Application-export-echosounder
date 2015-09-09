package no.imr.nmdapi.client.loader.config;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.spring.javaconfig.SingleRouteCamelConfiguration;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for camel
 *
 * @author sjurl
 */
@Configuration
public class CamelConfig extends SingleRouteCamelConfiguration implements InitializingBean {

    @Override
    public RouteBuilder route() {
        return new RouteBuilder() {

            @Override
            public void configure() {
                from("timer://harvesttimer?fixedRate=true&period=86400000")
                        //                        .errorHandler(deadLetterChannel("jms:queue:dead").maximumRedeliveries(3).redeliveryDelay(30000))
                        .to("echosounderLoaderService");
//                        .to("log:end?level=INFO");
            }
        };
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }

}
