package de.bioinformatikmuenchen.pg4.alignment.recursive.io;

import de.bioinformaikmuenchen.pg4.common.alignment.AlignmentResult;

/**
 *
 * @author koehleru
 */
public class HTMLAlignmentOutputFormatter extends AbstractAlignmentOutputFormatter {

    public String format(AlignmentResult result) {
        StringBuilder builder = new StringBuilder();
        builder.append("<div>");
        return "Alignment score: " + Double.toString(result.getScore());
    }
}