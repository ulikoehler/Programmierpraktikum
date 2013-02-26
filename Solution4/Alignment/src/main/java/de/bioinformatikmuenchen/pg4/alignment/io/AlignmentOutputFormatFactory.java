/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.alignment.io;

import de.bioinformatikmuenchen.pg4.alignment.AlignmentOutputFormat;

/**
 *
 * @author koehleru
 */
public class AlignmentOutputFormatFactory {

    public static IAlignmentOutputFormatter factorize(AlignmentOutputFormat format) {
        if (format == AlignmentOutputFormat.ALI) {
            return new ALIAlignmentOutputFormatter();
        } else if (format == AlignmentOutputFormat.HTML) {
            return new HTMLAlignmentOutputFormatter();
        } else if (format == AlignmentOutputFormat.SCORES) {
            return new ScoreOnlyAlignmentOutputFormatter();
        } else {
            throw new IllegalArgumentException("Output format " + format.toString() + " not supported!");
        }
    }
}
