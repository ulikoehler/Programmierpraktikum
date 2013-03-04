/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.alignmentvalidation;

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

    private ArrayList<Double> sensi = new ArrayList();
    private ArrayList<Double> speci = new ArrayList();
    private ArrayList<Double> cover = new ArrayList();
    private ArrayList<Double> means = new ArrayList();
    private ArrayList<Double> inver = new ArrayList();

    public void addSensi(double input) {
        sensi.add(input);
    }

    public void addSpeci(double input) {
        speci.add(input);
    }

    public void addCover(double input) {
        cover.add(input);
    }

    public void addMeans(double input) {
        means.add(input);
    }

    public void addInver(double input) {
        inver.add(input);
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

    public double getSensiMean() {
        return round(getMean(sensi));
    }

    public double getSpeciMean() {
        return round(getMean(speci));
    }

    public double getCoverMean() {
        return round(getMean(cover));
    }

    public double getMeansMean() {
        return round(getMean(means));
    }

    public double getInverMean() {
        return round(getMean(inver));
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

    public double getSensiMin() {
        return getMin(sensi);
    }

    public double getSpeciMin() {
        return getMin(speci);
    }

    public double getCoverMin() {
        return getMin(cover);
    }

    public double getMeansMin() {
        return getMin(means);
    }

    public double getInverMin() {
        return getMin(inver);
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

    public double getSensiMax() {
        return getMax(sensi);
    }

    public double getSpeciMax() {
        return getMax(speci);
    }

    public double getCoverMax() {
        return getMax(cover);
    }

    public double getMeansMax() {
        return getMax(means);
    }

    public double getInverMax() {
        return getMax(inver);
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
            if (!(list.get(i).isNaN())){
                j++;
            }
        }
        double number = (quan * j);
        return list.get((int) number);
    }

    public double getSensiQuantil(double quantil) {
        Collections.sort(sensi);
        return round(getQuantil(sensi, quantil));
    }

    public double getSpeciQuantil(double quantil) {
        Collections.sort(speci);
        return round(getQuantil(speci, quantil));
    }

    public double getCoverQuantil(double quantil) {
        Collections.sort(cover);
        return round(getQuantil(cover, quantil));
    }

    public double getMeansQuantil(double quantil) {
        Collections.sort(means);
        return round(getQuantil(means, quantil));
    }

    public double getInverQuantil(double quantil) {
        Collections.sort(inver);
        return round(getQuantil(inver, quantil));
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

    public double getSensiStanDevi() {
        return getStandardDeviation(sensi, this.getSensiMean());
    }

    public double getSpeciStanDevi() {
        return getStandardDeviation(speci, this.getSpeciMean());
    }

    public double getCoverStanDevi() {
        return getStandardDeviation(cover, this.getCoverMean());
    }

    public double getMeansStanDevi() {
        return getStandardDeviation(means, this.getMeansMean());
    }

    public double getInverStanDevi() {
        return getStandardDeviation(inver, this.getInverMean());
    }

    public static double round(double d) {
        Double p = d;
        if (!(p.isNaN())|| !(p.isInfinite())) {
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
