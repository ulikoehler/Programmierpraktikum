/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.ssp.ssppredict;

import static de.bioinformatikmuenchen.pg4.ssp.ssppredict.GORPredicter.convertStructureCharToMatrixId;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author spoeri
 */
public class GOR4Predicter extends GORPredicter {

    long[][] fS;
    long[][] fNS;
    private long[][][][][][] cMatrix;
    double[][][][] iMatrix;

    @Override
    public void init() {
        cMatrix = new long[Data.aaTable.length][Data.trainingWindowSize][Data.aaTable.length][Data.aaTable.length][Data.trainingWindowSize][Data.secStruct.length];
        iMatrix = new double[Data.aaTable.length][Data.trainingWindowSize][Data.aaTable.length][Data.aaTable.length][Data.trainingWindowSize][Data.secStruct.length];
        fS = new long[Data.aaTable.length][Data.secStruct.length];
        fNS = new long[Data.aaTable.length][Data.secStruct.length];
    }

    @Override
    public void readModelFile(File f) {

        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line = null;
            char currentState = ' ';
            char currentAA = ' ';
            int currentAminoAcidNr = -1;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (Predict.debug) {
                    System.out.println("Process line: " + line);
                }
                if (line.startsWith("//") || line.isEmpty()) {
                    if (Predict.debug) {
                        System.out.println(" Skip line!");
                    }
                    continue;
                }
                if (line.startsWith("=")) {
                    // getCurrentState
                    String[] r = line.substring(1, line.length() - 1).split(",");
                    currentAA = r[0].trim().charAt(0);
                    currentState = r[1].trim().charAt(0);
                    if (Predict.debug) {
                        System.out.println(" state line! (now in state: " + currentState + ")");
                    }
                } else {
                    if (Predict.debug) {
                        System.out.println(" data line!");
                    }
                    // go through line
                    String value = "";
                    int currentWindowPosition = -1;
                    line += " ";        // that's no line to remove; think why!
                    currentAminoAcidNr = GORPredicter.convertASCharToMatrixId(line.charAt(0));      // aa first
                    for (int i = 1; i < line.length(); i++) {
                        char currChar = line.charAt(i);
                        boolean currWhite = (currChar == ' ' || currChar == '\t') ? true : false;
                        if (value.isEmpty() && currWhite) {
                            continue;
                        }
                        if (!value.isEmpty() && currWhite) {
                            if (Predict.debug) {
                                System.out.println("  input in matrix: state: " + currentState + " currentWindowPosition: " + (currentWindowPosition + 1) + " currentAminoAcid: " + Data.aaTable[currentAminoAcidNr] + " => " + value);
                            }
                            cMatrix[GORPredicter.convertASCharToMatrixId(currentAA)][GORPredicter.convertStructureCharToMatrixId(currentState)][++currentWindowPosition][currentAminoAcidNr] = Integer.parseInt(value);
                            value = "";
                        } else {
                            value += currChar;
                        }
                    }
                }
            }
            if (Predict.debug) {
                System.out.println("Model file parsed! ");
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error reading model file! Error Type: " + e.toString() + " Error Message: " + e.getMessage() + " Stacktrace see above!");

        }
    }

    @Override
    public void initPrediction() {
        for (int stateAA = 0; stateAA < Data.aaTable.length; stateAA++) {
            // calc f_S and allAA
            for (int st = 0; st < Data.secStruct.length; st++) {
                fS[stateAA][st] = 0;
                for (int pos = 0; pos < Data.trainingWindowSize; pos++) {
                    for (int aa = 0; aa < Data.aaTable.length; aa++) {
                        fS[stateAA][st] += cMatrix[stateAA][st][pos][aa];
                        if (Predict.debug) {
                            System.out.println("calculated fs for " + st + ": " + fS[st]);
                        }
                    }
                }
            }

            // calc not f_S and prob for each S
            for (int st = 0; st < Data.secStruct.length; st++) {
                for (int nSt = 0; nSt < Data.secStruct.length; nSt++) {
                    if (st == nSt) {
                        continue;
                    }
                    fNS[stateAA][st] += fS[stateAA][nSt];
                    if (Predict.debug) {
                        System.out.println("calculated nfs for " + st + ": " + fNS[st]);
                    }
                }
            }

            // calc log matix
            for (int st = 0; st < Data.secStruct.length; st++) {
                for (int pos = 0; pos < Data.trainingWindowSize; pos++) {
                    for (int aa = 0; aa < Data.aaTable.length; aa++) {
                        long otherStruct = 0;
                        for (int nSt = 0; nSt < Data.secStruct.length; nSt++) {
                            if (st == nSt) {
                                continue;
                            }
                            otherStruct += cMatrix[stateAA][nSt][pos][aa];
                        }
                        double firstTerm = Math.log((double) cMatrix[stateAA][st][pos][aa]
                                / (double) otherStruct);
                        double secTerm = Math.log((double) fNS[stateAA][st] / (double) fS[stateAA][st]);
                        iMatrix[stateAA][st][pos][aa] = firstTerm + secTerm;

                        if (Predict.debug) {
                            System.out.println("calculated iMatrix for " + stateAA + ", " + st + ", " + pos + ", " + aa + ": " + iMatrix[stateAA][st][pos][aa]);
                        }
                    }
                }
            }
        }
    }

    @Override
    public double[] predict1Example(String aaSeq) {
        try {
            double[] result = new double[Data.secStruct.length];
            // getMiddleChar
            char middleChar = aaSeq.charAt(Data.prevInWindow);       // Note, that Java starts counting by 0
            if(GORPredicter.convertASCharToMatrixId(middleChar) == -1) {
                return result;
            }
            for (int st = 0; st < Data.secStruct.length; st++) {             // for each state to fillout
                double windowSum = 0;
                for (int pos = 0; pos < Data.trainingWindowSize; pos++) {    // for each window position
                    if (GORPredicter.convertASCharToMatrixId(aaSeq.charAt(pos)) == -1) {
                        continue;
                    }
                    windowSum += iMatrix[GORPredicter.convertASCharToMatrixId(middleChar)][st][pos][GORPredicter.convertASCharToMatrixId(aaSeq.charAt(pos))];
                }
                double expWS = Math.exp(windowSum) * ((double) fS[GORPredicter.convertASCharToMatrixId(middleChar)][st] / (double) fNS[GORPredicter.convertASCharToMatrixId(middleChar)][st]);
                result[st] = (expWS / (expWS + 1));
            }
            if (Predict.debug) {
                System.out.println("Prediction for: " + aaSeq);
                for (int i = 0; i < Data.secStruct.length; i++) {
                    System.out.println("Prediction for " + Data.secStruct[i] + ": " + result[i]);
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error executing predict1Example: called with: '" + aaSeq + "'" + e.getMessage() + " - " + e.getLocalizedMessage() + " - " + e.toString());
        }
    }
}