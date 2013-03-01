/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.alignmentvalidation;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

/**
 *
 * @author schoeffel
 */
public class test {
    
    
    public static void main (String[] args){
        
        double input = Math.round(1212.1264141);
        double out = (int) input;
        System.out.println(out);
        
    }
        public static double roundThreeDecimals(double d) {
        DecimalFormat numberFormat = new DecimalFormat();
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setDecimalSeparator('.');
        numberFormat.setGroupingUsed(false);
        numberFormat.setMinimumFractionDigits(6);
        numberFormat.setMaximumFractionDigits(2);
        numberFormat.setDecimalSeparatorAlwaysShown(false);
        numberFormat.setDecimalFormatSymbols(dfs);
        return Double.valueOf(numberFormat.format(d));
    }
}
