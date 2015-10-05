package no.imr.nmdapi.client.loader.service;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import no.imr.nmd.commons.dataset.jaxb.DataTypeEnum;
import no.imr.nmd.commons.dataset.jaxb.DatasetsType;
import no.imr.nmd.commons.dataset.jaxb.QualityEnum;
import no.imr.nmdapi.client.loader.dao.EchosounderDAO;
import no.imr.nmdapi.client.loader.pojo.EchosounderDataset;
import no.imr.nmdapi.client.loader.pojo.Frequency;
import no.imr.nmdapi.client.loader.pojo.Sa;
import no.imr.nmdapi.generic.nmdechosounder.domain.luf20.AcocatListType;
import no.imr.nmdapi.generic.nmdechosounder.domain.luf20.AcocatType;
import no.imr.nmdapi.generic.nmdechosounder.domain.luf20.ChTypeType;
import no.imr.nmdapi.generic.nmdechosounder.domain.luf20.Distance;
import no.imr.nmdapi.generic.nmdechosounder.domain.luf20.DistanceList;
import no.imr.nmdapi.generic.nmdechosounder.domain.luf20.EchosounderDatasetType;
import no.imr.nmdapi.generic.nmdechosounder.domain.luf20.FrequencyType;
import no.imr.nmdapi.generic.nmdechosounder.domain.luf20.SaByAcocatType;
import no.imr.nmdapi.generic.nmdechosounder.domain.luf20.SaType;
import no.imr.nmdapi.lib.nmdapipathgenerator.PathGenerator;
import no.imr.nmdapi.lib.nmdapipathgenerator.TypeValue;
import org.apache.camel.Exchange;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.io.FileUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Service that loads echosounder dataset from the database into xml files
 *
 * @author sjurl
 */
@Service(value = "echosounderLoaderService")
public class EchsounderLoaderService {

    private static final String DATASET_CONTAINER_DELIMITER = "/";
    
    @Autowired
    private EchosounderDAO dao;

    @Autowired
    @Qualifier("configuration")
    private Configuration config;

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(EchsounderLoaderService.class);

    /**
     * Loads all echosounder datasets into xml files
     *
     * @param ex
     */
    public void loadEchosounderToFile(Exchange ex) {
        EchosounderDataset echosounderDataset = ex.getIn().getBody(EchosounderDataset.class);
        EchosounderDatasetType datasetType = generateEchosounderDatasetType(echosounderDataset);

        PathGenerator pathGenerator = new PathGenerator();
        Map<String, TypeValue> typevalues = dao.getCruisePlatformAfterStart(echosounderDataset.getMissionId());
        String platformPath = pathGenerator.createPlatformURICode(typevalues);
        File outputFile = pathGenerator.generatePath(config.getString("file.location"), echosounderDataset.getMissionType(),
                echosounderDataset.getStartYear(), platformPath, echosounderDataset.getCruisecode().toString(), "echosounder");
        writeToFile(datasetType, outputFile);
        ex.getOut().setHeader("imr:datatype", DataTypeEnum.ECHOSOUNDER.toString());
        ex.getOut().setHeader("imr:datasetname", "data");
        ex.getOut().setHeader("imr:owner", "imr");
        ex.getOut().setHeader("imr:read", "unrestricted");
        ex.getOut().setHeader("imr:write", "SG-ECHOSOUNDER-WRITE");
        ex.getOut().setHeader("imr:qualityassured", QualityEnum.NONE.toString());
        ex.getOut().setHeader("imr:updated", getXMLGregorianCalendar().toString());
        ex.getOut().setHeader("imr:datasetscontainer", echosounderDataset.getMissionType().concat(DATASET_CONTAINER_DELIMITER).concat(echosounderDataset.getStartYear()).
                concat(DATASET_CONTAINER_DELIMITER).concat(platformPath).concat(DATASET_CONTAINER_DELIMITER).concat(echosounderDataset.getCruisecode().toString()));
        LOGGER.info("Wrote file: " + datasetType.getCruise().toString());
    }

    private EchosounderDatasetType generateEchosounderDatasetType(EchosounderDataset echosounderDataset) {
        EchosounderDatasetType datasetType = new EchosounderDatasetType();
        datasetType.setCruise(echosounderDataset.getCruisecode());
        datasetType.setLsssVersion(echosounderDataset.getLsssVersion());
        datasetType.setNation(echosounderDataset.getNationioc());
        datasetType.setPlatform(echosounderDataset.getPlatform());
        datasetType.setReportTime(echosounderDataset.getReportTime());
        DistanceList distanceList = generateDistances(echosounderDataset);
        datasetType.setDistanceList(distanceList);
        AcocatListType acocatList = new AcocatListType();
        List<AcocatType> acoustcatType = dao.getAcousticCategory(echosounderDataset.getId());
        acocatList.getAcocat().addAll(acoustcatType);
        Collections.sort(acocatList.getAcocat(), new Comparator<AcocatType>() {

            @Override
            public int compare(AcocatType o1, AcocatType o2) {
                if (o1.getAcocat().intValue() > o2.getAcocat().intValue()) {
                    return 1;
                } else if (o1.getAcocat().intValue() < o2.getAcocat().intValue()) {
                    return -1;
                }
                return 0;
            }
        });
        datasetType.setAcocatList(acocatList);
        return datasetType;
    }

