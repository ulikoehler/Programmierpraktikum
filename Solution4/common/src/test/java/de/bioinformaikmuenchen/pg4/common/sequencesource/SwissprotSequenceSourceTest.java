/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformaikmuenchen.pg4.common.sequencesource;

import de.bioinformatikmuenchen.pg4.common.Sequence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author koehleru
 */
public class SwissprotSequenceSourceTest {
    
    public SwissprotSequenceSourceTest() {
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
     * Test of getSequence method, of class SwissprotSequenceSource.
     */
    @Test
    public void testGetSequence() {
        SwissprotSequenceSource instance = new SwissprotSequenceSource();
        assertEquals(instance.getSequence("Q8ZHE7").getSequence(), "MSRTIFCTFLKKDAERQDFQLYPGEIGKRIYNEISKEAWSQWITKQTMLINEKKLSMMNIEDRKLLEQEMVNFLFEGQDVHIAGYTPPSK");
    }
}