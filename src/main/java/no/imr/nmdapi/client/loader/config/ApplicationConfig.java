package no.imr.nmdapi.client.loader.config;

import no.imr.nmdapi.client.loader.route.GenerateAll;
import no.imr.nmdapi.client.loader.route.GenerateUpdated;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.configuration.reloading.ReloadingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration for the application
 *
 * @author sjurl
 */
@Configuration
public class ApplicationConfig {

    private static final String CATALINA_BASE = "catalina.base";

    /**
     * Configuration object for communicating with property data.
     *
     * @return Configuration object containg properties.
     * @throws ConfigurationException Error during instansiation.
     */
    @Bean(name = "configuration")
    public PropertiesConfiguration configuration() throws ConfigurationException {
        PropertiesConfiguration configuration = new PropertiesConfiguration(System.getProperty(CATALINA_BASE) + "/conf/export_echosounder_loader.properties");
        ReloadingStrategy reloadingStrategy = new FileChangedReloadingStrategy();
        configuration.setReloadingStrategy(reloadingStrategy);
        return configuration;
    }

    @Bean
    public GenerateAll generateAllRoute() {
        return new GenerateAll();
    }

    @Bean
    public GenerateUpdated generateUpdatedRoute() {
        return new GenerateUpdated();
    }
}