    private DistanceList generateDistances(EchosounderDataset echosounderDataset) {
        List<no.imr.nmdapi.client.loader.pojo.Distance> distances = dao.getDistanceList(echosounderDataset.getId());
        DistanceList distanceList = new DistanceList();
        for (no.imr.nmdapi.client.loader.pojo.Distance distance : distances) {
            Distance dist = new Distance();
            dist.setBotChThickness(distance.getBotChThickness());
            dist.setIncludeEstimate(distance.getIncludeEstimate());
            dist.setIntegratorDist(distance.getIntegratorDist());
            dist.setLatStart(distance.getLatStart());
            dist.setLatStop(distance.getLatStop());
            dist.setLogStart(distance.getLogStart());
            dist.setLonStart(distance.getLonStart());
            dist.setLonStop(distance.getLonStop());
            dist.setPelChThickness(distance.getPelChThickness());
            dist.setStartTime(distance.getStartTime());
            dist.setStopTime(distance.getStopTime());

            generateFrequencies(distance, dist);

            distanceList.getDistance().add(dist);
        }
        return distanceList;
    }

    private void generateFrequencies(no.imr.nmdapi.client.loader.pojo.Distance distance, Distance dist) {
        List<Frequency> frequencies = dao.getFrequenciesList(distance.getId());
        for (Frequency frequency : frequencies) {
            FrequencyType freqType = new FrequencyType();
            freqType.setBubbleCorr(frequency.getBubbleCorr());
            freqType.setFreq(frequency.getFreq());
            freqType.setLowerIntegratorDepth(frequency.getLowerIntegratorDepth());
            freqType.setLowerInterpretDepth(frequency.getLowerInterpretDepth());
            freqType.setMaxBotDepth(frequency.getMaxBotDepth());
            freqType.setMinBotDepth(frequency.getMinBotDepth());
            freqType.setNumBotCh(frequency.getNumBotCh());
            freqType.setNumPelCh(frequency.getNumPelCh());
            freqType.setQuality(frequency.getQuality());
            freqType.setThreshold(frequency.getThreshold());
            freqType.setTranceiver(frequency.getTranceiver());
            freqType.setUpperIntegratorDepth(frequency.getUpperIntegratorDepth());
            freqType.setUpperInterpretDepth(frequency.getUpperInterpretDepth());

            generateSA(frequency, freqType);
            dist.setFrequency(freqType);
        }
    }

    private void generateSA(Frequency frequency, FrequencyType freqType) {
        List<Sa> sas = dao.getSaForFrequency(frequency.getId());
        ChTypeType pelagictype = new ChTypeType();
        pelagictype.setType("P");
        ChTypeType bottomType = new ChTypeType();
        bottomType.setType("B");
        Map<BigInteger, List<Sa>> categories = new HashMap<>();
        for (Sa sa : sas) {
            if (categories.containsKey(sa.getAcousticcategory())) {
                categories.get(sa.getAcousticcategory()).add(sa);
            } else {
                List<Sa> acocat = new ArrayList<>();
                acocat.add(sa);
                categories.put(sa.getAcousticcategory(), acocat);
            }
        }

        addSAToCorrectAcousticCategory(categories, bottomType, pelagictype);
        sortAcousticCategories(pelagictype);
        sortAcousticCategories(bottomType);
        if (!pelagictype.getSaByAcocat().isEmpty()) {
            freqType.getChType().add(pelagictype);
        }
        if (!bottomType.getSaByAcocat().isEmpty()) {
            freqType.getChType().add(bottomType);
        }
    }

    private void addSAToCorrectAcousticCategory(Map<BigInteger, List<Sa>> categories, ChTypeType bottomType, ChTypeType pelagictype) {
        for (BigInteger acocat : categories.keySet()) {
            SaByAcocatType acousticcateogoryPelagic = new SaByAcocatType();
            acousticcateogoryPelagic.setAcocat(acocat);
            SaByAcocatType acousticcateogoryBottom = new SaByAcocatType();
            acousticcateogoryBottom.setAcocat(acocat);
            List<Sa> sabyacos = categories.get(acocat);
            for (Sa sabyaco : sabyacos) {
                SaType satype = new SaType();
                satype.setCh(sabyaco.getCh());
                satype.setValue(sabyaco.getSa());
                if ("B".equals(sabyaco.getChType())) {
                    acousticcateogoryBottom.getSa().add(satype);
                } else if ("P".equals(sabyaco.getChType())) {
                    acousticcateogoryPelagic.getSa().add(satype);
                }
            }
            if (!acousticcateogoryBottom.getSa().isEmpty()) {
                sortSA(acousticcateogoryBottom);
                bottomType.getSaByAcocat().add(acousticcateogoryBottom);
            }
            if (!acousticcateogoryPelagic.getSa().isEmpty()) {
                sortSA(acousticcateogoryPelagic);
                pelagictype.getSaByAcocat().add(acousticcateogoryPelagic);
            }
        }
    }

