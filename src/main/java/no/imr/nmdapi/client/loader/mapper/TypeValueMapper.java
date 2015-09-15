package no.imr.nmdapi.client.loader.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import no.imr.nmdapi.lib.nmdapipathgenerator.TypeValue;

import org.springframework.jdbc.core.RowMapper;

/**
 * Maps key value pairs from the database to TypeValue objects
 *
 * @author Terry Hannant <a5119>
 */
public class TypeValueMapper implements RowMapper<TypeValue> {

    private final String typeName;
    private final String valueName;

    public TypeValueMapper(String typeName, String valueName) {
        this.typeName = typeName;
        this.valueName = valueName;
    }

    @Override
    public TypeValue mapRow(ResultSet rs, int rowNum) throws SQLException {
        TypeValue platform = new TypeValue();

        platform.setType(rs.getString(typeName));
        platform.setValue(rs.getString(valueName));

        return platform;
    }

}
