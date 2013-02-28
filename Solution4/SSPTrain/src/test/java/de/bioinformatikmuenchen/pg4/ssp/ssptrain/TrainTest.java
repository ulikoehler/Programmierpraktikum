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
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import java.io.*;

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

        System.out.println(new java.io.File(".").getAbsolutePath());
        
        for (int j = 0; j < files.length; j++) {
            for (int i = 0; i < gors.length; i++) {
                System.out.println(" Testing " + gors[i] + " with input file: " + files[j]);
                String[] args = new String[]{"--method", gors[i], "--model", path + "model.txt", "--db", path + files[j]};
                Train.main(args);
                String defaultOutputfile = "U" + gors[i] + files[j] + ".txt";
                // cmp defaultOutputFile to model.txt
                try {
                    System.out.println("cmp Files!");
                    
                    int lineNr = 0;
                    BufferedReader r1 = null;
                    BufferedReader r2 = null;
                    File f1 = new File(path + defaultOutputfile);
                    File f2 = new File(args[3]);
                    
                    try {
                        r1 = new BufferedReader(new InputStreamReader(new FileInputStream(f1)));
                    } catch (Exception e) {
                        System.out.println(f1.getAbsolutePath() + " - " + e.toString());
                        fail();
                    }
                    try {
                        r2 = new BufferedReader(new InputStreamReader(new FileInputStream(f2)));
                    } catch (Exception e) {
                        System.out.println(f2.getAbsolutePath() + " - " + e.toString());
                        fail();
                    }
                    String lastLine = null;
                    String line;
                    while ((line = r1.readLine()) != null) {
                        lineNr++;
                        String line2 = r2.readLine();
                        line = line.trim();
                        line2 = line2.trim();
                        assertEquals("  Line " + lineNr + ": default: '" + line + "' <-> got: '" + line2 + "' - last line '" + lastLine + "'", line2, line);
                        lastLine = line;
                    }
                    r1.close();
                    r2.close();
                } catch (IOException ex) {
                    System.out.println("---");
                    Logger.getLogger(TrainTest.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        }
    }
}