/*
 */
package no.imr.nmdapi.client.loader.route;

import java.util.ArrayList;
import no.imr.messaging.processor.ExceptionProcessor;
import no.imr.nmdapi.exceptions.CantWriteFileException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.util.UnsafeUriCharactersEncoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 *
 * @author sjurl
 */
public class GenerateAll extends RouteBuilder {

    @Autowired
    @Qualifier("configuration")
    private org.apache.commons.configuration.Configuration config;

    @Override
    public void configure() throws Exception {
        onException(CantWriteFileException.class).continued(true).process(new ExceptionProcessor(config.getString("application.name"))).to("jms:queue:".concat(config.getString("queue.outgoing.error")));

        from("jms:queue:".concat(config.getString("queue.incoming.echosounderRefresh")))
                .choice()
                .when(simple("${in.header.imr:refresh} == 'all' "))
                .to("getAllEchosounderDatasets")
                .split(body(ArrayList.class))
                //                        .parallelProcessing()
                .to("echosounderLoaderService")
                .multicast()
                .to("jms:queue:".concat(config.getString("queue.outgoing.update-dataset")),
                        "jms:queue:".concat(config.getString("queue.outgoing.success")));
    }

}
