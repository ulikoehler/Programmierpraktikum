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

    public static char predictionArgMax(double[] pAA) {
        int maxPos = 0;
        for(int i = 1; i < pAA.length; i++) {
            if(pAA[maxPos] < pAA[i])
                maxPos = i;
        }
        return Data.secStruct[maxPos];
    }

    public static int p2Int(double p) {
        int nr = (int) Math.round(p * 10);
        return (nr > 9)?9:nr;
    }
    
    public abstract void init(int windowSize);

    /**
     *
     * @param ssSeq secondary structure to preprocess
     * @return the preprocessed secondary structure
     */
    public String preprocess(String ssSeq, double[] pAA) {

        return ssSeq;
    }
}
