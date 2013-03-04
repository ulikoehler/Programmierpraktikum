/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.alignmentvalidation;

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 *
 * @author schoeffel
 */
public class FactorizeValidation {
    //hashmap for reference sequences from HOMSTRAD

    private HashMap<String, String> hash = new HashMap();
    //arraylist for candidate alignments that are to validated
    private ArrayList<VTuple> alignmentpair = new ArrayList();
    //safing validation algorihtm output
    private Summary summary;
    private Detailed detailed;

    public FactorizeValidation(File align, File reflist) {
        //safing alignment for each reference sequence in hash
        System.out.println("Filling Hashmap ");
        this.fillhash(reflist);
        //extracting candidate alingment
        System.out.println("Parsing alignments ");
        this.parsefile(align);
        //validating all candidate alignments
        System.out.println("Factorizing inputs ");
        this.factorizeInput();

    }

    private void fillhash(File reflist) {
        try {
            FileReader input = new FileReader(reflist);
            BufferedReader paths = new BufferedReader(input);
            //parsing through reference file list          
            String path = paths.readLine();
            while (path != null) {
                try {
                    //System.out.println("path " + path);
                    //homestrad file that is to be opened
                    File onehitwonder1 = new File(path);
                    FileReader onehitwonder2 = new FileReader(onehitwonder1);
                    BufferedReader homestradtext = new BufferedReader(onehitwonder2);
                    //parsing through homestradfile
                    String line = homestradtext.readLine();
                    while (line != null) {
                        //checking for sequence entry
                        if (line.charAt(0) == '>') {
                            String name;
                            String sequence = "";
                            name = line.substring(4, 8);
                            line = homestradtext.readLine();
                            line = homestradtext.readLine();
                            while (line.charAt(line.length() - 1) != '*') {
                                sequence += line;
                                line = homestradtext.readLine();
                            }
                            sequence += line.substring(0, line.length() - 1);
                            hash.put(name, sequence);
                        }
                        line = homestradtext.readLine();
                    }
                    homestradtext.close();

                } catch (Exception e) {
                    System.err.println(e);
                }
                path = paths.readLine();
            }
            paths.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    private void parsefile(File align) {
        try {
            //readers
            FileReader input = new FileReader(align);
            BufferedReader reader = new BufferedReader(input);

            String homstradname1;
            String homstradname2;
            String candidatetemplate = "";
            String candidatetarget = "";
            //temp string for reading each line
            String line;
            //initializing temp string
            line = reader.readLine();
            while (line != null) {
                //finding alignment entry
                if (!line.equals("")) {
                    if (line.charAt(0) == '>') {
                        homstradname1 = line.substring(1, 8);
                        homstradname2 = line.substring(9, 16);
                        line = reader.readLine();
                        //getting sequence 1
                        candidatetemplate = line.substring(9);
                        line = reader.readLine();
                        //getting sequence 2
                        candidatetarget = line.substring(9);
                        //safing alignment and reference pair in Arraylist
                        alignmentpair.add(new VTuple(homstradname1, homstradname2, candidatetemplate, candidatetarget));
                    }
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Caught one");
            System.err.println(e);
        }
    }

    private void factorizeInput() {
        //creating summary and detailed representations
        summary = new Summary();
        detailed = new Detailed();
        for (int i = 0; i < alignmentpair.size(); i++) {
            //assembling sequences
            String seqid1 = alignmentpair.get(i).att1.substring(0, 4);
            String seqid2 = alignmentpair.get(i).att2.substring(0, 4);
            String candidatetemplate = alignmentpair.get(i).att3;
            String candidatetarget = alignmentpair.get(i).att4;
            String referencetemplate = hash.get(seqid1);
            String referencetarget = hash.get(seqid2);
            //checking if reference alignment exists
            if (referencetemplate == null || referencetarget == null) {
                System.out.println("Reference pair doesnt exist");
            } else {
                //creating new instance of validation algorithm
                AliValiAlg instance = new AliValiAlg(candidatetemplate, candidatetarget, referencetemplate, referencetarget);
                //getting validation criteria
                double sensi = instance.getSensi();
                double speci = instance.getSpeci();
                double cover = instance.getCover();
                double means = instance.getMeanS();
                double inver = instance.getInver();
                //creating tuple representing validation
                String header = ">" + alignmentpair.get(i).att1 + " " + alignmentpair.get(i).att2 + " " + round(sensi) + " " + round(speci) + " " + round(cover) + " " + round(means) + " " + round(inver);
                FTuple result = new FTuple(header, candidatetemplate, candidatetarget, referencetemplate, referencetarget);
                //safing result
                detailed.add(result);
                //safing validation criteria for summary file
                summary.addSensi(sensi);
                summary.addSpeci(speci);
                summary.addCover(cover);
                summary.addMeans(means);
                summary.addInver(inver);
            }
        }
    }

    public Detailed getDetail() {
        return detailed;
    }

    public Summary getSummary() {
        return summary;
    }

    public static double round(double d) {
        Double p = d;
        if (!(p.isNaN())) {
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
        return d;
    }
}
