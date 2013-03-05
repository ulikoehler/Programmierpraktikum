/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.validatessp;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

/**
 *
 * @author schoeffel
 */
public class Summary {

    private ArrayList<Double> Q3 = new ArrayList();
    private ArrayList<Double> QH = new ArrayList();
    private ArrayList<Double> QE = new ArrayList();
    private ArrayList<Double> QC = new ArrayList();
    private ArrayList<Double> SOV = new ArrayList();
    private ArrayList<Double> SOVH = new ArrayList();
    private ArrayList<Double> SOVE = new ArrayList();
    private ArrayList<Double> SOVC = new ArrayList();

    public void addQ3(double input) {
        Q3.add(input);
    }

    public void addQH(double input) {
        QH.add(input);
    }

    public void addQE(double input) {
        QE.add(input);
    }

    public void addQC(double input) {
        QC.add(input);
    }

    public void addSOV(double input) {
        SOV.add(input);
    }

    public void addSOVH(double input) {
        SOVH.add(input);
    }

    public void addSOVE(double input) {
        SOVE.add(input);
    }

    public void addSOVC(double input) {
        SOVC.add(input);
    }

    private static double getMean(Collection<Double> coll) {
        double sum = 0.0;
        int NaN = 0;
        for (Double val : coll) {
            if (!(val.isNaN())) {
                sum += val;
            } else {
                NaN++;
            }
        }
        return sum / (coll.size() - NaN);
    }

    public double getQ3Mean() {
        if (Q3.isEmpty()) {
            return Double.NaN;
        }
        return round(getMean(Q3));
    }

    public double getQHMean() {
        if (QH.isEmpty()) {
            return Double.NaN;
        }
        return round(getMean(QH));
    }

    public double getQEMean() {
        if (QE.isEmpty()) {
            return Double.NaN;
        }
        return round(getMean(QE));
    }

    public double getQCMean() {
        if (QC.isEmpty()) {
            return Double.NaN;
        }
        return round(getMean(QC));
    }

    public double getSOVMean() {
        if (SOV.isEmpty()) {
            return Double.NaN;
        }
        return round(getMean(SOV));
    }

    public double getSOVHMean() {
        if (SOVH.isEmpty()) {
            return Double.NaN;
        }
        return round(getMean(SOVH));
    }

    public double getSOVEMean() {
        if (SOVE.isEmpty()) {
            return Double.NaN;
        }
        return round(getMean(SOVE));
    }

    public double getSOVCMean() {
        if (SOVC.isEmpty()) {
            return Double.NaN;
        }
        return round(getMean(SOVC));
    }

    private static double getMin(Collection<Double> coll) {
        double min = Double.POSITIVE_INFINITY;
        for (Double val : coll) {
            if (val < min) {
                min = val;
            }
        }
        return round(min);
    }

    public double getQ3Min() {
        if (Q3.isEmpty()) {
            return Double.NaN;
        }
        return (getMin(Q3));
    }

    public double getQHMin() {
        if (QH.isEmpty()) {
            return Double.NaN;
        }
        return (getMin(QH));
    }

    public double getQEMin() {
        if (QE.isEmpty()) {
            return Double.NaN;
        }
        return (getMin(QE));
    }

    public double getQCMin() {
        if (QC.isEmpty()) {
            return Double.NaN;
        }
        return (getMin(QC));
    }

    public double getSOVMin() {
        if (SOV.isEmpty()) {
            return Double.NaN;
        }
        return (getMin(SOV));
    }

    public double getSOVHMin() {
        if (SOVH.isEmpty()) {
            return Double.NaN;
        }
        return (getMin(SOVH));
    }

    public double getSOVEMin() {
        if (SOVE.isEmpty()) {
            return Double.NaN;
        }
        return (getMin(SOVE));
    }

    public double getSOVCMin() {
        if (SOVC.isEmpty()) {
            return Double.NaN;
        }
        return (getMin(SOVC));
    }

    private static double getMax(Collection<Double> coll) {
        double max = 0;
        for (Double val : coll) {
            if (val > max) {
                max = val;
            }
        }
        return round(max);
    }

    public double getQ3Max() {
        if (Q3.isEmpty()) {
            return Double.NaN;
        }
        return (getMax(Q3));
    }

    public double getQHMax() {
        if (QH.isEmpty()) {
            return Double.NaN;
        }
        return (getMax(QH));
    }

    public double getQEMax() {
        if (QE.isEmpty()) {
            return Double.NaN;
        }
        return (getMax(QE));
    }

    public double getQCMax() {
        if (QC.isEmpty()) {
            return Double.NaN;
        }
        return (getMax(QC));
    }

