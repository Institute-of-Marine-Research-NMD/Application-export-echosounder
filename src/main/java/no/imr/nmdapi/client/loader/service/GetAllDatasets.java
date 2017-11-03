/*
 */
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
public class GetAllDatasets {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(GetAllDatasets.class);

    @Autowired
    private EchosounderDAO dao;

    public List<String> getDatasets() {
        LOGGER.info("Started creating echosounder dataset list");
        List<String> updatedEchosounderDatasets = new ArrayList<>();
        List<EchosounderDataset> allEchosounderDatasets = dao.getEchosounderDatasets();
        for (EchosounderDataset echosounderDataset : allEchosounderDatasets) {
            updatedEchosounderDatasets.add(echosounderDataset.getId());
            LOGGER.info(echosounderDataset.getId());
        }
        LOGGER.info("Done creating datasets list");
        return updatedEchosounderDatasets;

    }
}
