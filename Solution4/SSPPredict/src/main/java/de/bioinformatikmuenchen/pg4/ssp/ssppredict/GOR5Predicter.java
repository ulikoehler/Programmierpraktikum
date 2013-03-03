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
    GORPredicter gor = null;
    private String masterSeqAA = null;
    private String masterSeqId = null;

    public GOR5Predicter(Predict.simpleGorMethods gorMethod) {
        if (gorMethod.name().equals(Predict.simpleGorMethods.gor1.name())) {
            gor = new GOR1Predicter();
        } else if (gorMethod.name().equals(Predict.simpleGorMethods.gor3.name())) {
            gor = new GOR3Predicter();
        } else if (gorMethod.name().equals(Predict.simpleGorMethods.gor4.name())) {
            gor = new GOR4Predicter();
        } else {
            throw new RuntimeException("No valid GOR (I,III or IV) @ GOR V!");
        }
    }

    public void readModelFile(File f) {
        if (Predict.debug) {
            System.out.println("read model file: " + f.getAbsolutePath());
        }
        gor.init();
        gor.readModelFile(f);
        gor.initPrediction();
    }

    public PredictionResult predict(File aln) {
        getAlign(aln);
        PredictionResult result = new PredictionResult();
        // 
        
        
        return result;
    }

    private void getAlign(File aln) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(aln));
            String line = null;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("//") || line.isEmpty()) {
                    continue;
                }
                // id
                if (line.startsWith(">")) {
                    this.masterSeqId = line.substring(1);
                } // sequence to predict
                else if (line.startsWith("AS")) {
                    this.masterSeqAA = line.substring(3).trim();
                } // secstruct
                else if (line.startsWith("SS")) {
                    // don't cheat skip this line
                } // alignseq
                else if (isNumeric(line.charAt(0))) {
                    int pos = 0;
                    while (isNumeric(line.charAt(pos))) {
                        pos++;
                    }
                    String seq = line.substring(pos).trim();
                    this.align.add(seq);
                } // unknown file ?
                else {
                    throw new RuntimeException("ERROR in ali file: " + line);
                }
            }
            br.close();
        } catch (Exception e) {
            throw new RuntimeException("Error reading aln file: " + e.getLocalizedMessage());
        }
    }

    private boolean isNumeric(char x) {
        int asciiValue = (int) x;
        if (asciiValue >= 48 && asciiValue <= 57) {
            return true;
        }
        return false;
    }
}
