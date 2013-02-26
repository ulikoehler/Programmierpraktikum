/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformaikmuenchen.pg4.common.alignment;

import junit.framework.TestCase;

/**
 *
 * @author koehleru
 */
public class SequencePairAlignmentTest extends TestCase {
    
    public SequencePairAlignmentTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of calculateMatchLine method, of class SequencePairAlignment.
     */
    public void testCalculateMatchLine() {
        SequencePairAlignment alignment = new SequencePairAlignment("ACTT-TGCA", "AATTG---A");
        alignment.calculateMatchLine();
        assertEquals("| ||    |", alignment.matchLine);
    }

}
