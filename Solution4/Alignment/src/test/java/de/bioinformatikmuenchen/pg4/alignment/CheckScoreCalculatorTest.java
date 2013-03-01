/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.alignment;

import de.bioinformatikmuenchen.pg4.alignment.gap.AffineGapCost;
import de.bioinformatikmuenchen.pg4.alignment.gap.ConstantGapCost;
import de.bioinformatikmuenchen.pg4.alignment.gap.IGapCost;
import de.bioinformatikmuenchen.pg4.alignment.recursive.WikipediaAlignmentMatrix1;
import de.bioinformatikmuenchen.pg4.alignment.recursive.ZeroOneAlignmentMatrix;
import de.bioinformatikmuenchen.pg4.common.Sequence;
import de.bioinformatikmuenchen.pg4.common.alignment.AlignmentResult;
import de.bioinformatikmuenchen.pg4.common.alignment.SequencePairAlignment;
import de.bioinformatikmuenchen.pg4.common.distance.IDistanceMatrix;
import de.bioinformatikmuenchen.pg4.common.distance.QUASARDistanceMatrixFactory;
import java.io.IOException;
import java.io.InputStreamReader;
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
        System.out.println("testCalculateCheckScoreNonAffineGlobal");
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
    public void testGetSinglePartGapCost() {
        System.out.println("testGetSinglePartGapCost");
        IGapCost gapCost = new AffineGapCost(-3, -1); //1 Gap = -4
        assertEquals(-11, CheckScoreCalculator.getOverallGapCost("---A--", gapCost), 0.0000001);
    }

