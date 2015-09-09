package no.imr.nmdapi.client.loader.pojo;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 *
 * @author sjurl
 */
public class Sa {

    private BigDecimal sa;
    private String chType;
    private BigInteger ch;
    private BigInteger acousticcategory;

    /**
     * @return the sa
     */
    public BigDecimal getSa() {
        return sa;
    }

    /**
     * @param sa the sa to set
     */
    public void setSa(BigDecimal sa) {
        this.sa = sa;
    }

    /**
     * @return the chType
     */
    public String getChType() {
        return chType;
    }

    /**
     * @param chType the chType to set
     */
    public void setChType(String chType) {
        this.chType = chType;
    }

    /**
     * @return the ch
     */
    public BigInteger getCh() {
        return ch;
    }

    /**
     * @param ch the ch to set
     */
    public void setCh(BigInteger ch) {
        this.ch = ch;
    }

    /**
     * @return the acousticcategory
     */
    public BigInteger getAcousticcategory() {
        return acousticcategory;
    }

    /**
     * @param acousticcategory the acousticcategory to set
     */
    public void setAcousticcategory(BigInteger acousticcategory) {
        this.acousticcategory = acousticcategory;
    }
}
