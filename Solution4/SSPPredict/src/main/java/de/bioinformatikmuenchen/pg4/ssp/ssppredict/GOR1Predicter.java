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
    double[] pSt;

    @Override
    public void init() {
        cMatrix = new long[Data.secStruct.length][Data.trainingWindowSize][Data.aaTable.length];
        iMatrix = new double[Data.secStruct.length][Data.trainingWindowSize][Data.aaTable.length];
        fS = new long[Data.secStruct.length];
        fNS = new long[Data.secStruct.length];
        pSt = new double[Data.secStruct.length];
    }

    @Override
    public void readModelFile(File f) {
        Pattern gor1Header = Pattern.compile("=\\s*([a-zA-Z])\\s*=");
        Pattern matrixContentLine = Pattern.compile("\\s\\d*\\s*");

        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line = null;
            char currentState = ' ';
            int currentAminoAcidNr = -1, currentWindowPosition = -1;
            while ((line = br.readLine().trim()) != null) {
                if (line.startsWith("//") || line.isEmpty()) {
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
                } else {
                    // go through line
                    String value = "";
                    line += " ";        // that's no line to remove; think why!
                    currentAminoAcidNr = GORPredicter.convertASCharToMatrixId(line.charAt(0));      // aa first
                    for (int i = 1; i < line.length(); i++) {
                        char currChar = line.charAt(i);
                        boolean currWhite = (currChar == ' ' || currChar == '\t') ? true : false;
                        if (value.isEmpty() && currWhite) {
                            continue;
                        }
                        if (!value.isEmpty() && currWhite) {
                            cMatrix[currentState][++currentWindowPosition][currentAminoAcidNr] = Integer.parseInt(value);
                            value = "";
                        } else {
                            value += currChar;
                        }
                    }
                }
            }
            br.close();
        } catch (Exception e) {
            throw new RuntimeException("Error reading model file! " + e.toString());
        }
    }

    @Override
    public void initPrediction() {

        // calc f_S and allAA
        long allAA = 0;
        for (int st = 0; st < Data.secStruct.length; st++) {
            fS[st] = 0;
            for (int pos = 0; pos < Data.trainingWindowSize; pos++) {
                for (int aa = 0; aa < Data.aaTable.length; aa++) {
                    fS[st] += cMatrix[st][pos][aa];
                    allAA += cMatrix[st][pos][aa];
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
                pSt[st] = (((double) (fS[st]))
                        / ((double) allAA));
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
                    iMatrix[st][pos][aa] = Math.log(
                            ((double) cMatrix[st][pos][aa])
                            / ((double) otherStruct))
                            + Math.log(
                            ((double) fNS[st])
                            / ((double) fS[st]));
                }
            }
        }
    }

    @Override
    public double[] predict1Example(String aaSeq) {
        double[] result = new double[Data.secStruct.length];
        for (int st = 0; st < Data.secStruct.length; st++) {             // for each state to fillout
            long windowSum = 0;
            for (int pos = 0; pos < Data.trainingWindowSize; pos++) {    // for each window position
                windowSum += iMatrix[st][pos][GORPredicter.convertASCharToMatrixId(aaSeq.charAt(pos))];
            }
            result[st] = ((Math.exp(windowSum) * pSt[st])
                    / ((Math.exp(windowSum) * pSt[st])
                    - (pSt[st])));
        }
        return result;
    }
}
