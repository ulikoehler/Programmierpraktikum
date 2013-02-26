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
    //helping methods
    private boolean helpboolean;

    public AliValiAlg(String refte, String refta, String cante, String canta) {
        assert refte.length() == refta.length();
        assert cante.length() == canta.length();
        reftemp = refte;
        reftar = refta;
        cantemp = cante;
        cantar = canta;
        template = sequence(cantemp);
        target = sequence(cantar);
        assert template.equals(sequence(refte));
        assert target.equals(sequence(refta));
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

        double bothaligned = 0;
        double canaligned = 0;

        for (int i = 0; i < target.length(); i++) {
            //temp value
            int p = i;
            int a = -1;
            //getting position in reference
            for (int j = 0; j <= p; j++) {
                if (reftar.charAt(j) == '_') {
                    p++;
                }
                a++;
            }
            //reseting temp value
            p = i;
            int b = -1;
            //getting position in candidate
            for (int j = 0; j <= p; j++) {
                if (cantar.charAt(j) == '_') {
                    p++;
                }
                b++;
            }
            //checking if residue is aligned in both alignments
            if (aligned(reftemp, reftar, a) && aligned(cantemp, cantar, b)) {
                bothaligned++;
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
        System.out.println("ref " + bothaligned);
        System.out.println("can " + canaligned);
        return (double) (bothaligned / canaligned);
    }

    public double getMeanS() {
        // number of sequence for which the shift is defined
        double number = 0;
        //sum of absolut shifts
        double shift = 0;
        //going through target       
        for (int i = 0; i < target.length(); i++) {
            //reseting help boolean to keep track of defined shifts
            //helpboolean gets modified when getting the shift
            helpboolean = true;
            //shift at for characters i
            int h = getshift(i);
            //absolut value for shift
            if (h < 0) {
                h = -h;
            }
            //adding to shift sum
            shift = shift + h;
            //increasing
            if (helpboolean) {
                number++;
            }
        }
        if (number == 0) {
            System.out.println("no shifts defined");
            return -1;
        }
        System.out.println("Number of defined shifts " + number);
        return (double) shift / number;
    }

    public double getInvers() {
        // number of sequence for which the shift is defined
        double number = 0;
        //sum of absolut shifts
        double shift = 0;
        //going through template       
        for (int i = 0; i < template.length(); i++) {
            //reseting help boolean to keep track of defined shifts
            //helpboolean gets modified when getting the shift
            helpboolean = true;
            //shift at for characters i
            int h = getinvers(i);
            //absolut value for shift
            if (h < 0) {
                h = -h;
            }
            //adding to shift sum
            shift = shift + h;
            //increasing
            if (helpboolean) {
                number++;
            }
        }
        if (number == 0) {
            System.out.println("no shifts defined");
            return -1;
        }
        System.out.println("Number of defined shifts " + number);
        return (double) shift / number;
    }

    public int getshift(int i) {

        System.out.println("Shift a postion " + i);

        //temp parameter
        int p = i;
        //position in target from reference
        int a = -1;
        for (int j = 0; j <= p; j++) {
            if (reftar.charAt(j) == '_') {
                p++;
            }
            a++;
        }
        //reset temp parameter
        p = i;
        //position in target from candidate
        int b = -1;
        for (int j = 0; j <= p; j++) {
            if (cantar.charAt(j) == '_') {
                p++;
            }
            b++;
        }
        //checking if shift is undefined
        System.out.println("a" + a);
        System.out.println("b" + b);
        if (reftemp.charAt(a) == '_' || cantemp.charAt(b) == '_') {
            //modifying helpboolean so 'number' of defined shifts wont be increased
            helpboolean = false;
            //returning 0 wont influence MSE
            System.out.println("undefined");
            return 0;
        }
        System.out.println("Position in reference " + a);
        System.out.println("Position in candidate " + b);
        //getting template position from candidate
        int c = 0;
        for (int j = 0; j < a; j++) {
            if (reftemp.charAt(j) != '_') {
                c++;
            }
        }
        //getting template position from reference
        int d = 0;
        for (int j = 0; j < b; j++) {
            if (cantemp.charAt(j) != '_') {
                d++;
            }
        }
        System.out.println("partner in template " + c);
        System.out.println("partner in template " + d);
        int out = d - c;
        System.out.println("shift " + out + "\n");
        return out;

    }

    public int getinvers(int i) {

        System.out.println("InversShift a postion " + i);

        //temp parameter
        int p = i;
        //position in template from reference
        int a = -1;
        for (int j = 0; j <= p; j++) {
            if (reftemp.charAt(j) == '_') {
                p++;
            }
            a++;
        }
        //reset temp parameter
        p = i;
        //position in template from candidate
        int b = -1;
        for (int j = 0; j <= p; j++) {
            if (cantemp.charAt(j) == '_') {
                p++;
            }
            b++;
        }
        //checking if shift is undefined
        System.out.println("a" + a);
        System.out.println("b" + b);
        if (reftar.charAt(a) == '_' || cantar.charAt(b) == '_') {
            //modifying helpboolean so 'number' of defined shifts wont be increased
            helpboolean = false;
            //returning 0 wont influence inverseMSE
            System.out.println("undefined");
            return 0;
        }
        System.out.println("Position in reference " + a);
        System.out.println("Position in candidate " + b);
        //getting target position from candidate
        int c = 0;
        for (int j = 0; j < a; j++) {
            if (reftar.charAt(j) != '_') {
                c++;
            }
        }
        //getting target position from reference
        int d = 0;
        for (int j = 0; j < b; j++) {
            if (cantar.charAt(j) != '_') {
                d++;
            }
        }
        System.out.println("partner in target " + c);
        System.out.println("partner in target " + d);
        int out = d - c;
        System.out.println("shift " + out + "\n");
        return out;

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
