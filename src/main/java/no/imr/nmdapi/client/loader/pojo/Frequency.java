package no.imr.nmdapi.client.loader.pojo;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 *
 * @author sjurl
 */
public class Frequency {

    private String id;
    private BigDecimal threshold;
    private BigInteger numPelCh;
    private BigInteger numBotCh;
    private BigDecimal minBotDepth;
    private BigDecimal maxBotDepth;
    private BigDecimal upperInterpretDepth;
    private BigDecimal lowerInterpretDepth;
    private BigDecimal upperIntegratorDepth;
    private BigDecimal lowerIntegratorDepth;
    private BigInteger quality;
    private BigDecimal bubbleCorr;
    private BigInteger freq;
    private BigInteger tranceiver;

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
     * @return the threshold
     */
    public BigDecimal getThreshold() {
        return threshold;
    }

    /**
     * @param threshold the threshold to set
     */
    public void setThreshold(BigDecimal threshold) {
        this.threshold = threshold;
    }

    /**
     * @return the numPelCh
     */
    public BigInteger getNumPelCh() {
        return numPelCh;
    }

    /**
     * @param numPelCh the numPelCh to set
     */
    public void setNumPelCh(BigInteger numPelCh) {
        this.numPelCh = numPelCh;
    }

    /**
     * @return the numBotCh
     */
    public BigInteger getNumBotCh() {
        return numBotCh;
    }

    /**
     * @param numBotCh the numBotCh to set
     */
    public void setNumBotCh(BigInteger numBotCh) {
        this.numBotCh = numBotCh;
    }

    /**
     * @return the minBotDepth
     */
    public BigDecimal getMinBotDepth() {
        return minBotDepth;
    }

    /**
     * @param minBotDepth the minBotDepth to set
     */
    public void setMinBotDepth(BigDecimal minBotDepth) {
        this.minBotDepth = minBotDepth;
    }

    /**
     * @return the maxBotDepth
     */
    public BigDecimal getMaxBotDepth() {
        return maxBotDepth;
    }

    /**
     * @param maxBotDepth the maxBotDepth to set
     */
    public void setMaxBotDepth(BigDecimal maxBotDepth) {
        this.maxBotDepth = maxBotDepth;
    }

    /**
     * @return the upperInterpretDepth
     */
    public BigDecimal getUpperInterpretDepth() {
        return upperInterpretDepth;
    }

    /**
     * @param upperInterpretDepth the upperInterpretDepth to set
     */
    public void setUpperInterpretDepth(BigDecimal upperInterpretDepth) {
        this.upperInterpretDepth = upperInterpretDepth;
    }

    /**
     * @return the lowerInterpretDepth
     */
    public BigDecimal getLowerInterpretDepth() {
        return lowerInterpretDepth;
    }

    /**
     * @param lowerInterpretDepth the lowerInterpretDepth to set
     */
    public void setLowerInterpretDepth(BigDecimal lowerInterpretDepth) {
        this.lowerInterpretDepth = lowerInterpretDepth;
    }

    /**
     * @return the upperIntegratorDepth
     */
    public BigDecimal getUpperIntegratorDepth() {
        return upperIntegratorDepth;
    }

    /**
     * @param upperIntegratorDepth the upperIntegratorDepth to set
     */
    public void setUpperIntegratorDepth(BigDecimal upperIntegratorDepth) {
        this.upperIntegratorDepth = upperIntegratorDepth;
    }

    /**
     * @return the lowerIntegratorDepth
     */
    public BigDecimal getLowerIntegratorDepth() {
        return lowerIntegratorDepth;
    }

    /**
     * @param lowerIntegratorDepth the lowerIntegratorDepth to set
     */
    public void setLowerIntegratorDepth(BigDecimal lowerIntegratorDepth) {
        this.lowerIntegratorDepth = lowerIntegratorDepth;
    }

    /**
     * @return the quality
     */
    public BigInteger getQuality() {
        return quality;
    }

    /**
     * @param quality the quality to set
     */
    public void setQuality(BigInteger quality) {
        this.quality = quality;
    }

    /**
     * @return the bubbleCorr
     */
    public BigDecimal getBubbleCorr() {
        return bubbleCorr;
    }

    /**
     * @param bubbleCorr the bubbleCorr to set
     */
    public void setBubbleCorr(BigDecimal bubbleCorr) {
        this.bubbleCorr = bubbleCorr;
    }

    /**
     * @return the freq
     */
    public BigInteger getFreq() {
        return freq;
    }

    /**
     * @param freq the freq to set
     */
    public void setFreq(BigInteger freq) {
        this.freq = freq;
    }

    /**
     * @return the tranceiver
     */
    public BigInteger getTranceiver() {
        return tranceiver;
    }

    /**
     * @param tranceiver the tranceiver to set
     */
    public void setTranceiver(BigInteger tranceiver) {
        this.tranceiver = tranceiver;
    }
}
