/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.ssp.ssppredict;

import java.io.*;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author spoeri
 */
public abstract class GORPredicter {

    // util
    public static int convertStructureCharToMatrixId(char x) {
        for (int i = 0; i < Data.secStruct.length; i++) {
            if (Data.secStruct[i] == x) {
                return i;
            }
        }
        return -1;
    }

    public static int convertASCharToMatrixId(char x) {
        for (int i = 0; i < Data.aaTable.length; i++) {
            if (Data.aaTable[i] == x) {
                return i;
            }
        }
        return -1;
    }

    // Preprocessing
    public static char[] getStatesFromFile(File model, Predict.simpleGorMethods method) {
        Pattern gor_1 = Pattern.compile("=\\s*([a-zA-Z])\\s*=");
        Pattern gor_3 = Pattern.compile("=\\s*[a-zA-Z]\\s*,\\s*([a-zA-Z])\\s*=");
        Pattern gor_4 = Pattern.compile("=\\s*([a-zA-Z])\\s*,\\s*[a-zA-Z]\\s*,\\s*[a-zA-Z]\\s*,\\s*.\\d*\\s*=");
        Pattern patternToUse = ((method.name() == method.gor1.name()) ? gor_1 : ((method.name() == method.gor3.name()) ? gor_3 : gor_4));

        ArrayList<Character> results = new ArrayList<Character>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(model));
            String line = null;
            while ((line = br.readLine()) != null) {
                Matcher m = patternToUse.matcher(line);
                if (m.find()) {
                    String r = m.group(1);
                    char t = r.charAt(0);
                    if ((!results.contains(t) && t != 0)) {
                        results.add(t);
                    }
                }
            }
            br.close();
        } catch (Exception e) {
            System.err.println("Error reading model file! " + e.toString());
            System.exit(1);
        }

        char ret[] = new char[results.size()];
        int i = 0;
        for (Character c : results) {
            ret[i++] = c;
        }

        return ret;
    }

    public static Predict.simpleGorMethods getGorFromFile(File model) {
        Pattern gor1 = Pattern.compile("=\\s*[a-zA-Z]\\s*=");
        Pattern gor3 = Pattern.compile("=\\s*[a-zA-Z]\\s*,\\s*[a-zA-Z]\\s*=");
        Pattern gor4 = Pattern.compile("=\\s*[a-zA-Z]\\s*,\\s*[a-zA-Z]\\s*,\\s*[a-zA-Z]\\s*,\\s*.\\d*\\s*=");

        try {
            BufferedReader br = new BufferedReader(new FileReader(model));
            String line = "";
            while ((line = br.readLine()) != null) {
                if (line.trim().startsWith("//") || line.trim().isEmpty()) {
                    continue;
                }
                if (gor1.matcher(line).find()) {
                    br.close();
                    return Predict.simpleGorMethods.gor1;
                }
                if (gor3.matcher(line).find()) {
                    br.close();
                    return Predict.simpleGorMethods.gor3;
                }
                if (gor4.matcher(line).find()) {
                    br.close();
                    return Predict.simpleGorMethods.gor4;
                }
                break;  // no valid model file
            }
            br.close();
        } catch (Exception e) {
            System.err.println("Error reading model file! " + e.toString());
            System.exit(1);
        }
        System.err.println("invalid model file!");
        System.exit(1);
        return Predict.simpleGorMethods.gor1;
    }

    public static int getWindowSizeFromFile(File model) {
        Pattern matrixLine = Pattern.compile("\\s*\\w\\s*(\\d*\\s*)*");

        try {
            BufferedReader br = new BufferedReader(new FileReader(model));
            String line = "";
            while ((line = br.readLine()) != null) {
                if (line.trim().startsWith("//") || line.trim().startsWith("=") || line.trim().isEmpty()) {
                    continue;
                }
                if (matrixLine.matcher(line).find()) {
                    // amino acid spaces or tabs value1 spaces or tabs ... value n => get number n
                    int count = 0;
                    boolean state = false;   // in numeric state

                    for (int i = 0; i < line.length(); i++) {
                        int cur = (int) line.charAt(i);
                        if (48 <= cur && cur <= 57) { // numeric
                            if (!state) {
                                count++;
                            }
                            state = true;
                        } else {
                            state = false;
                        }
                    }
                    br.close();
                    return count;
                }
                break;  // no valid model file
            }
        } catch (Exception e) {
            System.err.println("Error reading model file! " + e.toString());
            System.exit(1);
        }
        System.err.println("invalid model file!");
        return -1;
    }

    // Prediction
    public static char predictionArgMax(double[] pAA) {
        if (pAA == null) {
            return '_';
        }
        int maxPos = 0;
        for (int i = 0; i < pAA.length; i++) {
            if (pAA[maxPos] < pAA[i]) {
                maxPos = i;
            }
        }
        return Data.secStruct[maxPos];
    }

    public static int p2Int(double p) {
        int nr = (int) Math.max(Math.round(p * 10), 0);
        return (nr > 9) ? 9 : nr;
    }

    public abstract void init();

    public abstract void readModelFile(File f);

    public abstract void initPrediction();

    public PredictionResult predictFileSequences(File f) {
        PredictionResult result = new PredictionResult();
        try {
            BufferedReader bf = new BufferedReader(new FileReader(f));
            String line;
            String id = null, seq = null;
            while ((line = bf.readLine()) != null) {
                if (Predict.debug) {
                    System.out.println("Process line: " + line);
                }
                if (line.startsWith(";") || line.startsWith("//") || line.trim().isEmpty() || (line.charAt(2) == ' ' && !line.startsWith("AS"))) {
                    continue;
                }
                if (line.startsWith(">")) {
                    if (id != null && seq != null) {
                        if (Predict.debug) {
                            System.out.println("Found Sequence: ID: '" + id + "'\nseq: " + seq);
                        }
                        result.add(id, seq, predictSequence(seq));
                    }
                    id = line.substring(1);
                    seq = "";
                } else {
                    if (line.charAt(2) == ' ' || line.charAt(2) == '\t') {
                        seq += line.substring(3);
                    } else {
                        seq += line;
                    }
                }
            }
            if (id != null && seq != null) {
                if (Predict.debug) {
                    System.out.println("Found Sequence: ID: '" + id + "'\nseq: " + seq);
                }
                result.add(id, seq, predictSequence(seq));
            }
            bf.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error reading fasta file! " + e.toString() + " see stac trace above!");
        }
        return result;
    }

    public double[][] predictSequence(String aaSeq) {
        double[][] result = new double[aaSeq.length() - Data.trainingWindowSize + 1][];
        try {
            for (int startPos = 0; startPos < aaSeq.length() - Data.trainingWindowSize + 1; startPos++) {
                String subSeq = aaSeq.substring(startPos, startPos + Data.trainingWindowSize);
                if (Predict.debug) {
                    System.out.println("Call prediction for: " + subSeq);
                }
                result[startPos] = predict1Example(subSeq);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error while predicting single Sequnece! Stacktrace see above!");
            System.exit(1);
        }
        return result;
    }

    public abstract double[] predict1Example(String aaSeq);

    // Postprocessing
    public static PredictionResult postprocess(PredictionResult results) {
        // TODO: implement + build more tests
        // an helix has an minimal size of 4 so it doesn't make sens to have less than 4 H's after each other

        return results;
    }
}
