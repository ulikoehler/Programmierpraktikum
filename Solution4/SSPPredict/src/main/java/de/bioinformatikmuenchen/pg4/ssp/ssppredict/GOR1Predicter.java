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
        Pattern matrixHeader = Pattern.compile("=\\s*([a-zA-Z])\\s*=");
        Pattern matrixContentLine = Pattern.compile("\\s\\d*\\s*");

        try {
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line = null;
            while ((line = br.readLine()) != null) {
            }
        } catch (Exception e) {
            throw new RuntimeException("Error reading model file! " + e.toString());
        }
    }

    @Override
    public void initPrediction() {
        
    }

    @Override
    public double[] predict1Example(String aaSeq) {
        return new double[Data.secStruct.length];
    }
}
