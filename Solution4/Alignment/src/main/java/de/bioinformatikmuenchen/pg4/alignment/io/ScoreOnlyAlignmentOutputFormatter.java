package de.bioinformatikmuenchen.pg4.alignment.io;

import de.bioinformaikmuenchen.pg4.common.alignment.AlignmentResult;

/**
 *
 * @author koehleru
 */
public class ScoreOnlyAlignmentOutputFormatter extends AbstractAlignmentOutputFormatter {

    public String format(AlignmentResult result) {
        return "Alignment score: " + Double.toString(result.getScore());
    }
}