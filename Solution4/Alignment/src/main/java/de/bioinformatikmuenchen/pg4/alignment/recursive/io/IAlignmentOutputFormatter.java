/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.alignment.recursive.io;

import de.bioinformaikmuenchen.pg4.common.alignment.AlignmentResult;

/**
 *
 * @author koehleru
 */
public interface IAlignmentOutputFormatter {
    String format(AlignmentResult result);
    void formatAndPrint(AlignmentResult result);
}
