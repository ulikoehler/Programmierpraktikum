/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.alignment.io;

import java.io.IOException;
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
public class SecStructDBParserTest extends TestCase {

    public SecStructDBParserTest() {
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
     * Test of getAS method, of class SecStructDBParser.
     */
    @Test
    public void testDBReading() throws IOException {

        SecStructDBParser instance = new SecStructDBParser(SecStructDBParserTest.class.getResourceAsStream("/ssp/test.db"));
        assertEquals(instance.getAS("11asB00"), "AYIAKQRQISFVKSHFSRQLEERLGLIEVQAPILSRVGDGTQDNLSGAEKAVQVKVKALPDAQFEVVHSLAKWKRQTLGQHDFSAGEGLYTHMKALRPDEDRLSPLHSVYVDQWDWERVMGDGERQFSTLKSTVEAIWAGIKATEAAVSEEFGLAPFLPDQIHFVHSQELLSRYPDLDAKGRERAIAKDLGAVFLVGIGGKLSDGHRHDVRAPDYDDWSTPSELGHAGLNGDILVWNPVLEDAFELSSMGIRVDADTLKHQLALTGDEDRLELEWHQALLRGEMPQTIGGGIGQSRLTMLLLQLPHIGQVQAGVWPAAVRESVPSLL");
        assertEquals(instance.getSS("11asB00"), "CHHHHHHHHHHHHHHHHHHHHHCCCEEECCCCCEEECCCCCCCCCCCCCCCCCCCCCCCCCCCEEECCCCCCHHHHHHHHCCCCCCCEEEEEEEEECCCCCCCCCCCCCEEEEEEEEEECCCCCCCHHHHHHHHHHHHHHHHHHHHHHHHHHCCCCCCCCCCEEEEHHHHHHHCCCCCHHHHHHHHHHHHCEEEEECCCCCCCCCCCCCCCCCCCECCCCECCCCCECCEEEEEEEECCCCEEEECEEEEEECCHHHHHHHHHHHCCHHHHHCHHHHHHHCCCCCCEEEEEEEHHHHHHHHCCCCCHHHCCCCCCCHHHHCCCCCCC");
        assertEquals(instance.getAS("1a22B01"), "PKFTKCRSPERETFSCHWTLGPIQLFYTRRNTQEWTQEWKECPDYVSAGENSCYFNSSFTSIWIPYCIKLTSNGGTVDEKCFSVDEIVQP");
        assertEquals(instance.getSS("1a22B01"), "CCEEEEEECCCCCEEEEECCCCCEEEEEECCCCCCCCCCEECCCCCCCCCCEEEECCCCCCCCCEEEEEEEECCEEEEEEEEEHHHCECC");

    }
}