    public double getSOVMax() {
        if (SOV.isEmpty()) {
            return Double.NaN;
        }
        return (getMax(SOV));
    }

    public double getSOVHMax() {
        if (SOVH.isEmpty()) {
            return Double.NaN;
        }
        return (getMax(SOVH));
    }

    public double getSOVEMax() {
        if (SOVE.isEmpty()) {
            return Double.NaN;
        }
        return (getMax(SOVE));
    }

    public double getSOVCMax() {
        if (SOVC.isEmpty()) {
            return Double.NaN;
        }
        return (getMax(SOVC));
    }

    private static double getQuantil(ArrayList<Double> list, double quantil) {
        double quan = quantil;
        if (quantil > 1) {
            quan = 1;
        }
        if (quantil < 0) {
            quan = 0;
        }
        int j = 0;
        for (int i = 0; i < list.size(); i++) {
            if (!(list.get(i).isNaN())) {
                j++;
            }
        }
        double number = (quan * j);
        return list.get((int) number);
    }

    public double getQ3Quantil(double quantil) {
        if (Q3.isEmpty()) {
            return Double.NaN;
        }
        Collections.sort(Q3);
        return round(getQuantil(Q3, quantil));
    }

    public double getQHQuantil(double quantil) {
        if (QH.isEmpty()) {
            return Double.NaN;
        }
        Collections.sort(QH);
        return round(getQuantil(QH, quantil));
    }

    public double getQEQuantil(double quantil) {
        if (QE.isEmpty()) {
            return Double.NaN;
        }
        Collections.sort(QE);
        return round(getQuantil(QE, quantil));
    }

    public double getQCQuantil(double quantil) {
        if (QC.isEmpty()) {
            return Double.NaN;
        }
        Collections.sort(QC);
        return round(getQuantil(QC, quantil));
    }

    public double getSOVQuantil(double quantil) {
        if (SOV.isEmpty()) {
            return Double.NaN;
        }
        Collections.sort(SOV);
        return round(getQuantil(SOV, quantil));
    }

    public double getSOVHQuantil(double quantil) {
        if (SOVH.isEmpty()) {
            return Double.NaN;
        }
        Collections.sort(SOVH);
        return round(getQuantil(SOVH, quantil));
    }

    public double getSOVEQuantil(double quantil) {
        if (SOVE.isEmpty()) {
            return Double.NaN;
        }
        Collections.sort(SOVE);
        return round(getQuantil(SOVE, quantil));
    }

    public double getSOVCQuantil(double quantil) {
        if (SOVC.isEmpty()) {
            return Double.NaN;
        }
        Collections.sort(SOVC);
        return round(getQuantil(SOVC, quantil));
    }

    private static double getStandardDeviation(Collection<Double> coll, double mean) {
        double number = 0;
        int NaN = 0;
        for (Double val : coll) {
            if (!(val.isNaN())) {
                number += (mean - val) * (mean - val);
            } else {
                NaN++;
            }
        }
        return round(Math.sqrt(number / (coll.size() - NaN)));
    }

    public double getQ3StanDevi() {
        if (Q3.isEmpty()) {
            return Double.NaN;
        }
        return getStandardDeviation(Q3, this.getQ3Mean());
    }

    public double getQHStanDevi() {
        if (QH.isEmpty()) {
            return Double.NaN;
        }
        return getStandardDeviation(QH, this.getQHMean());
    }

    public double getQEStanDevi() {
        if (QE.isEmpty()) {
            return Double.NaN;
        }
        return getStandardDeviation(QE, this.getQEMean());
    }

    public double getQCStanDevi() {
        if (QC.isEmpty()) {
            return Double.NaN;
        }
        return getStandardDeviation(QC, this.getQCMean());
    }

    public double getSOVStanDevi() {
        if (SOV.isEmpty()) {
            return Double.NaN;
        }
        return getStandardDeviation(SOV, this.getSOVMean());
    }

    public double getSOVHStanDevi() {
        if (SOVH.isEmpty()) {
            return Double.NaN;
        }
        return getStandardDeviation(SOVH, this.getSOVHMean());
    }

    public double getSOVEStanDevi() {
        if (SOVE.isEmpty()) {
            return Double.NaN;
        }
        return getStandardDeviation(SOVE, this.getSOVEMean());
    }

    public double getSOVCStanDevi() {
        if (SOVC.isEmpty()) {
            return Double.NaN;
        }
        return getStandardDeviation(SOVC, this.getSOVCMean());
    }

    private static double round(double d) {
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
}
