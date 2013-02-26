/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.common.distance;

import de.bioinformaikmuenchen.pg4.common.distance.IDistanceMatrix;
import de.bioinformaikmuenchen.pg4.common.distance.QUASARDistanceMatrixFactory;
import java.io.InputStreamReader;
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
public class QUASARDistanceMatrixFactoryTest extends TestCase {
    
    public QUASARDistanceMatrixFactoryTest() {
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
     * Test of factorize method, of class QUASARDistanceMatrixFactory.
     */
    @Test
    public void testFactorize() throws Exception {
        IDistanceMatrix matrix = QUASARDistanceMatrixFactory.factorize(
                new InputStreamReader(
                    QUASARDistanceMatrixFactoryTest.class.getResourceAsStream("/de/bioinformatikmuenchen/pg4/common/distance/blosum80.mat")));
        assertEquals(7.0, matrix.distance('A', 'A'), 0.0000001);
        assertEquals(-3.0, matrix.distance('A', 'R'), 0.0000001);
        assertEquals(-3.0, matrix.distance('R', 'A'), 0.0000001);
        assertEquals(9, matrix.distance('R', 'R'), 0.0000001);
        assertEquals(-1.0, matrix.distance('C', 'A'), 0.0000001);
        assertEquals(-1.0, matrix.distance('A', 'C'), 0.0000001);
    }
}