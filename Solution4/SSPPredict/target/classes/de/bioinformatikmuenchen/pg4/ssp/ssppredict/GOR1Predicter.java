/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.ssp.ssppredict;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author spoeri
 */
public class GOR1Predicter extends GORPredicter {

    long[] fS;
    long[] fNS;
    long[][][] cMatrix;
    double[][][] iMatrix;

    @Override
    public void init() {
        cMatrix = new long[Data.secStruct.length][Data.trainingWindowSize][Data.aaTable.length];
        iMatrix = new double[Data.secStruct.length][Data.trainingWindowSize][Data.aaTable.length];
        fS = new long[Data.secStruct.length];
        fNS = new long[Data.secStruct.length];
    }

    @Override
    public void readModelFile(File f) {
        Pattern gor1Header = Pattern.compile("=\\s*([a-zA-Z])\\s*=");

        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line = null;
            char currentState = ' ';
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
                    Matcher m = gor1Header.matcher(line);
                    if (m.find()) {
                        String r = m.group(1);
                        currentState = r.charAt(0);
                    } else {
                        throw new Exception("Invalid model file!");
                    }
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
                            cMatrix[GORPredicter.convertStructureCharToMatrixId(currentState)][++currentWindowPosition][currentAminoAcidNr] = Integer.parseInt(value);
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

        // calc f_S and allAA
        for (int st = 0; st < Data.secStruct.length; st++) {
            fS[st] = 0;
            for (int pos = 0; pos < Data.trainingWindowSize; pos++) {
                for (int aa = 0; aa < Data.aaTable.length; aa++) {
                    fS[st] += cMatrix[st][pos][aa];
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
                fNS[st] += fS[nSt];
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
                        otherStruct += cMatrix[nSt][pos][aa];
                    }
                    double firstTerm = Math.log((double) cMatrix[st][pos][aa]
                            / (double) otherStruct);
                    double secTerm = Math.log((double) fNS[st] / (double) fS[st]);
                    iMatrix[st][pos][aa] = firstTerm + secTerm;
                    
                    if (Predict.debug) {
                        System.out.println("calculated iMatrix for " + st + ", " + pos + ", " + aa + ": " + iMatrix[st][pos][aa]);
                    }
                }
            }
        }
    }

    @Override
    public double[] predict1Example(String aaSeq) {
        try {
            double[] result = new double[Data.secStruct.length];
            for (int st = 0; st < Data.secStruct.length; st++) {             // for each state to fillout
                double windowSum = 0;
                for (int pos = 0; pos < Data.trainingWindowSize; pos++) {    // for each window position
                    if (GORPredicter.convertASCharToMatrixId(aaSeq.charAt(pos)) == -1) {
                        continue;
                    }
                    windowSum += iMatrix[st][pos][GORPredicter.convertASCharToMatrixId(aaSeq.charAt(pos))];
                }
                double expWS = Math.exp(windowSum) * ((double) fS[st] / (double) fNS[st]);
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
