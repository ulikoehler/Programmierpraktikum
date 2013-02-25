/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.alignment.recursive.io;

import de.bioinformatikmuenchen.pg4.alignment.AlignmentResult;

/**
 *
 * @author koehleru
 */
public class ScoreOnlyAlignmentOutputFormatter extends AbstractAlignmentOutputFormatter {

    public String format(AlignmentResult result) {
        return Double.toString(result.getScore());
    }
}