    private void sortAcousticCategories(ChTypeType channelTypeType) {
        Collections.sort(channelTypeType.getSaByAcocat(), new Comparator<SaByAcocatType>() {
            @Override
            public int compare(SaByAcocatType o1, SaByAcocatType o2) {
                Integer o1Val = Integer.valueOf(o1.getAcocat().toString().substring(0, 1));
                Integer o2Val = Integer.valueOf(o2.getAcocat().toString().substring(0, 1));

                if (o1Val > o2Val) {
                    return 1;
                } else if (o1Val < o2Val) {
                    return -1;
                } else {
                    if (o1.getAcocat().intValue() > o2.getAcocat().intValue()) {
                        return 1;
                    } else if (o1.getAcocat().intValue() < o2.getAcocat().intValue()) {
                        return -1;
                    }
                }
                return 0;
            }
        });
    }

    private void sortSA(SaByAcocatType acousticcateogoryPelagic) {
        Collections.sort(acousticcateogoryPelagic.getSa(), new Comparator<SaType>() {

            @Override
            public int compare(SaType o1, SaType o2) {
                if (o1.getCh().intValue() > o2.getCh().intValue()) {
                    return 1;
                } else if (o1.getCh().intValue() < o2.getCh().intValue()) {
                    return -1;
                }
                return 0;
            }
        });
    }

    private void writeToFile(EchosounderDatasetType dataset, File path) {
        File newFile = new File(FileUtils.getTempDirectory().getAbsolutePath().concat(File.separator).concat(dataset.getCruise().toString()));
        File oldFile = new File(path.getAbsolutePath());

        writeLUF20File(dataset, newFile);
        if (newFile.exists() && oldFile.exists()) {
            try {
                if (FileUtils.checksumCRC32(oldFile) != FileUtils.checksumCRC32(newFile)) {
                    FileUtils.copyFile(newFile, oldFile);
                }
            } catch (IOException ex) {
                LOGGER.error("Error working on table ".concat(dataset.getCruise().toString()), ex);
            }
        } else if (newFile.exists() && !oldFile.exists()) {
            try {
                FileUtils.copyFile(newFile, oldFile);
            } catch (IOException ex) {
                LOGGER.error("Unable to write file " + oldFile.getAbsolutePath(), ex);
            }
        }
        newFile.delete();
        LOGGER.info("FINISHED with ".concat(dataset.getCruise().toString()));
    }

    private void writeLUF20File(EchosounderDatasetType dataset, File path) {
        try {
            JAXBContext ctx = JAXBContext.newInstance("no.imr.nmdapi.generic.nmdechosounder.domain.luf20");
            Marshaller marshaller = ctx.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(dataset, path);
        } catch (JAXBException ex) {
            Logger.getLogger(EchosounderDatasetType.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private XMLGregorianCalendar getXMLGregorianCalendar() {
        try {
            GregorianCalendar c = new GregorianCalendar();
            c.setTime(Calendar.getInstance().getTime());
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
        } catch (DatatypeConfigurationException ex) {
            Logger.getLogger(EchsounderLoaderService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private File getDatasetsFile(File outputFile) {
        File dir = outputFile.getParentFile().getParentFile();
        return new File(dir.getAbsolutePath().concat("/data.xml"));
    }

    private DatasetsType unmarshall(File datasetsFile) {
        try {
            JAXBContext ctx = JAXBContext.newInstance("no.imr.nmd.commons.dataset.jaxb");
            Unmarshaller unm = ctx.createUnmarshaller();
            Object o = unm.unmarshal(datasetsFile);
            if (o != null) {
                if (o instanceof JAXBElement) {
                    return (DatasetsType) ((JAXBElement) o).getValue();
                } else {
                    return (DatasetsType) o;
                }
            }
        } catch (JAXBException ex) {
            Logger.getLogger(EchsounderLoaderService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private void marshall(File datasetsFile, DatasetsType datasets) {
        try {
            JAXBContext ctx = JAXBContext.newInstance("no.imr.nmd.commons.dataset.jaxb");
            Marshaller marshaller = ctx.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            marshaller.marshal(datasets, datasetsFile);
        } catch (JAXBException ex) {
            Logger.getLogger(EchosounderDatasetType.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
