/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.ssp.ssppredict;

import java.io.PrintStream;
import java.util.LinkedList;

/**
 *
 * @author spoeri
 */
public class PredictionResult {

    public LinkedList<String[]> sequences = new LinkedList<String[]>();
    public LinkedList<double[][]> probabilities = new LinkedList<double[][]>();
    private String[][] s;
    private double[][][] p;

    private void initArrays() {
        s = sequences.toArray(new String[sequences.size()][]);
        p = probabilities.toArray(new double[probabilities.size()][][]);
    }

    public void add(String id, String seq, double[][] probability) {
        String[] str = new String[]{id, seq};
        sequences.add(str);
        probabilities.add(probability);
        if (Predict.debug) {
            System.out.println("Prediction Result added! (" + id + ", " + seq + ", " + probability.toString() + ")");
        }
    }

    // convert to
    public void getHTMLRepresentation(boolean printProbabilities, PrintStream out) {// TODO
        initArrays();

        out.append("<html><head><title>secondary structure prediction</title></head><body><div style=\"font-family: Courier New\">");

        for (int i = 0; i < s.length; i++) {
            out.append("&gt;").append(s[i][0]).append("<br>");   // append id
            out.append("AS ").append(s[i][1]).append("<br>"); // and the seq
            // predicted
            out.append("PS " + concatN('-', Data.prevInWindow));
            for (int z = 0; z < p[i].length; z++) {
                if (Predict.debug) {
                    System.out.println("Debug position probability:");
                    for (int st = 0; st < Data.secStruct.length; st++) {
                        System.out.println("  @pos: " + z + " value fpr " + Data.secStruct[st] + " = " + p[i][z][st]);
                    }
                    System.out.println(" => max: " + GORPredicter.predictionArgMax(p[i][z]));
                }
                out.append(GORPredicter.predictionArgMax(p[i][z]));
            }
            out.append(concatN('-', Data.trainingWindowSize - (Data.prevInWindow + 1))).append("<br>");
            // probabilities
            if (printProbabilities) {
                for (int stateProb = Data.secStruct.length - 1; stateProb >= 0; stateProb--) {
                    out.append("P").append(Data.secStruct[stateProb]).append(" "); // concat the other way round
                    out.append(concatN('-', Data.prevInWindow));
                    for (int z = 0; z < p[i].length; z++) {
                        out.append(color(GORPredicter.p2Int(p[i][z][stateProb])));
                    }
                    out.append(concatN('-', Data.trainingWindowSize - (Data.prevInWindow + 1))).append("<br>");
                }
            }
            out.append("<br>");
        }

        out.append("</div></body></html>");
    }

    public void getTXTRepresentation(boolean printProbabilities, PrintStream out) {
        initArrays();

        for (int i = 0; i < s.length; i++) {
            out.append(">").append(s[i][0]).append("\n");   // append id
            out.append("AS ").append(s[i][1]).append("\n"); // and the seq
            // predicted
            out.append("PS " + concatN('-', Data.prevInWindow));
            for (int z = 0; z < p[i].length; z++) {
                if (Predict.debug) {
                    System.out.println("Debug position probability:");
                    for (int st = 0; st < Data.secStruct.length; st++) {
                        System.out.println("  @pos: " + z + " value fpr " + Data.secStruct[st] + " = " + p[i][z][st]);
                    }
                    System.out.println(" => max: " + GORPredicter.predictionArgMax(p[i][z]));
                }
                out.append(GORPredicter.predictionArgMax(p[i][z]));
            }
            out.append(concatN('-', Data.trainingWindowSize - (Data.prevInWindow + 1))).append("\n");
            // probabilities
            if (printProbabilities) {
                for (int stateProb = Data.secStruct.length - 1; stateProb >= 0; stateProb--) {
                    out.append("P").append(Data.secStruct[stateProb]).append(" "); // concat the other way round
                    out.append(concatN('-', Data.prevInWindow));
                    for (int z = 0; z < p[i].length; z++) {
                        out.write(GORPredicter.p2Int(p[i][z][stateProb]));
                    }
                    out.append(concatN('-', Data.trainingWindowSize - (Data.prevInWindow + 1))).append("\n");
                }
            }
        }
    }

    private String concatN(char x, int n) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < n; i++) {
            s.append(x);
        }
        return s.toString();
    }

    private String color(int n) {
        n = (n > 0) ? n : -n;
        return "<font color=\'" + Data.colors[n % Data.colors.length] + "\'>" + n + "</font>";   // I know font is depricated (IE also)
    }
}
