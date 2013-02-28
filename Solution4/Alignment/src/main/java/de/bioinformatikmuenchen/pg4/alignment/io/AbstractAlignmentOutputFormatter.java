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

    /**
     * Check if the given AlignmentResult object contains valid data Throws an
     * exception or fails an assertion if invalid.
     */
    protected void check(AlignmentResult result) {
        assert result != null;
        assert result.getAlignments().size() >= 1;
    }

    public void formatAndPrint(AlignmentResult result) {
        System.out.println(format(result));
    }
}
