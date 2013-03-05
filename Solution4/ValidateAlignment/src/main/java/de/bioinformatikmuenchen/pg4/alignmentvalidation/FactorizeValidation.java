/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.alignmentvalidation;

import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.regex.Pattern;

/**
 *
 * @author schoeffel
 */
public class FactorizeValidation {
    private static Splitter colonWhitespaceSplitter = Splitter.on(Pattern.compile(":\\s")).omitEmptyStrings().trimResults();
    private static Splitter whitespaceSplitter = Splitter.on(CharMatcher.WHITESPACE).omitEmptyStrings().trimResults();
    //hashmap for reference sequences from HOMSTRAD
    private HashMap<String, String> hash = new HashMap();
    //arraylist for candidate alignments that are to validated
    private ArrayList<FTuple> alignmentpair = new ArrayList();
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
                    //Tuple list to keep track of reference sequences in this ali file
                    ArrayList<ZTuple> list = new ArrayList();
                    Splitter colonSplitter = Splitter.on(Pattern.compile(";")).omitEmptyStrings().trimResults();
                    ArrayList<String> split;
                    while (line != null) {

                        //checking for sequence entry
                        if (line.startsWith(">")) {
                            split = Lists.newArrayList(colonSplitter.split(line));
                            String name = split.get(1);
                            String sequence = "";
                            //Skip structureX line
                            line = homestradtext.readLine();
                            //Assemble all lines
                            line = homestradtext.readLine();
                            while (!line.endsWith("*")) {
                                sequence += line;
                                line = homestradtext.readLine();
                            }
                            sequence += line.substring(0, line.length() - 1);
                            list.add(new ZTuple(name, sequence));
                        }
                        line = homestradtext.readLine();
                    }
                    homestradtext.close();
                    //going through aligment pairs that need to be added to hash
                    for (int i = 0; i < list.size(); i++) {
                        String id1 = list.get(i).att1;
                        String seq1 = list.get(i).att2;
                        for (int j = i + 1; j < list.size(); j++) {
                            String id2 = list.get(j).att1;
                            String seq2 = list.get(j).att2;
                            //these hash tags are still not unique and might be overwritten
                            //but there isnt more information form the input alignment
                            //that could identify the correct reference alignment
                            hash.put(id1 + id2 + sequence(seq1) + sequence(seq2), seq1);
                            hash.put(id2 + id1 + sequence(seq2) + sequence(seq1), seq2);
                        }
                    }

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
            String score;
            String candidatetemplate = "";
            String candidatetarget = "";
            //temp string for reading each line
            String line;
            //initializing temp string
            line = reader.readLine();
            while (line != null) {
                //finding alignment entry
                if (!line.isEmpty()) {
                    if (line.charAt(0) == '>') {
                        ArrayList<String> split = Lists.newArrayList(whitespaceSplitter.split(line));
                        homstradname1 = split.get(0);
                        homstradname2 = split.get(1);
                        score = split.get(2);
                        line = reader.readLine();
                        //getting sequence 1
                        split = Lists.newArrayList(colonWhitespaceSplitter.split(line));
                        candidatetemplate = split.get(1);
                        line = reader.readLine();
                        //getting sequence 2
                        split = Lists.newArrayList(colonWhitespaceSplitter.split(line));
                        candidatetarget = split.get(1);
                        //safing alignment and reference pair in Arraylist
                        alignmentpair.add(new FTuple(homstradname1, homstradname2, candidatetemplate, candidatetarget,score));
                    }
                }
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    private void factorizeInput() {
        //creating summary and detailed representations
        summary = new Summary();
        detailed = new Detailed();
        for (int i = 0; i < alignmentpair.size(); i++) {
            //assembling sequences
            String candidatetemplate = alignmentpair.get(i).att3;
            String candidatetarget = alignmentpair.get(i).att4;
            String seqid1 = alignmentpair.get(i).att1.substring(1);
            String seqid2 = alignmentpair.get(i).att2;
            String referencetemplate = hash.get(seqid1 + seqid2 + sequence(candidatetemplate) + sequence(candidatetarget));
            String referencetarget = hash.get(seqid2 + seqid1 + sequence(candidatetarget) + sequence(candidatetemplate));
            //checking if reference alignment exists
            if (referencetemplate == null || referencetarget == null) {
                System.err.println("Reference pair doesnt exist");
            } else {
                if (referencetemplate.length() != referencetarget.length()) {
                    System.err.println("False reference identifications");
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
                    String header = alignmentpair.get(i).att1 + " " + alignmentpair.get(i).att2+ " "+ alignmentpair.get(i).att5 + " " + round(sensi) + " " + round(speci) + " " + round(cover) + " " + round(means) + " " + round(inver);
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
    }

    public Detailed getDetail() {
        return detailed;
    }

    public Summary getSummary() {
        return summary;
    }

    public static double round(double d) {
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
        return d;
    }

    public static String sequence(String gapseq) {
        String temp = "";
        //copy sequence without gaps
        for (int i = 0, n = gapseq.length(); i < n; i++) {
            if (gapseq.charAt(i) != '-') {
                temp = temp + gapseq.charAt(i);
            }
        }
        return temp;
    }
}
