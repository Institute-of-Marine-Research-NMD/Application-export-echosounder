package no.imr.nmdapi.client.loader.pojo;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 *
 * @author sjurl
 */
public class Distance {

    private String id;
    private String botChThickness;
    private BigInteger includeEstimate;
    private BigDecimal integratorDist;
    private BigDecimal latStart;
    private String latStop;
    private BigDecimal lonStart;
    private String lonStop;
    private BigDecimal pelChThickness;
    private BigDecimal logStart;
    private String startTime;
    private String stopTime;

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
     * @return the botChThickness
     */
    public String getBotChThickness() {
        return botChThickness;
    }

    /**
     * @param botChThickness the botChThickness to set
     */
    public void setBotChThickness(String botChThickness) {
        this.botChThickness = botChThickness;
    }

    /**
     * @return the includeEstimate
     */
    public BigInteger getIncludeEstimate() {
        return includeEstimate;
    }

    /**
     * @param includeEstimate the includeEstimate to set
     */
    public void setIncludeEstimate(BigInteger includeEstimate) {
        this.includeEstimate = includeEstimate;
    }

    /**
     * @return the integratorDist
     */
    public BigDecimal getIntegratorDist() {
        return integratorDist;
    }

    /**
     * @param integratorDist the integratorDist to set
     */
    public void setIntegratorDist(BigDecimal integratorDist) {
        this.integratorDist = integratorDist;
    }

    /**
     * @return the latStart
     */
    public BigDecimal getLatStart() {
        return latStart;
    }

    /**
     * @param latStart the latStart to set
     */
    public void setLatStart(BigDecimal latStart) {
        this.latStart = latStart;
    }

    /**
     * @return the latStop
     */
    public String getLatStop() {
        return latStop;
    }

    /**
     * @param latStop the latStop to set
     */
    public void setLatStop(String latStop) {
        this.latStop = latStop;
    }

    /**
     * @return the lonStart
     */
    public BigDecimal getLonStart() {
        return lonStart;
    }

    /**
     * @param lonStart the lonStart to set
     */
    public void setLonStart(BigDecimal lonStart) {
        this.lonStart = lonStart;
    }

    /**
     * @return the lonStop
     */
    public String getLonStop() {
        return lonStop;
    }

    /**
     * @param lonStop the lonStop to set
     */
    public void setLonStop(String lonStop) {
        this.lonStop = lonStop;
    }

    /**
     * @return the pelChThickness
     */
    public BigDecimal getPelChThickness() {
        return pelChThickness;
    }

    /**
     * @param pelChThickness the pelChThickness to set
     */
    public void setPelChThickness(BigDecimal pelChThickness) {
        this.pelChThickness = pelChThickness;
    }

    /**
     * @return the logStart
     */
    public BigDecimal getLogStart() {
        return logStart;
    }

    /**
     * @param logStart the logStart to set
     */
    public void setLogStart(BigDecimal logStart) {
        this.logStart = logStart;
    }

    /**
     * @return the startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the stopTime
     */
    public String getStopTime() {
        return stopTime;
    }

    /**
     * @param stopTime the stopTime to set
     */
    public void setStopTime(String stopTime) {
        this.stopTime = stopTime;
    }
}
