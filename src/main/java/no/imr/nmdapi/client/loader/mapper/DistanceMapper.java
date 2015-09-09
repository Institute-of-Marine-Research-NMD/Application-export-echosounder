package no.imr.nmdapi.client.loader.mapper;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import no.imr.nmdapi.client.loader.pojo.Distance;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author sjurl
 */
public class DistanceMapper implements RowMapper<Distance> {

    @Override
    public Distance mapRow(ResultSet rs, int rowNum) throws SQLException {
        Distance dist = new Distance();
        dist.setId(rs.getString("id"));
        BigDecimal bchanth = rs.getBigDecimal("bot_ch_thickness");
        if (bchanth == null || bchanth.doubleValue() == 0.0) {
            dist.setBotChThickness("");
        } else {
            DecimalFormat format = new DecimalFormat("#.0");
            dist.setBotChThickness(format.format(rs.getDouble("bot_ch_thickness")));
        }
        dist.setIncludeEstimate(BigInteger.valueOf(rs.getLong("include_estimate")));
        dist.setIntegratorDist(BigDecimal.valueOf(rs.getDouble("integrator_dist")));
        dist.setLatStart(BigDecimal.valueOf(rs.getDouble("lat_start")));

        BigDecimal latstop = rs.getBigDecimal("lat_stop");
        if (latstop == null) {
            dist.setLatStop("");
        } else {
            dist.setLatStop(BigDecimal.valueOf(rs.getDouble("lat_stop")).toPlainString());
        }
//        dist.setLatStop(BigDecimal.valueOf(rs.getDouble("lat_stop")).toPlainString());
        dist.setLonStart(BigDecimal.valueOf(rs.getDouble("lon_start")));

        BigDecimal lonstop = rs.getBigDecimal("lon_stop");
        if (lonstop == null) {
            dist.setLonStop("");
        } else {
            dist.setLonStop(BigDecimal.valueOf(rs.getDouble("lon_stop")).toPlainString());
        }
//        dist.setLonStop(BigDecimal.valueOf(rs.getDouble("lon_stop")).toPlainString());
        dist.setPelChThickness(BigDecimal.valueOf(rs.getDouble("pel_ch_thickness")));
        dist.setLogStart(BigDecimal.valueOf(rs.getDouble("log_start")));
        Calendar tzCal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        Timestamp starttimestamp = rs.getTimestamp("start_time", tzCal);

        if (starttimestamp != null) {
            Date starttime = new Date(starttimestamp.getTime());
            dist.setStartTime(sdf.format(starttime));
        }
        Timestamp endtimestamp = rs.getTimestamp("stop_time", tzCal);
        if (endtimestamp != null) {
            Date endtime = new Date(endtimestamp.getTime());
            dist.setStopTime(sdf.format(endtime));
        }

        return dist;
    }

}
