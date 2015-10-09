package no.imr.nmdapi.client.loader.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import no.imr.nmd.commons.dataset.jaxb.DataTypeEnum;
import no.imr.nmd.commons.dataset.jaxb.DatasetType;
import no.imr.nmdapi.client.loader.dao.EchosounderDAO;
import no.imr.nmdapi.client.loader.pojo.EchosounderDataset;
import no.imr.nmdapi.dao.file.NMDDatasetDao;
import no.imr.nmdapi.lib.nmdapipathgenerator.PathGenerator;
import no.imr.nmdapi.lib.nmdapipathgenerator.TypeValue;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author sjurl
 */
@Service("getAllEchosounderDatasets")
public class GetAllEchosounderDatasets {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(GetAllEchosounderDatasets.class);

    @Autowired
    private EchosounderDAO dao;

    @Autowired
    private NMDDatasetDao datasetDAO;

    public List<String> getDatasets() {

        List<String> updatedEchosounderDatasets = new ArrayList<>();
        List<EchosounderDataset> allEchosounderDatasets = dao.getEchosounderDatasets();
        for (EchosounderDataset echosounderDataset : allEchosounderDatasets) {
            PathGenerator pathGenerator = new PathGenerator();
            Map<String, TypeValue> typevalues = dao.getCruisePlatform(echosounderDataset.getMissionId());
            String platformPath = pathGenerator.createPlatformURICode(typevalues);
            DatasetType dataset = datasetDAO.getDatasetByName(DataTypeEnum.ECHOSOUNDER, "data", echosounderDataset.getMissionType(), echosounderDataset.getStartYear(), platformPath, echosounderDataset.getCruisecode().toString());
            if (dataset != null) {
                Date lastUpdated = dao.getLastUpdated(echosounderDataset.getId());
                if (lastUpdated.after(dataset.getUpdated().toGregorianCalendar().getTime())) {
                    LOGGER.info("found dataset, time on dataset: " + dataset.getUpdated().toString() + "  time on db: " + lastUpdated.toString());
                    updatedEchosounderDatasets.add(echosounderDataset.getId());
                }
            } else {
                updatedEchosounderDatasets.add(echosounderDataset.getId());
            }
        }
        return updatedEchosounderDatasets;
    }
}
