/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.alignment.io;

import com.google.common.collect.Lists;
import de.bioinformatikmuenchen.pg4.common.alignment.AlignmentResult;
import de.bioinformatikmuenchen.pg4.common.alignment.SequencePairAlignment;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author koehleru
 */
public class ScoreOnlyAlignmentOutputFormatterTest {

    /**
     * Test of format method, of class ScoreOnlyAlignmentOutputFormatter.
     */
    @Test
    public void testFormat() {
        ScoreOnlyAlignmentOutputFormatter instance = new ScoreOnlyAlignmentOutputFormatter();
        //1j2xA00 1wq2B00 33.9000
        AlignmentResult result = new AlignmentResult();
        result.setQuerySequenceId("1j2xA00");
        result.setTargetSequenceId("1wq2B00");
        result.setAlignments(Lists.newArrayList((SequencePairAlignment) null)); //if this is not done, an assertion fails...
        result.setScore(33.9);
        assertEquals("1j2xA00 1wq2B00 33.9000", instance.format(result));
    }
}