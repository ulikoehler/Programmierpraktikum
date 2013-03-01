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
        {'A'}, {'A'}, {'A'},
        {'A'}, {'A'}, {'A'},
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
            char n[] = Predict.getStatesFromFile(f, Predict.getGorFromFile(f));
            if (n.length != p[i].length) {
                fail();
            }
            for (int z = 0; z < n.length; z++) {
                if (p[i][z] != n[z]) {
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
            Predict.simpleGorMethods n = Predict.getGorFromFile(new File(path + files[i]));
            assertEquals(eGors[i], n.toString());
        }
    }

    @Test
    public void testGetWindowSizeFromFile() {
        System.out.println("Starting test getWindowSize ...");
        for (int i = 0; i < files.length; i++) {
            assertEquals(eWindow[i], Predict.getWindowSizeFromFile(new File(path + files[i])));
        }
    }
}