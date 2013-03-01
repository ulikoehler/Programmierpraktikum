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

    public static char predictionArgMax(double pC, double pE, double pH) {
        if (pC > pE) {
            if (pC > pH) {
                return 'C';
            }
            return 'H';
        } else {
            if (pH > pE) {
                return 'H';
            }
            return 'E';
        }
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
    public String preprocess(String ssSeq, String pC, String pE, String pH) {

        return ssSeq;
    }
}
