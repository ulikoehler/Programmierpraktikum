/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.validatessp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author schoeffel
 */
public class FactorizeValidation {

    private HashMap<String, String> hash = new HashMap();
    //arraylist for secondary predictions that are to validated
    private ArrayList<DTupel> secondarypair = new ArrayList();
    //safing validation algorihtm output
    private Summary summary;
    private Detailed detailed;

    public FactorizeValidation(File prediction, File reference) {
        //filling hash with reference strucutres
        System.out.println("Filling hash");
        this.fillhash(reference);
        //getting predicted 
        System.out.println("Extracting predictions");
        this.parsefile(prediction);
        //creating validition for each prediction
        System.out.println("Validating predictions");
        this.factorizeInput();
    }

    private void fillhash(File reference) {
        try {
            FileReader input = new FileReader(reference);
            BufferedReader lines = new BufferedReader(input);
            //parsing through reference file list          
            String line = lines.readLine();
            while (line != null) {
                if (!line.equals("")) {
                    if (line.charAt(0) == '>') {
                        String sequence;
                        String secon;
                        line = lines.readLine();
                        sequence = line.substring(3);
                        line = lines.readLine();
                        secon = line.substring(3);
                        hash.put(sequence, secon);
                    }
                }
                line = lines.readLine();
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    private void parsefile(File prediction) {
        try {
            FileReader input = new FileReader(prediction);
            BufferedReader lines = new BufferedReader(input);
            String line = lines.readLine();
            while (line != null) {
                if (!line.equals("")) {
                    if (line.charAt(0) == '>') {
                        String id = line.substring(2);
                        String amino = "";
                        String secon = "";
                        line = lines.readLine();
                        amino += line.substring(3);
                        line = lines.readLine();
                        secon += line.substring(3);
                        //creaing Tupel representing prediction for one sequence
                        secondarypair.add(new DTupel(id, amino, secon));
                    }
                }
                line = lines.readLine();
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    private void factorizeInput() {

        summary = new Summary();
        detailed = new Detailed();
        for (int i = 0; i < secondarypair.size(); i++) {
            //assembling secondary structures pair that is to be validated
            String prediction = secondarypair.get(i).att3;
            String reference = hash.get(secondarypair.get(i).att2);
            //checking if reference structure exists
            if (!(reference == null)) {
                //creating new instance of secondary structure validation algorithm
                StrucValiAlg instance = new StrucValiAlg(prediction, reference);
                //getting validation criteria
                double Q3 = instance.getQ3();
                double QH = instance.getQH();
                double QE = instance.getQE();
                double QC = instance.getQC();
                double SOV = instance.getSOV();
                double SOVH = instance.getSOVH();
                double SOVE = instance.getSOVE();
                double SOVC = instance.getSOVC();
                //creating Tuple representing Validation
                String header = ">" + secondarypair.get(i).att1 + " " + round(Q3) + " " + round(SOV);
                header += " " + round(QH) + " " + round(QE) + " " + round(QC);
                header += " " + round(SOVH) + " " + round(SOVE) + " " + round(SOVC);
                VTupeltxt result = new VTupeltxt(header, secondarypair.get(i).att2, prediction, reference);
                //safing result
                detailed.add(result);
                //safing validation criteria for summary
                summary.addQ3(Q3);
                summary.addQH(QH);
                summary.addQE(QE);
                summary.addQC(QC);
                summary.addSOV(SOV);
                summary.addSOVH(SOVH);
                summary.addSOVE(SOVE);
                summary.addSOVC(SOVC);
            }
        }
    }

    public Detailed getDetail() {
        return detailed;
    }

    public Summary getSummary() {
        return summary;
    }

    private static double round(double d) {
        try {
            Double p = d;
            if (!(p.isNaN()) || !(p.isInfinite())) {
                DecimalFormat numberFormat = new DecimalFormat();
                DecimalFormatSymbols dfs = new DecimalFormatSymbols();
                dfs.setDecimalSeparator('.');
                numberFormat.setGroupingUsed(false);
                numberFormat.setMinimumFractionDigits(6);
                numberFormat.setMaximumFractionDigits(3);
                numberFormat.setDecimalSeparatorAlwaysShown(false);
                numberFormat.setDecimalFormatSymbols(dfs);
                return Double.valueOf(numberFormat.format(d));
            }
        } catch (NumberFormatException ex) {
            return Double.NaN;
        }
        return d;
    }
}
