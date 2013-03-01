/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.ssp.ssppredict;

import java.util.LinkedList;
import java.util.ArrayList;

/**
 *
 * @author spoeri
 */
public class PredictionResult {

    public LinkedList<String[]> sequences;
    public LinkedList<double[][]> probabilities;
    private String[][] s;
    private double[][][] p;

    private void initArrays() {
        s = sequences.toArray(new String[sequences.size()][]);
        p = probabilities.toArray(new double[probabilities.size()][][]);
    }

    public void add(String id, String seq, double[][] probability) {
        sequences.add(new String[]{id, seq});
        probabilities.add(probability);
    }

    // convert to
    public String getHTMLRepresentation(boolean printProbabilities) {
        initArrays();

        StringBuilder result = new StringBuilder("<html><head><title>secondary structure prediction</title></head><body>");

        for (int i = 0; i < s.length; i++) {
            result.append(">").append(s[i][0]).append("<br>");   // append id
            result.append("AS ").append(s[i][1]).append("<br>"); // and the seq
            // predicted
            result.append("PS " + concatN('-', Data.prevInWindow));
            for (int z = 0; z < p.length; z++) {
                result.append(color(GORPredicter.predictionArgMax(p[i][z])));
            }
            result.append(concatN('-', Data.trainingWindowSize - (Data.prevInWindow + 1))).append("<br>");
            // probabilities
            if (printProbabilities) {
                for (int stateProb = Data.secStruct.length - 1; stateProb >= 0; stateProb--) {
                    result.append("P").append(Data.secStruct[stateProb]).append(" "); // concat the other way round
                    result.append(concatN('-', Data.prevInWindow));
                    for (int z = 0; z < p.length; z++) {
                        result.append(color((char) GORPredicter.p2Int(p[i][z][stateProb])));
                    }
                    result.append(concatN('-', Data.trainingWindowSize - (Data.prevInWindow + 1))).append("<br>");
                }
            }
        }
        
        result.append("</body></html>");

        return result.toString();
    }

    public String getTXTRepresentation(boolean printProbabilities) {
        initArrays();

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < s.length; i++) {
            result.append(">").append(s[i][0]).append("\n");   // append id
            result.append("AS ").append(s[i][1]).append("\n"); // and the seq
            // predicted
            result.append("PS " + concatN('-', Data.prevInWindow));
            for (int z = 0; z < p.length; z++) {
                result.append(GORPredicter.predictionArgMax(p[i][z]));
            }
            result.append(concatN('-', Data.trainingWindowSize - (Data.prevInWindow + 1))).append("\n");
            // probabilities
            if (printProbabilities) {
                for (int stateProb = Data.secStruct.length - 1; stateProb >= 0; stateProb--) {
                    result.append("P").append(Data.secStruct[stateProb]).append(" "); // concat the other way round
                    result.append(concatN('-', Data.prevInWindow));
                    for (int z = 0; z < p.length; z++) {
                        result.append(p[i][z][stateProb]);
                    }
                    result.append(concatN('-', Data.trainingWindowSize - (Data.prevInWindow + 1))).append("\n");
                }
            }
        }

        return result.toString();
    }

    private String concatN(char x, int n) {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < n; i++) {
            s.append(x);
        }
        return s.toString();
    }

    private String color(char n) {
        return "<font color=\'" + Data.colors[((int) n) % Data.colors.length] + "\'>" + n + "</font>";   // I know font is depricated (IE also)
    }
}
