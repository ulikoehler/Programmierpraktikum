/*
 * GOR5 Prediction
 */
package de.bioinformatikmuenchen.pg4.ssp.ssppredict;

import java.io.*;
import java.util.LinkedList;

/**
 *
 * @author spoeri
 */
public class GOR5Predicter {

    LinkedList<String> align = new LinkedList<String>();
    GORPredicter gor;
    
    
    public GOR5Predicter() {
    }

    public void readModelFile(File f) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line = "";
            while((line = br.readLine())!= null) {
                line = line.trim();         // no spaces at the beginning etc.
                
                
                
                
            }
        } catch(Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Exception while reading ali file! " + e.getLocalizedMessage() + " Stacktrace see above!");
        }
    }

    public PredictionResult predict() {
        return null;
    }
}
