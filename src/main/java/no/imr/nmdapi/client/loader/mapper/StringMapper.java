package no.imr.nmdapi.client.loader.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

/**
 * maps id's from the database to String objects
 *
 * @author sjurl
 */
public class StringMapper implements RowMapper<String> {

    @Override
    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
        return rs.getString("id");
    }

}
