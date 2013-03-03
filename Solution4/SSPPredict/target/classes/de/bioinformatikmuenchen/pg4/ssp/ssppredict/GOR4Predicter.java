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

    long[][][][][][] cMatrix;
    long[][][][] gor3cMatrix;
    double fact1 = 2.0d / 17.0d;
    double fact2 = 15.0d / 17.0d;

    @Override
    public void init() {
        cMatrix = new long[Data.aaTable.length][Data.trainingWindowSize][Data.aaTable.length][Data.aaTable.length][Data.trainingWindowSize][Data.secStruct.length];
        gor3cMatrix = new long[Data.aaTable.length][Data.secStruct.length][Data.trainingWindowSize][Data.aaTable.length];
    }

    @Override
    public void readModelFile(File f) {
        // Trainer output:
        // result.append("=").append(Data.secStruct[ss]).append(",").append(Data.aaTable[aaAbove]).append(",").append(Data.aaTable[aaSom]).append(",").append(aaSomPos - Data.prevInWindow).append("=\n\n");
        // result.append(cMatrix[aaSom][aaSomPos][aaAbove][aaRel][relPos][ss]).append("\t");
        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line = null;
            char ss = ' ', aaAbove = ' ', aaSom = ' ';
            int aaSomPos = 0;
            int aaRel = -1;
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
                if (line.startsWith("+")) {
                    // quasi gor3
                    char currentState = ' ';
                    char currentAA = ' ';
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
                            aaRel = GORPredicter.convertASCharToMatrixId(line.charAt(0));      // aa first
                            for (int i = 1; i < line.length(); i++) {
                                char currChar = line.charAt(i);
                                boolean currWhite = (currChar == ' ' || currChar == '\t') ? true : false;
                                if (value.isEmpty() && currWhite) {
                                    continue;
                                }
                                if (!value.isEmpty() && currWhite) {
                                    if (Predict.debug) {
                                        System.out.println("  input in matrix: state: " + currentState + " currentWindowPosition: " + (currentWindowPosition + 1) + " currentAminoAcid: " + Data.aaTable[aaRel] + " => " + value);
                                    }
                                    gor3cMatrix[GORPredicter.convertASCharToMatrixId(currentAA)][GORPredicter.convertStructureCharToMatrixId(currentState)][++currentWindowPosition][aaRel] = Integer.parseInt(value);
                                    value = "";
                                } else {
                                    value += currChar;
                                }
                            }
                        }
                    }
                } else if (line.startsWith("=")) {
                    // getCurrentState
                    String[] r = line.substring(1, line.length() - 1).split(",");
                    ss = r[0].trim().charAt(0);
                    aaAbove = r[1].trim().charAt(0);
                    aaSom = r[2].trim().charAt(0);
                    aaSomPos = Integer.parseInt(r[3].trim()) + Data.prevInWindow;
                    if (Predict.debug) {
                        System.out.println(" state line! (now in state: " + ss + ", " + aaAbove + ", " + aaSom + ", " + aaSomPos + ")");
                    }
                } else {
                    if (Predict.debug) {
                        System.out.println(" data line!");
                    }
                    // go through line
                    String value = "";
                    int relPos = -1;
                    line += " ";        // that's no line to remove; think why!
                    aaRel = GORPredicter.convertASCharToMatrixId(line.charAt(0));      // aa first
                    for (int i = 1; i < line.length(); i++) {
                        char currChar = line.charAt(i);
                        boolean currWhite = (currChar == ' ' || currChar == '\t') ? true : false;
                        if (value.isEmpty() && currWhite) {
                            continue;
                        }
                        if (!value.isEmpty() && currWhite) {
                            if (Predict.debug) {
                                System.out.println("  input in matrix: state: " + ss + ", " + aaAbove + ", " + aaSom + ", " + aaSomPos + " currentWindowPosition: " + (relPos + 1) + " currentAminoAcid: " + Data.aaTable[aaRel] + " => " + value);
                            }
                            // result.append(cMatrix[aaSom][aaSomPos][aaAbove][aaRel][relPos][ss])
                            cMatrix[GORPredicter.convertASCharToMatrixId(aaSom)][aaSomPos][GORPredicter.convertASCharToMatrixId(aaAbove)][aaRel][++relPos][GORPredicter.convertStructureCharToMatrixId(ss)] = Integer.parseInt(value);
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
        // Nothing to do!
    }

    @Override
    public double[] predict1Example(String aaSeq) {
        double[] result = new double[Data.secStruct.length];
        int middleChar = GORPredicter.convertASCharToMatrixId(aaSeq.charAt(Data.prevInWindow));       // Note, that Java starts counting by 0
        if (middleChar == -1) {
            return result;
        }
        for (int secundaryStructure = 0; secundaryStructure < Data.secStruct.length; secundaryStructure++) {        // foreach result
            // calc first sum
            double firstSum = 0;
            for (int k = 0; k < Data.trainingWindowSize; k++) {                                     // sum from k=-m to m
                for (int l = k + 1; l < Data.trainingWindowSize; l++) {                             // sum from l=-m to m; l>k
                    int aaAtPosK = GORPredicter.convertASCharToMatrixId(aaSeq.charAt(k));           // get a_j+k
                    if (aaAtPosK == -1) {
                        break;
                    }
                    int aaAtPosL = GORPredicter.convertASCharToMatrixId(aaSeq.charAt(l));           // get a_j+l
                    if (aaAtPosL == -1) {
                        break;
                    }
                    // matrix has the following structure:
                    // [aaTable][trainingWindowSize][aaTable][aaTable][trainingWindowSize][secStruct]
                    // [amino acid somewhere in window][position of acid somewhere in window][amino acid in the middle]
                    // [amino acid relative to the middle][position relative to the middle][secondary state in middle] = count
                    double p = cMatrix[aaAtPosK][k][middleChar][aaAtPosL][l][secundaryStructure];   // p for state
                    double nP = 0;
                    for (int nSecStruct = 0; nSecStruct < Data.secStruct.length; nSecStruct++) {    // get "not p state"
                        if (nSecStruct == secundaryStructure) {                                     // don't add to current state
                            continue;
                        }
                        nP += cMatrix[aaAtPosK][k][middleChar][aaAtPosL][l][nSecStruct];            // sum up
                    }
                    firstSum += Math.log((1.0 + p) / (2.0 + nP));                                                   // sum up log values
                }
            }
            // calc second sum
            double secondSum = 0;
            for (int pos = 0; pos < Data.trainingWindowSize; pos++) {
                // [Data.aaTable.length][Data.secStruct.length][Data.trainingWindowSize][Data.aaTable.length]
                int aaAtPos = GORPredicter.convertASCharToMatrixId(aaSeq.charAt(pos));
                if (aaAtPos == -1) {
                    break;
                }
                double p = gor3cMatrix[middleChar][secundaryStructure][pos][aaAtPos];
                double nP = 0;
                for (int nSecStruct = 0; nSecStruct < Data.secStruct.length; nSecStruct++) {
                    if (nSecStruct == secundaryStructure) {
                        continue;
                    }
                    nP += gor3cMatrix[middleChar][nSecStruct][pos][aaAtPos];
                }
                secondSum += Math.log((p + 1.0) / (2.0 + nP));
            }
            // calc approx
            double eX = Math.exp((fact1 * firstSum) - (fact2 * secondSum));
            // append result
            result[secundaryStructure] = eX / (1 + eX);
        }
        return result;
    }

    public String getMatrixRepresentation() {

        // =State, AS, AS, Pos=
        //        POS
        //  AS   count

        StringBuilder result = new StringBuilder("// Matrix6D\n\n");

        for (int ss = 0; ss < Data.secStruct.length; ss++) {
            for (int aaAbove = 0; aaAbove < Data.aaTable.length; aaAbove++) {
                for (int aaSom = 0; aaSom < Data.aaTable.length; aaSom++) {
                    for (int aaSomPos = 0; aaSomPos < Data.trainingWindowSize; aaSomPos++) {
                        result.append("=").append(Data.secStruct[ss]).append(",").append(Data.aaTable[aaAbove]).append(",").append(Data.aaTable[aaSom]).append(",").append(aaSomPos - Data.prevInWindow).append("=\n\n");
                        for (int aaRel = 0; aaRel < Data.aaTable.length; aaRel++) {
                            result.append(Data.aaTable[aaRel]).append("\t");
                            for (int relPos = 0; relPos < Data.trainingWindowSize; relPos++) {
                                result.append(cMatrix[aaSom][aaSomPos][aaAbove][aaRel][relPos][ss]).append("\t");
                            }
                            result.append('\n');
                        }
                        result.append("\n");
                    }
                }
            }
        }

        result.append("+++++++++++++++++++++++++++\n\n");

        return result.toString();

    }
}