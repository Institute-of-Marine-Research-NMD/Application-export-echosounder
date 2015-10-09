package no.imr.nmdapi.client.loader.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import no.imr.nmd.commons.dataset.jaxb.DataTypeEnum;
import no.imr.nmd.commons.dataset.jaxb.DatasetType;
import no.imr.nmd.commons.dataset.jaxb.DatasetsType;
import no.imr.nmdapi.client.loader.dao.EchosounderDAO;
import no.imr.nmdapi.client.loader.pojo.EchosounderDataset;
import no.imr.nmdapi.lib.nmdapipathgenerator.PathGenerator;
import no.imr.nmdapi.lib.nmdapipathgenerator.TypeValue;
import org.apache.commons.configuration.Configuration;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 *
 * @author sjurl
 */
@Service("getAllEchosounderDatasets")
public class GetAllEchosounderDatasets {

    private static final String DATASET_JAXB_PATH = "no.imr.nmd.commons.dataset.jaxb";
    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(GetAllEchosounderDatasets.class);

    @Autowired
    private EchosounderDAO dao;

    @Autowired
    @Qualifier("configuration")
    private Configuration config;

    public List<String> getDatasets() {

        List<String> updatedEchosounderDatasets = new ArrayList<>();
        List<EchosounderDataset> allEchosounderDatasets = dao.getEchosounderDatasets();
        for (EchosounderDataset echosounderDataset : allEchosounderDatasets) {
            PathGenerator pathGenerator = new PathGenerator();
            Map<String, TypeValue> typevalues = dao.getCruisePlatform(echosounderDataset.getMissionId());
            String platformPath = pathGenerator.createPlatformURICode(typevalues);
            File datasetFile = pathGenerator.generatePath(config.getString("file.location"), echosounderDataset.getMissionType(),
                    echosounderDataset.getStartYear(), platformPath, echosounderDataset.getCruisecode().toString(), null);

            if (datasetFile.exists()) {
                DatasetsType dataset = unmarshallDataset(datasetFile);
                Date lastUpdated = dao.getLastUpdated(echosounderDataset.getId());
                boolean found = false;
                for (DatasetType datasetType : dataset.getDataset()) {
                    if (datasetType.getDataType().equals(DataTypeEnum.ECHOSOUNDER) && lastUpdated.after(datasetType.getUpdated().toGregorianCalendar().getTime())) {
                        LOGGER.info("found dataset, time on dataset: " + datasetType.getUpdated().toString() + "  time on db: " + lastUpdated.toString());
                        updatedEchosounderDatasets.add(echosounderDataset.getId());
                        found = true;
                    } else if (datasetType.getDataType().equals(DataTypeEnum.ECHOSOUNDER) && !lastUpdated.after(datasetType.getUpdated().toGregorianCalendar().getTime())) {
                        found = true;
                    }
                }

                if (!found) {
                    updatedEchosounderDatasets.add(echosounderDataset.getId());
                }
            } else {
                updatedEchosounderDatasets.add(echosounderDataset.getId());
            }
        }
        return updatedEchosounderDatasets;
    }

    private DatasetsType unmarshallDataset(File dataseFile) {
        try {
            JAXBContext ctx = JAXBContext.newInstance(DATASET_JAXB_PATH);
            Unmarshaller unmarshaller = ctx.createUnmarshaller();
            Object obj = unmarshaller.unmarshal(dataseFile);
            if (obj instanceof JAXBElement) {
                return (DatasetsType) ((JAXBElement) obj).getValue();
            } else {
                return (DatasetsType) obj;
            }
        } catch (JAXBException ex) {
            LOGGER.info(null, ex);
        }
        return null;
    }
}
