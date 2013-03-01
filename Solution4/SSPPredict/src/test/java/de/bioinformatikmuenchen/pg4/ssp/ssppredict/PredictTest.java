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
    
    
    public class GORPredicterImpl extends GORPredicter {

        public void init() {
        }
    }
    
    String files[] = { "PredictGor1" , "PredictGor3", "PredictGor4", "PredictGor1x" , "PredictGor3x", "PredictGor4x", "PredictGor4xx" };
    String eGors[] = { "gor1", "gor3", "gor4", "gor1", "gor3", "gor4", "gor4" };
    int eWindow[] = { 4, 5, 3, 4, 5, 3, 3 };
    String path = "src/test/resources/";
    
    @Test
    public void testGetGorFromFile() {
        System.out.println("Starting test get gor method from File ...");
        // Predict.getGorFromFile
        for(int i = 0; i < files.length; i++) {
            Predict.simpleGorMethods n = Predict.getGorFromFile(new File(path + files[i]));
            assertEquals(eGors[i], n.toString());
        }
    }

    @Test
    public void testGetWindowSizeFromFile() {
        System.out.println("Starting test getWindowSize ...");
        for(int i = 0; i < files.length; i++) {
            assertEquals(eWindow[i], Predict.getWindowSizeFromFile(new File(path + files[i])));
        }
    }
}