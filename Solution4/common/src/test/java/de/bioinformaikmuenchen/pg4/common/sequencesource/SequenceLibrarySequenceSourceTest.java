/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformaikmuenchen.pg4.common.sequencesource;

import de.bioinformatikmuenchen.pg4.common.sequencesource.SequenceLibrarySequenceSource;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.zip.GZIPInputStream;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author koehleru
 */
public class SequenceLibrarySequenceSourceTest extends TestCase {

    public SequenceLibrarySequenceSourceTest() {
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
     * Test of getSequence method, of class SequenceLibrarySequenceSource.
     */
    @Test
    public void testGetSequence() throws IOException {
        Reader reader = new InputStreamReader(new GZIPInputStream(
                SequenceLibrarySequenceSource.class.getResourceAsStream("/de/bioinformatikmuenchen/pg4/common/sequencesource/domains.seqlib.gz")));
        SequenceLibrarySequenceSource src = new SequenceLibrarySequenceSource(reader);
        //Seq from the beginning
        assertEquals(src.getSequence("12gsB02").getSequence(), "YGKDQQEAALVDMVNDGVEDLRCKYISLIYTNYEAGKDDYVKALPGQLKPFETLLSQNQGGKTFIVGDQISFADYNLLDLLLIHEVLAPGCLDAFPLLSAYVGRLSA");
        assertEquals(src.getSequence("1a0aB00").getSequence(), "MKRESHKHAEQARRNRLAVALHELASLIPAEWKQQNVSAAPSKATTVEAACRYIRHLQQNGS");
        //Somewhere in the middle
        assertEquals(src.getSequence("1nltA02").getSequence(), "CKECEGRGGKKGAVKKCTSCNGQGIKFVTRQMGPMIQRFQTECDVCHGTGDIIDPKDRCKSCNGK");
        assertEquals(src.getSequence("1njiC02").getSequence(), "GRRIQGQRRGRGTSTFRAPSHRYKADLEHRKVEDGDVIAGTVVDIEHDPARSAPVAAVEFEDGDRRLILAPEGVGVG");
        //at the end
        assertEquals(src.getSequence("7gatA00").getSequence(), "MKNGEQNGPTTCTNCFTQTTPVWRRNPEGQPLCNACGLFLKLHGVVRPLSLKTDVIKKRNRNSAN");
        assertEquals(src.getSequence("8ickA01").getSequence(), "ETLNGGITDMLTELANFEKNVSQAIHKYNAYRKAASVIAKYPHKIKSGAEAKKLPGVGTKIAEKIDEFLATGKLRKLEKIRQ");
        //Other / misc
        assertEquals(src.getSequence("1j2xA00").getSequence(), "GPLDVQVTEDAVRRYLTRKPMTTKDLLKKFQTKKTGLSSEQTVNVLAQILKRLNPERKMINDKMHFSLK");
        assertEquals(src.getSequence("1wq2B00").getSequence(), "MEEAKQKVVDFLNSKSKSKFYFNDFTDLFPDMKQREVKKILTALVNDEVLEYWSSGSTTMYGLKG");
    }
}