/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.alignment;

import de.bioinformatikmuenchen.pg4.alignment.gap.AffineGapCost;
import de.bioinformatikmuenchen.pg4.alignment.gap.ConstantGapCost;
import de.bioinformatikmuenchen.pg4.alignment.gap.IGapCost;
import de.bioinformatikmuenchen.pg4.common.Sequence;
import de.bioinformatikmuenchen.pg4.common.alignment.AlignmentResult;
import de.bioinformatikmuenchen.pg4.common.distance.IDistanceMatrix;
import de.bioinformatikmuenchen.pg4.common.distance.QUASARDistanceMatrixFactory;
import java.io.IOException;
import java.io.InputStreamReader;
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
public class GotohTest {

    public GotohTest() {
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
     * Real world example from sanity.pairs that hasn't worked somewhen -- Not
     * sure if this is for Goto, so doublechekc
     */
    @Test
    public void testAlignReal1() throws IOException {
        IDistanceMatrix matrix = QUASARDistanceMatrixFactory.factorize(new InputStreamReader(NeedlemanWunschTest.class.getResourceAsStream("/matrices/dayhoff.mat")));
        Sequence seq1Obj = new Sequence("GPLDVQVTEDAVRRYLTRKPMTTKDLLKKFQTKKTGLSSEQTVNVLAQILKRLNPERKMINDKMHFSLK");
        Sequence seq2Obj = new Sequence("MEEAKQKVVDFLNSKSKSKFYFNDFTDLFPDMKQREVKKILTALVNDEVLEYWSSGSTTMYGLKG");
        IGapCost gapCost = new AffineGapCost(-12, -1);
        Gotoh instance = new Gotoh(AlignmentMode.GLOBAL, AlignmentAlgorithm.GOTOH, matrix, gapCost);
        AlignmentResult result = instance.align(seq1Obj, seq2Obj);
        assertEquals(4.90, result.getScore(), 0.00000001);
    }

    /**
     * Real world example from sanity.pairs that hasn't worked somewhen -- Not
     * sure if this is for Goto, so doublechekc
     */
    @Test
    public void testAlignReal2() throws IOException {
        //1xauA00  1fltX00
        IDistanceMatrix matrix = QUASARDistanceMatrixFactory.factorize(new InputStreamReader(NeedlemanWunschTest.class.getResourceAsStream("/matrices/dayhoff.mat")));
        Sequence seq1Obj = new Sequence("CEVQLNIKRNSKHSAWTGELFKIECPVKYCVHRPNVTWCKHNGTIWVPLEVGPQLYTSWEENRSVPVFVLHFKPIHLSDNGSYSCSTNFNSQVINSHSVTIHV");
        Sequence seq2Obj = new Sequence("GRPFVEMYSEIPEIIHMTEGRELVIPCRVTSPNITVTLKKFPLDTLIPDGKRIIWDSRKGFIISNATYKEIGLLTCEATVNGHLYKTNYLTHRQ");
        IGapCost gapCost = new AffineGapCost(-12, -1);
        Gotoh instance = new Gotoh(AlignmentMode.GLOBAL, AlignmentAlgorithm.GOTOH, matrix, gapCost);
        AlignmentResult result = instance.align(seq1Obj, seq2Obj);
        assertEquals(4.70, result.getScore(), 0.00000001);
    }

//    @Test
    public void testAlignHay() throws IOException {
        IDistanceMatrix matrix = QUASARDistanceMatrixFactory.factorize(new InputStreamReader(NeedlemanWunschTest.class.getResourceAsStream("/matrices/dayhoff.mat")));
        Sequence seq1Obj = new Sequence("SIE");
        Sequence seq2Obj = new Sequence("SAHNE");
        IGapCost gapCost = new AffineGapCost(-21, -1);
        NeedlemanWunsch instance = new NeedlemanWunsch(AlignmentMode.GLOBAL, AlignmentAlgorithm.NEEDLEMAN_WUNSCH, matrix, gapCost);
        AlignmentResult result = instance.align(seq1Obj, seq2Obj);
        assertEquals(4.90, result.getScore(), 0.00000001);
    }
}