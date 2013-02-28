/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.ssp.ssptrain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author spoeri
 */
public class TrainTest extends TestCase {

    public TrainTest() {
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
     * Test of main method, of class Train.
     */
    String gors[] = new String[]{"gor1", "gor3", "gor4"};
    String files[] = new String[]{"test.db", "CB513DSSP.db"};
    String path = "src/test/resources/";

    @Test
    public void testMain() {
        System.out.println("testing the main class of Train.java");

        for (int j = 0; j < files.length; j++) {
            for (int i = 0; i < gors.length; i++) {
                String[] args = new String[]{"--method", gors[i], "--model", "model.txt", "--db", files[i]};
                Train.main(args);
                String defaultOutputfile = gors[i] + files[i] + ".txt";
                // cmp defaultOutputFile to model.txt
                try {
                    BufferedReader r1 = new BufferedReader(new InputStreamReader(TrainTest.class.getResourceAsStream("/" + defaultOutputfile)));
                    BufferedReader r2 = new BufferedReader(new InputStreamReader(TrainTest.class.getResourceAsStream("/" + args[3])));
                    String line;
                    while ((line = r1.readLine()) != null) {
                        String line2 = r2.readLine();
                        assertEquals(line2, line);
                    }
                    r1.close();
                    r2.close();
                } catch (IOException ex) {
                    Logger.getLogger(TrainTest.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }
    }
}