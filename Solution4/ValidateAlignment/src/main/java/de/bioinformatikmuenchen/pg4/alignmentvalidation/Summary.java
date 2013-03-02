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
        for (Double val : coll) {
            sum += val;
        }
        return round(sum / coll.size());
    }

    public double getSensiMean() {
        return getMean(sensi);
    }

    public double getSpeciMean() {
        return getMean(speci);
    }

    public double getCoverMean() {
        return getMean(cover);
    }

    public double getMeansMean() {
        return getMean(means);
    }

    public double getInverMean() {
        return getMean(inver);
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
        double number = Math.round(quan * list.size());
        return list.get((int) number);
    }

    public double getSensiQuantil(double quantil) {
        Collections.sort(sensi);
        return getQuantil(sensi, quantil);
    }

    public double getSpeciQuantil(double quantil) {
        Collections.sort(speci);
        return getQuantil(speci, quantil);
    }

    public double getCoverQuantil(double quantil) {
        Collections.sort(cover);
        return getQuantil(cover, quantil);
    }

    public double getMeansQuantil(double quantil) {
        Collections.sort(means);
        return getQuantil(means, quantil);
    }

    public double getInverQuantil(double quantil) {
        Collections.sort(inver);
        return getQuantil(inver, quantil);
    }

    public static double round(double d) {
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
