/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.validation;

/**
 *
 * @author schoeffel
 */
public class AliValiAlg {

    //reference alignment
    private String reftemp;
    private String reftar;
    //candidate alignment
    private String cantemp;
    private String cantar;
    //sequence without gaps
    private String template;
    private String target;

    public AliValiAlg(String refte, String refta, String cante, String canta) {
        reftemp = refte;
        reftar = refta;
        cantemp = cante;
        cantar = canta;
        template = sequence(cantemp);
        target = sequence(cantar);
    }

    public double getSpeci() {
        int correct = 0;
        int aligned = 0;
        for (int i = 0, n = cantemp.length(); i < n; i++) {
            //count aligned residues in candidate
            if (aligned(cantemp, cantar, i)) {
                aligned++;
            }
            //count correctly aligned residues in candidate 
            if (correctlyAligned(i)) {
                correct++;
            }
        }
        if (aligned == -1) {
            return 0;
        }
        System.out.println("correct " + correct);
        System.out.println("aligned " + aligned);
        return (double) correct / aligned;
    }

    public double getSensi() {
        int correct = 0;
        int aligned = 0;
        for (int i = 0, n = cantemp.length(); i < n; i++) {
            //count correctly aligned residues in candidate 
            if (correctlyAligned(i)) {
                correct++;
            }
        }
        for (int i = 0, n = reftemp.length(); i < n; i++) {
            //count aligned residues in candidate
            if (aligned(reftemp, reftar, i)) {
                aligned++;
            }
        }
        if (aligned == 0) {
            return -1;
        }
        System.out.println("correct " + correct);
        System.out.println("aligned " + aligned);
        return (double) correct / aligned;
    }

    public double getCover() {

        double refaligned = 0;
        double canaligned = 0;

        for (int i = 0, n = reftemp.length(); i < n; i++) {
            //count aligned residues in candidate
            if (aligned(reftemp, reftar, i)) {
                refaligned++;
            }
        }
        for (int i = 0, n = cantemp.length(); i < n; i++) {
            //count aligned residues in candidate
            if (aligned(cantemp, cantar, i)) {
                canaligned++;
            }
        }
        if (canaligned == 0) {
            return -1;
        }
        System.out.println("ref " + refaligned);
        System.out.println("can " + canaligned);
        return (double) ((refaligned + canaligned) / canaligned);
    }

    public double getMeanS() {
        // number of sequence for which the shift is defined
        double number = 0;
        //sum of absolut shifts
        double shift = 0;
        //going through target

        
        
        for (int i = 0; i < target.length(); i++){
            
            
            
        }
        
        return (double) shift / number;
    }

    public boolean correctlyAligned(int i) {
        if (cantemp.charAt(i) == '_' || cantar.charAt(i) == '_') {
            return false;
        }
        //positions in sequence without and gaps
        int a = 0;
        int b = 0;
        for (int j = 0; j < i; j++) {
            //getting template postion in sequence
            if (cantemp.charAt(j) != '_') {
                a++;
            }
            //getting target postion in sequence
            if (cantar.charAt(j) != '_') {
                b++;
            }
        }
        //checking positions in reference
        int c = 0;
        int d = 0;
        //geting template position in reference
        for (int j = 0; j <= a; j++) {
            if (reftemp.charAt(j) == '_') {
                a++;
            }
            c++;
        }
        //getting target position in reference
        for (int j = 0; j <= b; j++) {
            if (reftar.charAt(j) == '_') {
                b++;
            }
            d++;
        }
        //check if residues from candidate have same position in reference
        if (c == d) {
            return true;
        } else {
            return false;
        }
    }

    public boolean aligned(String temp, String tar, int i) {
        //check position i for gaps
        if (temp.charAt(i) == '_' || tar.charAt(i) == '_') {
            return false;
        }
        return true;
    }

    public String sequence(String alignmentstring) {
        String temp = "";
        //copy sequence without gaps
        for (int i = 0, n = alignmentstring.length(); i < n; i++) {
            if (alignmentstring.charAt(i) != '_') {
                temp = temp + alignmentstring.charAt(i);
            }
        }
        return temp;
    }
}
