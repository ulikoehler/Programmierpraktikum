/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.alignment.io;

import de.bioinformatikmuenchen.pg4.common.alignment.AlignmentResult;

/**
 *
 * @author koehleru
 */
public abstract class AbstractAlignmentOutputFormatter implements IAlignmentOutputFormatter {

    public abstract String format(AlignmentResult result);

    public void formatAndPrint(AlignmentResult result) {
        System.out.println(format(result));
    }
    
}
