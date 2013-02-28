/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.ssp.ssppredict;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import junit.framework.TestCase;
import org.junit.*;
import static junit.framework.Assert.assertEquals;

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

    /**
     * Test of predictionArgMax method, of class GORPredicter.
     */
    @Test
    public void testPredictionArgMax() {
        System.out.println("test the predictionArgMax");
        assertEquals(GORPredicter.predictionArgMax(0.9, 0.2, 0.5), 'C');
        assertEquals(GORPredicter.predictionArgMax(0.3, 0.7, 0.5), 'E');
        assertEquals(GORPredicter.predictionArgMax(0.2, 0.2, 0.5), 'H');
        
    }
}