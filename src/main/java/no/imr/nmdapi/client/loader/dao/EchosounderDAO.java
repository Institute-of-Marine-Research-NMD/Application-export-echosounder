package no.imr.nmdapi.client.loader.dao;

import java.math.BigInteger;
import java.util.List;
import javax.sql.DataSource;
import no.imr.nmdapi.client.loader.mapper.AcousticCategoryTypeMapper;
import no.imr.nmdapi.client.loader.mapper.DistanceMapper;
import no.imr.nmdapi.client.loader.mapper.EchosounderDatasetMapper;
import no.imr.nmdapi.client.loader.mapper.FrequencyMapper;
import no.imr.nmdapi.client.loader.mapper.SaMapper;
import no.imr.nmdapi.client.loader.pojo.Distance;
import no.imr.nmdapi.client.loader.pojo.EchosounderDataset;
import no.imr.nmdapi.client.loader.pojo.Frequency;
import no.imr.nmdapi.client.loader.pojo.Sa;
import no.imr.nmdapi.generic.nmdechosounder.domain.luf20.AcocatType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Data access object for acoustic categories
 *
 * @author sjurl
 */
public class EchosounderDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    private static final String GET_ECHOSOUNDER_DATASET = "select ed.id, ed.report_time, ed.lsss_version, cm.cruisecode, na.nationioc, pl.platform "
            + "from nmdechosounder.echosounder_dataset ed, nmdmission.cruisemission cm, "
            + "nmdmission.mission mi, nmdreference.platform pl, "
            + "nmdreference.nation na "
            + "Where "
            + "ed.id_m_mission = mi.id and cm.id_mission = mi.id "
            + "and mi.id_r_platform = pl.id and pl.id_nation = na.id";

    private static final String GET_DISTANCE_LIST_FOR_DATASET = "select id, bot_ch_thickness, include_estimate, integrator_dist, lat_start, lat_stop, lon_start, lon_stop, pel_ch_thickness, start_time, stop_time, log_start "
            + "from nmdechosounder.distance where id_echosounder_dataset = ? order by start_time";

    private static final String GET_SA_FOR_FREQUENCY = "select s.sa, s.ch_type, s.ch, a.acousticcategory from "
            + "nmdechosounder.sa s, nmdreference.acousticcategory a"
            + " where s.id_acoustic_category = a.id and s.id_frequency = ? order by ch_type, acousticcategory, sa";

    private static final String GET_ACOUSTIC_CATEGORY = "select p.purpose, a.acousticcategory from "
            + "nmdechosounder.purpose p, nmdreference.acousticcategory a "
            + "where p.id_acoustic_category = a.id and "
            + "p.id_echosounder_dataset = ?";

    /**
     * Returns a list of acoustic categories
     *
     * @return
     */
    public List<EchosounderDataset> getEchosounderDatasets() {
        return jdbcTemplate.query(GET_ECHOSOUNDER_DATASET, new EchosounderDatasetMapper());
    }

    public List<Distance> getDistanceList(String id) {
        return jdbcTemplate.query(GET_DISTANCE_LIST_FOR_DATASET, new DistanceMapper(), id);
    }

    public List<Frequency> getFrequenciesList(String id) {
        return jdbcTemplate.query("select id, freq, tranceiver, threshold, num_pel_ch, num_bot_ch, min_bot_depth, max_bot_depth, upper_interpret_depth, lower_interpret_depth, upper_integrator_depth, lower_integrator_depth, quality, bubble_corr from nmdechosounder.frequency where id_distance = ?", new FrequencyMapper(), id);
    }

    public List<Sa> getSaForFrequency(String id) {
        return jdbcTemplate.query(GET_SA_FOR_FREQUENCY, new SaMapper(), id);
    }

//
//    public Date getLastChanged() {
//        return (Date) jdbcTemplate.query("SELECT max(last_edited) as last_edited FROM nmdreference.acousticcategory", new DateMapper()).get(0);
//    }
    public List<AcocatType> getAcousticCategory(String datasetId) {
        return jdbcTemplate.query(GET_ACOUSTIC_CATEGORY, new AcousticCategoryTypeMapper(), datasetId);
    }
}
