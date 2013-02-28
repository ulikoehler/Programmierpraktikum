/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.alignment;

import de.bioinformatikmuenchen.pg4.alignment.gap.ConstantGapCost;
import de.bioinformatikmuenchen.pg4.alignment.gap.IGapCost;
import de.bioinformatikmuenchen.pg4.alignment.recursive.WikipediaAlignmentMatrix1;
import de.bioinformatikmuenchen.pg4.alignment.recursive.ZeroOneAlignmentMatrix;
import de.bioinformatikmuenchen.pg4.common.Sequence;
import de.bioinformatikmuenchen.pg4.common.alignment.AlignmentResult;
import de.bioinformatikmuenchen.pg4.common.alignment.SequencePairAlignment;
import de.bioinformatikmuenchen.pg4.common.distance.IDistanceMatrix;
import static junit.framework.Assert.assertEquals;
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
public class CheckScoreCalculatorTest {

    public CheckScoreCalculatorTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of calculateCheckScoreNonAffine method, of class
     * CheckScoreCalculator. See testAvatarAlign() in NeedlemanWunsch Test
     */
    @Test
    public void testCalculateCheckScoreNonAffineGlobal() {
        Sequence seq1Obj = new Sequence("GAATT");
        Sequence seq2Obj = new Sequence("GGATC");
        IDistanceMatrix matrix = new WikipediaAlignmentMatrix1();
        IGapCost gapCost = new ConstantGapCost(-5);
        NeedlemanWunsch instance = new NeedlemanWunsch(AlignmentMode.GLOBAL, AlignmentAlgorithm.NEEDLEMAN_WUNSCH, matrix, gapCost);
        AlignmentResult result = instance.align(seq1Obj, seq2Obj);
        assertEquals(24.0, result.getScore(), 0.0000000001);
        SequencePairAlignment spa = result.getFirstAlignment();
        assertEquals(24.0, CheckScoreCalculator.calculateCheckScoreNonAffine(AlignmentMode.GLOBAL, result.getFirstAlignment(), matrix, gapCost), 0.00001);
    }

    /**
     * Test of calculateCheckScoreNonAffine method, of class
     * CheckScoreCalculator. See testAvatarAlign() in NeedlemanWunsch Test
     */
    @Test
    public void testCalculateCheckScoreNonAffineLocal() {
        Sequence seq1Obj = new Sequence("GAATT");
        Sequence seq2Obj = new Sequence("AGATC");
        IDistanceMatrix matrix = new WikipediaAlignmentMatrix1();
        IGapCost gapCost = new ConstantGapCost(-5);
        SmithWaterman instance = new SmithWaterman(AlignmentMode.LOCAL, AlignmentAlgorithm.SMITH_WATERMAN, matrix, gapCost);
        AlignmentResult result = instance.align(seq1Obj, seq2Obj);
        assertEquals(result.getScore(), CheckScoreCalculator.calculateCheckScoreNonAffine(AlignmentMode.LOCAL, result.getFirstAlignment(), matrix, gapCost), 0.00001);
    }

    /**
     * Test of stripStartAndEndGaps method, of class CheckScoreCalculator.
     */
    @Test
    public void testGapOnBothParts() {
        System.out.println("testGapOnBothParts");
        //AT the end
        String qa = "---ATAGTTAT-";
        String ta = "ATA---G--A--";
        try {
            String[] result = CheckScoreCalculator.stripStartAndEndGaps(qa, ta);
            fail("No exception");
        } catch (Exception ex) {
        }
        //At the start
        qa = "---ATAGTTAT-";
        ta = "-TA---G--A-A";
        try {
            String[] result = CheckScoreCalculator.stripStartAndEndGaps(qa, ta);
            fail("No exception");
        } catch (Exception ex) {
        }
    }

    @Test
    public void testNoModify() {
        System.out.println("stripStartAndEndGaps");
        //These shall not be modified
        String qa = "A--ATAGTTATA";
        String ta = "ATA---G--A-T";
        String[] expResult = new String[]{qa, ta};
        String[] result = CheckScoreCalculator.stripStartAndEndGaps(qa, ta);
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of stripStartAndEndGaps method, of class CheckScoreCalculator.
     */
    @Test
    public void testStripStartAndEndGaps() {
        System.out.println("stripStartAndEndGaps");
        String qa = "---ATAGTTATA";
        String ta = "ATA---G--A--";
        String[] expResult = new String[]{"ATAGTTA", "---G--A"};
        String[] result = CheckScoreCalculator.stripStartAndEndGaps(qa, ta);
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of calculateCheckScoreAffine method, of class CheckScoreCalculator.
     */
    @Test
    public void testCalculateCheckScoreAffine() {
        System.out.println("calculateCheckScoreAffine");
        SequencePairAlignment alignment = null;
        IDistanceMatrix distanceMatrix = null;
        IGapCost gapCost = null;
        double expResult = 0.0;
        double result = CheckScoreCalculator.calculateCheckScoreAffine(alignment, distanceMatrix, gapCost);
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}