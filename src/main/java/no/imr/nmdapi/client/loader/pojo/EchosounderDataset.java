package no.imr.nmdapi.client.loader.pojo;

import java.math.BigInteger;

/**
 *
 * @author sjurl
 */
public class EchosounderDataset {

    private String id;
    private String cruisecode;
    private String lsssVersion;
    private BigInteger nationioc;
    private BigInteger platform;
    private String reportTime;
    private String startYear;
    private String missionType;
    private String missionId;

    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the cruisecode
     */
    public String getCruisecode() {
        return cruisecode;
    }

    /**
     * @param cruisecode the cruisecode to set
     */
    public void setCruisecode(String cruisecode) {
        this.cruisecode = cruisecode;
    }

    /**
     * @return the lsssVersion
     */
    public String getLsssVersion() {
        return lsssVersion;
    }

    /**
     * @param lsssVersion the lsssVersion to set
     */
    public void setLsssVersion(String lsssVersion) {
        this.lsssVersion = lsssVersion;
    }

    /**
     * @return the nationioc
     */
    public BigInteger getNationioc() {
        return nationioc;
    }

    /**
     * @param nationioc the nationioc to set
     */
    public void setNationioc(BigInteger nationioc) {
        this.nationioc = nationioc;
    }

    /**
     * @return the platform
     */
    public BigInteger getPlatform() {
        return platform;
    }

    /**
     * @param platform the platform to set
     */
    public void setPlatform(BigInteger platform) {
        this.platform = platform;
    }

    /**
     * @return the reportTime
     */
    public String getReportTime() {
        return reportTime;
    }

    /**
     * @param reportTime the reportTime to set
     */
    public void setReportTime(String reportTime) {
        this.reportTime = reportTime;
    }

    /**
     * @return the missionType
     */
    public String getMissionType() {
        return missionType;
    }

    /**
     * @param missionType the missionType to set
     */
    public void setMissionType(String missionType) {
        this.missionType = missionType;
    }

    /**
     * @return the startYear
     */
    public String getStartYear() {
        return startYear;
    }

    /**
     * @param startYear the startYear to set
     */
    public void setStartYear(String startYear) {
        this.startYear = startYear;
    }

    /**
     * @return the missionId
     */
    public String getMissionId() {
        return missionId;
    }

    /**
     * @param missionId the missionId to set
     */
    public void setMissionId(String missionId) {
        this.missionId = missionId;
    }
}
