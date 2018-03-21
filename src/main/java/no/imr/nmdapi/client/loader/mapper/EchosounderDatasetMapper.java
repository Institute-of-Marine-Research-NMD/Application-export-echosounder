/*
 */
package no.imr.nmdapi.client.loader.mapper;

import java.math.BigInteger;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import no.imr.nmdapi.client.loader.pojo.EchosounderDataset;
import org.springframework.jdbc.core.RowMapper;

/**
 * Maps echosounder datasets from the database to EchosounderDataset objects
 *
 * @author sjurl
 */
public class EchosounderDatasetMapper implements RowMapper<EchosounderDataset> {

    @Override
    public EchosounderDataset mapRow(ResultSet rs, int rowNum) throws SQLException {
        EchosounderDataset type = new EchosounderDataset();
        type.setId(rs.getString("id"));
        type.setCruisecode(rs.getString("cruisecode"));
        type.setLsssVersion(rs.getString("lsss_version"));
        type.setNationioc(BigInteger.valueOf(rs.getInt("nationioc")));
        type.setPlatform(BigInteger.valueOf(rs.getInt("platform")));
        type.setMissionType(rs.getString("description"));
        type.setStartYear(String.valueOf(rs.getInt("startyear")));
        type.setMissionId(rs.getString("missionid"));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS");
        Timestamp repordate = rs.getTimestamp("report_time");

        if (repordate != null) {
            Date date = new Date(repordate.getTime());
            type.setReportTime(sdf.format(date));
        }

        return type;
    }

}
