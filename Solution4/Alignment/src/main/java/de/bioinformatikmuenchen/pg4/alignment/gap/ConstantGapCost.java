/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.alignment.gap;

/**
 *
 * @author koehleru
 */
public class ConstantGapCost implements IGapCost {

    private double cost;

    public ConstantGapCost(double cost) {
        this.cost = cost;
    }

    public double getGapCost(int length) {
        return cost * length;
    }

    public double getGapExtensionPenalty(int previousLength, int extendBy) {
        return cost * extendBy;
    }
}
