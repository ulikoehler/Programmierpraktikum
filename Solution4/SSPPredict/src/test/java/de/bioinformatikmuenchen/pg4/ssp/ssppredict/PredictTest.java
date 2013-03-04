/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.ssp.ssppredict;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author spoeri
 */
public class PredictTest {

    public PredictTest() {
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

    @Test
    public void testMain() {
        //Predict.main("--probabilities --format HTML --model src/test/resources/Ugor4CB513DSSP.db.txt --seq predict".split(" "));
        Predict.main("--probabilities --format HTML --model src/test/resources/Ugor3CB513DSSP.db.txt --maf src/test/resources/1chkb.aln".split(" "));
        //Predict.main("--probabilities --postprocessing 0.7 --format HTML --model src/test/resources/Ugor1CB513DSSP.db.txt --seq predict".split(" "));
        
        /*
        java.io.PrintStream sysOut = System.out;
        System.out.println("Testing main method ...");
        String defaultOutputfile = "src/test/resources/testOutput.txt";
        String[][] args = new String[][] {
            {"--model", "src/test/resources/Ugor1CB513DSSP.db.txt", "--seq", "src/test/resources/1C0T/1C0T.fasta"},
            {"--model", "src/test/resources/Ugor1CB513DSSP.db.txt", "--seq", "src/test/resources/1C0T/1C0T.fasta", "--format", "html"},
            {"--model", "src/test/resources/Ugor3CB513DSSP.db.txt", "--seq", "src/test/resources/1C0T/1C0T.fasta"},
            {"--model", "src/test/resources/Ugor4CB513DSSP.db.txt", "--seq", "src/test/resources/1C0T/1C0T.fasta"},
        };
        String expectedFilePath = "src/test/resources/tests";
        String expectedOutputFiles[] = new String[] {
            "TestMain1", "TestMain2", "TestMain3", "TestMain4", "TestMain5", "TestMain6"
        };
        for (int i = 0; i <args.length; i++) {
            try {
                PrintStream p = new PrintStream(new java.io.FileOutputStream(new File(defaultOutputfile)));
                System.setOut(p);
                Predict.main(args[i]);
                p.close();
            } catch (FileNotFoundException ex) {
                fail("Couldn't test");
                sysOut.append("Test failed!");
            }

            // cmp defaultOutputFile (f) to expected output
            try {

                File f = new File(defaultOutputfile);
                BufferedReader r = new BufferedReader(new FileReader(f));
                String line = null;
                while ((line = r.readLine()) != null) {
                }


            } catch (IOException ex) {
                System.out.println("---");
                fail("Unexpected Exception in Test!");
            }
        }*/
    }

    @Test
    public void testPrintUsageAndQuit() {
    }
}