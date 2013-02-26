/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.alignment.gap;

/**
 *
 * @author koehleru
 */
public class AffineGapCost implements IGapCost {

    private double gapopen;
    private double gapextend;

    public AffineGapCost(double gapopen, double gapextend) {
        this.gapopen = gapopen;
        this.gapextend = gapextend;
    }

    @Override
    public double getGapCost(int length) {
        return gapopen + (length - 1) * gapextend;
    }

    @Override
    public double getGapExtensionPenalty(int previousLength, int extendBy) {
        return extendBy * gapextend;
    }
}