//    /**
//     * Test of calculateCheckScoreNonAffine method, of class
//     * CheckScoreCalculator. See testAvatarAlign() in NeedlemanWunsch Test
//     */
//    @Test
//    public void testCalculateCheckScoreNonAffineLocal() {
//        System.out.println("testCalculateCheckScoreNonAffineLocal");
//        Sequence seq1Obj = new Sequence("Id1","GAATT");
//        Sequence seq2Obj = new Sequence("Id2","AGATC");
//        IDistanceMatrix matrix = new WikipediaAlignmentMatrix1();
//        IGapCost gapCost = new ConstantGapCost(-5);
//        SmithWaterman instance = new SmithWaterman(AlignmentMode.LOCAL, AlignmentAlgorithm.SMITH_WATERMAN, matrix, gapCost);
//        AlignmentResult result = instance.align(seq1Obj, seq2Obj);
//        SequencePairAlignment alignment = result.getFirstAlignment();
//        System.out.println(alignment.toString());
//        assertEquals(result.getScore(), CheckScoreCalculator.calculateCheckScoreNonAffine(AlignmentMode.LOCAL, result.getFirstAlignment(), matrix, gapCost), 0.00001);
//    }
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
            String[] result = CheckScoreCalculator.stripStartAndEndGapsFreeshift(qa, ta);
            fail("No exception");
        } catch (Exception ex) {
        }
        //At the start
        qa = "---ATAGTTAT-";
        ta = "-TA---G--A-A";
        try {
            String[] result = CheckScoreCalculator.stripStartAndEndGapsFreeshift(qa, ta);
            fail("No exception");
        } catch (Exception ex) {
        }
    }

    @Test
    public void testStripNoModify() {
        System.out.println("testStripNoModify");
        //These shall not be modified
        String qa = "A--ATAGTTATA";
        String ta = "ATA---G--A-T";
        String[] expResult = new String[]{qa, ta};
        String[] result = CheckScoreCalculator.stripStartAndEndGapsFreeshift(qa, ta);
        assertArrayEquals(expResult, result);
    }

    @Test
    public void testCheckScoreAffine() {
        System.out.println("testCheckScoreAffine");
        IGapCost gapCost = new AffineGapCost(-3, -1); //1 Gap = 4
        assertEquals(-4, gapCost.getGapCost(1), 0.000001);
        String qa = "A--ATAGTTATA";
        String ta = "ATA---G--A-T";
        assertEquals(-17, CheckScoreCalculator.calculateCheckScoreAffine(AlignmentMode.GLOBAL, new SequencePairAlignment(qa, ta), new ZeroOneAlignmentMatrix(), gapCost), 0.000000001);
    }

    /**
     * Test of stripStartAndEndGaps method, of class CheckScoreCalculator.
     */
    @Test
    public void testStripStartAndEndGapsFreeshift() {
        System.out.println("stripStartAndEndGaps");
        String qa = "---ATAGTTATA";
        String ta = "ATA---G--A--";
        String[] expResult = new String[]{"ATAGTTA", "---G--A"};
        String[] result = CheckScoreCalculator.stripStartAndEndGapsFreeshift(qa, ta);
        assertArrayEquals(expResult, result);
        qa = "---ATAGTTATA";
        ta = "ATA---G--A--";
        expResult = new String[]{"ATAGTTA", "---G--A"};
        result = CheckScoreCalculator.stripStartAndEndGapsFreeshift(qa, ta);
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of stripStartAndEndGaps method, of class CheckScoreCalculator.
     */
    @Test
    public void testStripStartAndEndGapsLocal() {
        System.out.println("testStripStartAndEndGapsLocal");
        String qa = "x-xxx-x";
        String ta = "-x-x-x-";
        String[] expResult = new String[]{"x", "x"};
        String[] result = CheckScoreCalculator.stripStartAndEndGapsLocal(qa, ta);
        assertArrayEquals(expResult, result);
    }

    /**
     * Test of stripStartAndEndGaps method, of class CheckScoreCalculator.
     */
    @Test
    public void testCheckScoreRealGlobal1() throws IOException {
        IDistanceMatrix dayhoff = QUASARDistanceMatrixFactory.factorize(new InputStreamReader(CheckScoreCalculatorTest.class.getResourceAsStream("/matrices/dayhoff.mat")));
        //2czeB00 1q1gC00
        System.out.println("testCheckScoreRealGlobal1");
        String qa = "GPLDVQVTEDAVRRYLTRKPMTTKDLLKKFQTKKTGLSSEQTVNVLAQILKRLNPERKMINDKMHFSLK-";
        String ta = "----MEEAKQKVVDFLNSKSK-SKFYFNDFTDLFPDMKQREVKKILTALVNDEVLEYWSSGSTTMYGLKG";
        IGapCost gapCost = new AffineGapCost(-12, -1);
        assertEquals(4.9, CheckScoreCalculator.calculateCheckScoreAffine(AlignmentMode.GLOBAL, new SequencePairAlignment(qa, ta), dayhoff, gapCost), 0.0000001);
    }

    /**
     * Test of stripStartAndEndGaps method, of class CheckScoreCalculator.
     */
    @Test
    public void testCheckScoreRealGlobal2() throws IOException {
        IDistanceMatrix dayhoff = QUASARDistanceMatrixFactory.factorize(new InputStreamReader(CheckScoreCalculatorTest.class.getResourceAsStream("/matrices/dayhoff.mat")));
        //1oh1A00 1p9mC02 -35.100
        System.out.println("testCheckScoreRealGlobal1");
        String qa = "--GSMEQFELFSIDKFKCNSEAKYYLNIIEGEWH-PQDLNDS--PLKFILSTSDDSDYICKYINTEHKQLTLY---NKNNSSIVIEIFIPNDNKILLTIMNTEALGTSPRM-----TFIKH";
        String ta = "QPDPPANITVTAVAR-----NPRW----LSVTWQDPHSWNSSFYRLRFEL----------RYRAERSKTFTTWMVKDLQHHCVIHDAW--SGLRHVVQLRAQEEFGQGEWSEWSPEAMGTP";
        IGapCost gapCost = new AffineGapCost(-12, -1);
        assertEquals(-35.1, CheckScoreCalculator.calculateCheckScoreAffine(AlignmentMode.GLOBAL, new SequencePairAlignment(qa, ta), dayhoff, gapCost), 0.0000001);
    }

    /**
     * Test of stripStartAndEndGaps method, of class CheckScoreCalculator.
     */
    @Test
    public void testCheckScoreRealLocal1() throws IOException {
        IDistanceMatrix dayhoff = QUASARDistanceMatrixFactory.factorize(new InputStreamReader(CheckScoreCalculatorTest.class.getResourceAsStream("/matrices/dayhoff.mat")));
        //2czeB00 1q1gC00
        System.out.println("testCheckScoreRealLocal1");
        String qa = "GPLDVQVTEDAVRRYLTRKPMTTKDLLKKFQTKKTGLSSEQTVNVLAQILKRLNPERKMINDKMHFSLK-";
        String ta = "----MEEAKQKVVDFLNSKSK-SKFYFNDFTDLFPDMKQREVKKILTALVNDEVLEYWSSGSTTMYGLKG";
        IGapCost gapCost = new AffineGapCost(-12, -1);
        assertEquals(33.9, CheckScoreCalculator.calculateCheckScoreAffine(AlignmentMode.LOCAL, new SequencePairAlignment(qa, ta), dayhoff, gapCost), 0.0000001);
    }

    /**
     * Test of stripStartAndEndGaps method, of class CheckScoreCalculator.
     */
    @Test
    public void testCheckScoreRealLocal2() throws IOException {
        IDistanceMatrix dayhoff = QUASARDistanceMatrixFactory.factorize(new InputStreamReader(CheckScoreCalculatorTest.class.getResourceAsStream("/matrices/dayhoff.mat")));
        //1oh1A00 1p9mC02
        System.out.println("testCheckScoreRealLocal1");
        String qa = "GSMEQFELFSIDKFKCNSEAKYYLNIIE-----------------------------------------------------------------------------------------GEWHPQDLNDSPLKFILSTSDDSDYICKYINTEHKQLTLYNKNNSSIVIEIFIPNDNKILLTIMNTEALGTSPRMTFIKH";
        String ta = "----------------------------QPDPPANITVTAVARNPRWLSVTWQDPHSWNSSFYRLRFELRYRAERSKTFTTWMVKDLQHHCVIHDAWSGLRHVVQLRAQEEFGQGEWSEWSPEAMGTP---------------------------------------------------------------------";
        IGapCost gapCost = new AffineGapCost(-12, -1);
        assertEquals(35.1, CheckScoreCalculator.calculateCheckScoreAffine(AlignmentMode.LOCAL, new SequencePairAlignment(qa, ta), dayhoff, gapCost), 0.0000001);
    }
    /**
     * Test of calculateCheckScoreAffine method, of class CheckScoreCalculator.
     */
//    @Test
//    public void testCalculateCheckScoreAffine() {
//        System.out.println("calculateCheckScoreAffine");
//        SequencePairAlignment alignment = null;
//        IDistanceMatrix distanceMatrix = null;
//        IGapCost gapCost = null;
//        double expResult = 0.0;
//        double result = CheckScoreCalculator.calculateCheckScoreAffine(alignment, distanceMatrix, gapCost);
//        assertEquals(expResult, result, 0.0);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
}