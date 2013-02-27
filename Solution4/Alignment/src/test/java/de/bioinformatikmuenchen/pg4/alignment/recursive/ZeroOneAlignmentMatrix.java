/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.alignment.recursive;

import de.bioinformatikmuenchen.pg4.common.distance.IDistanceMatrix;

/**
 *
 * @author koehleru
 */
public class ZeroOneAlignmentMatrix implements IDistanceMatrix {

    public double distance(char A, char B) {
        return (A == B) ? 1 : 0;
    }

    public boolean isAminoAcidMatrix() {
        return true;
    }

    public String getName() {
        return ZeroOneAlignmentMatrix.class.getName();
    }
}
