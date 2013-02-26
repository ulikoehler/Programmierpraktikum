/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.alignment.pairfile;

import com.google.common.collect.Lists;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
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
public class PairfileParserTest extends TestCase {

    public PairfileParserTest() {
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
     * Test of parsePairfile method, of class PairfileParser.
     */
    @Test
    public void testParsePairfile_Reader() throws Exception {
        Reader reader = new InputStreamReader(
                PairfileParser.class.getResourceAsStream("/pairfile/test.pairs"));
        ArrayList<PairfileEntry> list = Lists.newArrayList(PairfileParser.parsePairfile(reader));
        assertEquals(new PairfileEntry("1j2xA00", "1wq2B00", "1.10.10.10 1.10.10.10 d1j2xa_ d1wq2b1 a.4.5.30 a.4.5.45"), list.get(0));
        assertEquals(new PairfileEntry("2f5dA01", "1p4xA01", "1.10.10.10 1.10.10.10 d2f5da1 d1p4xa1 a.4.5.24 a.4.5.28"), list.get(1));
        assertEquals(new PairfileEntry("1rz4A02", "1iuyA00", "1.10.10.10 1.10.10.10 d1rz4a1 d1iuya_ a.4.5.53 a.4.5.34"), list.get(2));

    }
}