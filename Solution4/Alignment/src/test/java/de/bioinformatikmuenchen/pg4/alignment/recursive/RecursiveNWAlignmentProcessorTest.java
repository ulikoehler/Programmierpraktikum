/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.alignment.recursive;

import de.bioinformaikmuenchen.pg4.common.Sequence;
import de.bioinformaikmuenchen.pg4.common.distance.IDistanceMatrix;
import de.bioinformatikmuenchen.pg4.alignment.AlignmentAlgorithm;
import de.bioinformatikmuenchen.pg4.alignment.AlignmentMode;
import junit.framework.TestCase;

/**
 *
 * @author koehleru
 */
public class RecursiveNWAlignmentProcessorTest extends TestCase {
    
    public RecursiveNWAlignmentProcessorTest(String testName) {
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
     * Test of align method, of class RecursiveNWAlignmentProcessor.
     */
    public void testAlign() {
        System.out.println("align");
        Sequence seq1Obj = new Sequence("AGACTAGTTAC");
        Sequence seq2Obj = new Sequence("CGAGACGT");
        IDistanceMatrix matrix = new WikipediaAlignmentMatrix1();
        RecursiveNWAlignmentProcessor instance = new RecursiveNWAlignmentProcessor(AlignmentMode.GLOBAL, AlignmentAlgorithm.NEEDLEMAN_WUNSCH, matrix, null);
        
    }
}
