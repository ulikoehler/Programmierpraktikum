/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.ssp.ssppredict;

/**
 *
 * @author spoeri
 */
public abstract class GORPredicter {

    

    public abstract void init();

    /**
     *
     * @param ssSeq secondary structure to preprocess
     * @return the preprocessed secondary structure
     */
    public String preprocess(String ssSeq, String pH, String pC, String pE) {

        return ssSeq;
    }
}
