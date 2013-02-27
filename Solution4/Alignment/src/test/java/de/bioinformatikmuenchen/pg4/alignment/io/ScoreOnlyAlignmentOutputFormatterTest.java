/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.alignment.io;

import de.bioinformaikmuenchen.pg4.common.alignment.AlignmentResult;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
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
        result.setSeq1Id("1j2xA00");
        result.setSeq2Id("1wq2B00");
        result.setScore(33.9);
        assertEquals("1j2xA00 1wq2B00 33.9000", instance.format(result));
    }
}