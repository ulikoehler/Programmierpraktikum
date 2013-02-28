/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.alignment.recursive;

import de.bioinformatikmuenchen.pg4.common.distance.IDistanceMatrix;

/**
 * Matrix described
 * http://en.wikipedia.org/wiki/Smith%E2%80%93Waterman_algorithm#Example
 *
 * @author koehleru
 */
public class MinusOneTwoAlignmentMatrix implements IDistanceMatrix {

    public MinusOneTwoAlignmentMatrix() {
    }

    @Override
    public double distance(char A, char B) {
        return (A == B ? 2 : -1);
    }

    @Override
    public boolean isAminoAcidMatrix() {
        return true;
    }

    @Override
    public String getName() {
        return MinusOneTwoAlignmentMatrix.class.getCanonicalName();
    }
}
