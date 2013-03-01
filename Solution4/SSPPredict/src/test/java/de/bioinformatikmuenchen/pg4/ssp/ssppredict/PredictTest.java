/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.ssp.ssppredict;

import java.io.File;
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
        Predict.main(new String[] {"--model", "/home/proj/biocluster/praktikum/bioprakt/Data/commandline/gor_examples/gor1_cb513_model.txt", "--seq", "/home/proj/biocluster/praktikum/bioprakt/Data/commandline/gor_examples/1C0T/1C0T.fasta"});
    }

    @Test
    public void testPrintUsageAndQuit() {
    }
}