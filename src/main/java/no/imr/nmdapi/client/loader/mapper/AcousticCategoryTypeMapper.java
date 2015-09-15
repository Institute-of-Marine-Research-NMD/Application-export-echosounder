package no.imr.nmdapi.client.loader.mapper;

import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import no.imr.nmdapi.generic.nmdechosounder.domain.luf20.AcocatType;
import org.springframework.jdbc.core.RowMapper;

/**
 * Maps acoustic categories to AcocatType objects
 *
 * @author sjurl
 */
public class AcousticCategoryTypeMapper implements RowMapper<AcocatType> {

    @Override
    public AcocatType mapRow(ResultSet rs, int rowNum) throws SQLException {
        AcocatType ac = new AcocatType();
        ac.setAcocat(BigInteger.valueOf(rs.getInt("acousticcategory")));
        ac.setPurpose(BigInteger.valueOf(rs.getInt("purpose")));
        return ac;
    }

}
