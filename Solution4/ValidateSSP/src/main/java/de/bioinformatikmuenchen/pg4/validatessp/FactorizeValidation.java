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
                        String name = line.substring(2, 6);
                        String secon = "";
                        line = lines.readLine();
                        if (!line.equals("")) {
                            while (line.charAt(0) != '>' && !line.substring(0, 3).equals("SS ")) {
                                line = lines.readLine();
                                if (line.equals("")) {
                                    break;
                                }
                            }
                        }
                        if (line.substring(0, 3).equals("SS ")) {
                            secon += line.substring(3);
                            line = lines.readLine();
                            if (!line.equals("")) {
                                while (line.charAt(0) != '>') {
                                    secon += line;
                                }
                            }
                            hash.put(name, secon);
                        }
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
                  if(!line.equals("")){
                      if(line.charAt(0)== '>'){
                          String id = line.substring(2);
                          String amino = "";
                          String secon = "";
                          line = lines.readLine();
                          amino += line.substring(3);
                          line = lines.readLine(); 
                          secon += line.substring(3);
                          
                          System.out.println(id);
                          System.out.println(amino);
                          System.out.println(secon);
                          secondarypair.add(new DTupel(id,amino,secon));
                      }
                  }
                  line = lines.readLine();
              }
        } catch (Exception e) {
            System.err.println(e);
        }
    }
    
    private void factorizeInput(){
        
        summary = new Summary();
        detailed = new Detailed();
        
        
    }

    public Detailed getDetail() {
        return detailed;
    }

    public Summary getSummary() {
        return summary;
    }

    private static double round(double d) {
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
}
