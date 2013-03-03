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

    LinkedList<LinkedList<String>> align = new LinkedList<LinkedList<String>>();
    GORPredicter gor = null;
    private LinkedList<String> masterSeqAA = new LinkedList<String>();
    private LinkedList<String> masterSeqId = new LinkedList<String>();

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
        // predict
        for (int i = 0; i < masterSeqAA.size(); i++) {                               // for each sequence
            // information of the current seq
            String currMasterSeqAA = masterSeqAA.get(i);
            String currMasterSeqId = masterSeqId.get(i);

            // p for this seq
            double[][] currP = new double[currMasterSeqAA.length() - Data.trainingWindowSize + 1][];

            // foreach position in the sequence
            for (int pos = 0; pos < currP.length; pos++) {
                // calc the states probability for this position
                double[] posResult = new double[Data.secStruct.length];
                int cAlignSeq = align.get(i).size();

                // get all alignment sequences
                for (int j = 0; j < cAlignSeq; j++) {
                    // calc alignment with gor for each probability (ignore gaps)
                    String subSeq = align.get(i).get(j).substring(pos, pos + Data.trainingWindowSize);
                    double predictionForSeqState[] = gor.predict1Example(subSeq);
                    
                    // add state count to posResults
                    for(int z = 0; z < posResult.length; z++) {
                        posResult[z] += predictionForSeqState[z];
                    }
                }

                // correct to probability
                for (int j = 0; j < Data.secStruct.length; j++) {
                    posResult[j] = posResult[j] / cAlignSeq;
                }

                // save result for position
                currP[pos] = posResult;
            }

            // save result
            result.add(currMasterSeqAA, currMasterSeqId, currP);
        }
        // return all
        return result;
    }

    private void getAlign(File aln) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(aln));
            String line = null;
            int i = -1;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("//") || line.isEmpty()) {
                    continue;
                }
                // id
                if (line.startsWith(">")) {
                    this.masterSeqId.add(line.substring(1));
                    this.align.add(new LinkedList<String>());
                    i++;
                } // sequence to predict
                else if (line.startsWith("AS")) {
                    this.masterSeqAA.add(line.substring(3).trim());
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
                    this.align.get(i).add(seq);
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
