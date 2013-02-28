/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.alignment;

import de.bioinformatikmuenchen.pg4.common.distance.IDistanceMatrix;
import de.bioinformatikmuenchen.pg4.alignment.gap.IGapCost;

/**
 *
 * @author koehleru
 */
public class AlignmentProcessorFactory {

    public static AlignmentProcessor factorize(AlignmentMode alignmentMode, AlignmentAlgorithm algorithm, IDistanceMatrix distanceMatrix, IGapCost gapCost) {
        if (algorithm == AlignmentAlgorithm.NEEDLEMAN_WUNSCH) {
            return new NeedlemanWunsch(alignmentMode, algorithm, distanceMatrix, gapCost);
        } else if (algorithm == AlignmentAlgorithm.SMITH_WATERMAN) {
            return new SmithWaterman(alignmentMode, algorithm, distanceMatrix, gapCost);
        } else if (algorithm == AlignmentAlgorithm.GOTOH) {
            return new GotohGlobal(alignmentMode, algorithm, distanceMatrix, gapCost);
        } else {
            throw new IllegalArgumentException("Algorithm " + algorithm.toString() + " unknown / not supported!");
        }
    }
}
