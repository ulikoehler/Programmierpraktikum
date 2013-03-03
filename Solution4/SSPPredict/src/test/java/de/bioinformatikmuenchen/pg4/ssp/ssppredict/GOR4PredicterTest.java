/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.ssp.ssppredict;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author spoeri
 */
public class GOR4PredicterTest {

    public GOR4PredicterTest() {
    }

    @Test
    public void testInit() {
    }

    @Test
    public void testReadModelFile() {
    }

    @Test
    public void testInitPrediction() {
    }

    @Test
    public void testPredict1Example() {
    }

    @Test
    public void testGetMatrixRepresentation() {
        
        try {
            GOR4Predicter p = new GOR4Predicter();
            p.init();
            p.readModelFile(new java.io.File("src/test/resources/Ugor4CB513DSSP.db.txt"));
            String k = p.getMatrixRepresentation();
            java.io.FileWriter f = new java.io.FileWriter(new java.io.File("out"));
            f.append(k);
            f.flush();
            f.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            fail();
        }
    }
}