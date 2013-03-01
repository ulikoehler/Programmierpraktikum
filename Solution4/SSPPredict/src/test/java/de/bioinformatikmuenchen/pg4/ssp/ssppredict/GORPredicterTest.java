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
import junit.framework.TestCase;
import org.junit.*;
import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 *
 * @author spoeri
 */
public class GORPredicterTest {
    
    public GORPredicterTest() {
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

    // Postprocessing
    String files[] = {
        "PredictGor1", "PredictGor3", "PredictGor4", "PredictGor1x", "PredictGor3x", "PredictGor4x", "PredictGor4xx",
        "Ugor1CB513DSSP.db.txt", "Ugor3CB513DSSP.db.txt", "Ugor4CB513DSSP.db.txt",
        "Ugor1test.db.txt", "Ugor3test.db.txt", "Ugor4test.db.txt"
    };
    String eGors[] = {
        "gor1", "gor3", "gor4", "gor1", "gor3", "gor4", "gor4",
        "gor1", "gor3", "gor4",
        "gor1", "gor3", "gor4"
    };
    int eWindow[] = {
        4, 5, 3, 4, 5, 3, 3,
        17, 17, 17,
        17, 17, 17
    };
    char[][] p = new char[][]{
        {'A'}, {'C'}, {'A'},
        {'A'}, {'C'}, {'A'},
        {'A'},
        {'C', 'E', 'H'}, {'C', 'E', 'H'}, {'C', 'E', 'H'},
        {'C', 'E', 'H'}, {'C', 'E', 'H'}, {'C', 'E', 'H'}
    };
    String path = "src/test/resources/";

    @Test
    public void testGetStatesFromFile() {
        System.out.println("Starting test get states from file ...");
        for (int i = 0; i < files.length; i++) {
            File f = new File(path + files[i]);
            char n[] = GORPredicter.getStatesFromFile(f, GORPredicter.getGorFromFile(f));
            if (n.length != p[i].length) {
                fail();
            }
            for (int z = 0; z < n.length; z++) {
                System.out.println("current: " + n[z] + " <-> expected: " + p[i][z] + " @file: " + files[i]);
                if (n[z] != p[i][z]) {
                    fail();
                }
            }
        }
    }
    
    @Test
    public void testGetGorFromFile() {
        System.out.println("Starting test get gor method from File ...");
        // Predict.getGorFromFile
        for (int i = 0; i < files.length; i++) {
            Predict.simpleGorMethods n = GORPredicter.getGorFromFile(new File(path + files[i]));
            assertEquals(eGors[i], n.toString());
        }
    }

    @Test
    public void testGetWindowSizeFromFile() {
        System.out.println("Starting test getWindowSize ...");
        for (int i = 0; i < files.length; i++) {
            assertEquals(eWindow[i], GORPredicter.getWindowSizeFromFile(new File(path + files[i])));
        }
    }
    
    // Prediction
    @Test
    public void testPredictionArgMax() {
        System.out.println("test the predictionArgMax");
        for(int i = 0; i < Data.secStruct.length; i++) {
            double test[] = new double[Data.secStruct.length];
            test[i] = 1;
            assertEquals(GORPredicter.predictionArgMax(test), Data.secStruct[i]);
        }
        
    }

    /**
     * Test of p2Int method, of class GORPredicter.
     */
    @Test
    public void testP2Int() {
        System.out.println("p2Int");
        assertEquals(0, GORPredicter.p2Int(0));
        assertEquals(0, GORPredicter.p2Int(0.03));
        assertEquals(1, GORPredicter.p2Int(0.05));
        assertEquals(1, GORPredicter.p2Int(0.07));
        assertEquals(1, GORPredicter.p2Int(0.1));
        assertEquals(1, GORPredicter.p2Int(0.13));
        assertEquals(2, GORPredicter.p2Int(0.17));
        assertEquals(2, GORPredicter.p2Int(0.2));
        assertEquals(3, GORPredicter.p2Int(0.31));
        assertEquals(4, GORPredicter.p2Int(0.41));
        assertEquals(5, GORPredicter.p2Int(0.54));
        assertEquals(6, GORPredicter.p2Int(0.56));
        assertEquals(6, GORPredicter.p2Int(0.61));
        assertEquals(7, GORPredicter.p2Int(0.67));
        assertEquals(8, GORPredicter.p2Int(0.75));
        assertEquals(8, GORPredicter.p2Int(0.8499999));
        assertEquals(9, GORPredicter.p2Int(0.85));
        assertEquals(9, GORPredicter.p2Int(0.86));
        assertEquals(9, GORPredicter.p2Int(0.87));
        assertEquals(9, GORPredicter.p2Int(0.88));
        assertEquals(9, GORPredicter.p2Int(0.89));
        assertEquals(9, GORPredicter.p2Int(0.90));
        assertEquals(9, GORPredicter.p2Int(0.91));
        assertEquals(9, GORPredicter.p2Int(0.92));
        assertEquals(9, GORPredicter.p2Int(0.93));
        assertEquals(9, GORPredicter.p2Int(0.94));
        assertEquals(9, GORPredicter.p2Int(0.95));
        assertEquals(9, GORPredicter.p2Int(0.96));
        assertEquals(9, GORPredicter.p2Int(0.97));
        assertEquals(9, GORPredicter.p2Int(0.98));
        assertEquals(9, GORPredicter.p2Int(0.99));
        assertEquals(9, GORPredicter.p2Int(1.0));
        assertEquals(9, GORPredicter.p2Int(1.1));
    }
/*
    // Postprocessing
    @Test
    public void testPreprocess() {
        String input[] = new String[] {
            "CCCHCCC",
            "CHHHCCC"
        };
        String[][] prob = new String[][] {  // prob for C, E, H
            { "9874789", "0003000", "1234321" },
            { "9333399", "0000000", "1113111" }
        };
        String output[] = new String[] {
            "CCCCCCC",
            "CHHHHCC"
        };
        for(int i = 0; i < input.length; i++) {
            String r = GORPredicter.preprocess(input[i], prob[i]);
            assertEquals(output[i], r);
        }
    }*/
}