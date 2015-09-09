package no.imr.nmdapi.client.loader.mapper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import no.imr.nmdapi.client.loader.pojo.Sa;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author sjurl
 */
public class SaMapper implements RowMapper<Sa> {

    @Override
    public Sa mapRow(ResultSet rs, int rowNum) throws SQLException {
        Sa sa = new Sa();
        sa.setAcousticcategory(BigInteger.valueOf(rs.getInt("acousticcategory")));
        sa.setCh(BigInteger.valueOf(rs.getInt("ch")));
        sa.setChType(rs.getString("ch_type"));
        sa.setSa(BigDecimal.valueOf(rs.getDouble("sa")));
        return sa;
    }

}
