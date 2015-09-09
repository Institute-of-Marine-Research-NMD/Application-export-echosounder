package no.imr.nmdapi.client.loader.config;

import javax.sql.DataSource;
import no.imr.nmdapi.client.loader.dao.EchosounderDAO;
import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class containing the datasources
 *
 * @author sjurl
 */
@Configuration
public class PersistenceConfig {

    @Autowired
    private org.apache.commons.configuration.Configuration config;

    @Bean
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();

        dataSource.setDriverClassName(config.getString("jdbc.driver"));
        dataSource.setUrl(config.getString("jdbc.url"));
        dataSource.setUsername(config.getString("jdbc.user"));
        dataSource.setPassword(config.getString("jdbc.password"));
        dataSource.setPassword(config.getString("jdbc.password"));

        return dataSource;
    }

    
    @Bean
    public EchosounderDAO echosounderDAO(){
        return new EchosounderDAO();
    }
}
