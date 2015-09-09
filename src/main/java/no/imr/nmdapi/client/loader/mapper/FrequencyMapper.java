package no.imr.nmdapi.client.loader.mapper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.ResultSet;
import java.sql.SQLException;
import no.imr.nmdapi.client.loader.pojo.Frequency;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author sjurl
 */
public class FrequencyMapper implements RowMapper<Frequency> {

    @Override
    public Frequency mapRow(ResultSet rs, int rowNum) throws SQLException {
        Frequency freq = new Frequency();
        freq.setBubbleCorr(BigDecimal.valueOf(rs.getDouble("bubble_corr")));
        freq.setId(rs.getString("id"));
        freq.setFreq(BigInteger.valueOf(rs.getInt("freq")));
        freq.setLowerIntegratorDepth(BigDecimal.valueOf(rs.getDouble("lower_integrator_depth")));
        freq.setLowerInterpretDepth(BigDecimal.valueOf(rs.getDouble("lower_interpret_depth")));
        freq.setMaxBotDepth(BigDecimal.valueOf(rs.getDouble("max_bot_depth")));
        freq.setMinBotDepth(BigDecimal.valueOf(rs.getDouble("min_bot_depth")));
        freq.setNumBotCh(BigInteger.valueOf(rs.getInt("num_bot_ch")));
        freq.setNumPelCh(BigInteger.valueOf(rs.getInt("num_pel_ch")));
        freq.setQuality(BigInteger.valueOf(rs.getInt("quality")));
        freq.setThreshold(BigDecimal.valueOf(rs.getDouble("threshold")));
        freq.setTranceiver(BigInteger.valueOf(rs.getInt("tranceiver")));
        freq.setUpperIntegratorDepth(BigDecimal.valueOf(rs.getDouble("upper_integrator_depth")));
        freq.setUpperInterpretDepth(BigDecimal.valueOf(rs.getDouble("upper_interpret_depth")));
        return freq;
    }

}
