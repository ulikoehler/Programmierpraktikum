/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.alignment;

import de.bioinformatikmuenchen.pg4.alignment.gap.IGapCost;
import de.bioinformatikmuenchen.pg4.alignment.io.ALIAlignmentOutputFormatter;
import de.bioinformatikmuenchen.pg4.alignment.io.HTMLAlignmentOutputFormatter;
import de.bioinformatikmuenchen.pg4.alignment.io.IAlignmentOutputFormatter;
import de.bioinformatikmuenchen.pg4.alignment.io.ScoreOnlyAlignmentOutputFormatter;

/**
 *
 * @author koehleru
 */
public class AlignmentProcessorFactory {

    public static AlignmentProcessor factorize(AlignmentMode alignmentMode, AlignmentAlgorithm algorithm, IDistanceMatrix distanceMatrix, IGapCost gapCost) {
        if (algorithm == AlignmentAlgorithm.NEEDLEMAN_WUNSCH) {
            return new NeedlemanWunsch(mode, algorithm, distanceMatrix, gapCost);
        } else if (algorithm == AlignmentAlgorithm.SMITH_WATERMAN) {
            throw new UnsupportedOperationException("SW is not impl yet");
        } else if (algorithm == AlignmentAlgorithm.GOTOH) {
            throw new UnsupportedOperationException("Gotoh is not impl yet");
        } else {
            throw new IllegalArgumentException("Algorithm " + algorithm.toString() + " unknown / not supported!");
        }
    }
}
