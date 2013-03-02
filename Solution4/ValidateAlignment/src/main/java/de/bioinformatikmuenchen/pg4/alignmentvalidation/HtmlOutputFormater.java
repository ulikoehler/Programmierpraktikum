/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.alignmentvalidation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author schoeffel
 */
public class HtmlOutputFormater {

    public String format(Detailed input) {
        StringBuilder builder = new StringBuilder();
        builder.append("<html>");
        builder.append("<head><title> AlignmentValidation </title></head>");
        builder.append("<body>");
        for (int i = 0; i < input.results.size(); i++) {
            builder.append(input.results.get(i).att1).append("<br>");
            builder.append(input.results.get(i).att1.substring(1, 8)).append(": ");
            builder.append(input.results.get(i).att2).append("<br>");
            builder.append(input.results.get(i).att1.substring(9, 16)).append(": ");
            builder.append(input.results.get(i).att3).append("<br>");
            builder.append(input.results.get(i).att1.substring(1, 8)).append(": ");
            builder.append(input.results.get(i).att4).append("<br>");
            builder.append(input.results.get(i).att1.substring(9, 16)).append(": ");
            builder.append(input.results.get(i).att5).append("<br>");
            builder.append("<br>");
        }
        builder.append("</body>");
        builder.append("</html>");
        return builder.toString();
    }

    public String format(Summary input) {
        StringBuilder builder = new StringBuilder();
                builder.append("<html>");
        builder.append("<head><title> AlignmentValidationSummary </title></head>");
        builder.append("<body>");
        
        builder.append("<h1>Summary:</h1>");
        builder.append("Validation Value\tMean\tMin\tMax\tQuantil50\tQuantil5\tQuantil95<br><br>");
        builder.append(">Sensitivity    \t").append(input.getSensiMean()).append("\t").append(input.getSensiMin()).append("\t")
                .append(input.getSensiMax()).append("\t").append(input.getSensiQuantil(0.5)).append("\t")
                .append(input.getSensiQuantil(0.05)).append("\t").append(input.getSensiQuantil(0.95)).append("<br><br>");
        builder.append(">Specitivity    \t").append(input.getSpeciMean()).append("\t").append(input.getSpeciMin()).append("\t")
                .append(input.getSpeciMax()).append("\t").append(input.getSpeciQuantil(0.5)).append("\t")
                .append(input.getSpeciQuantil(0.05)).append("\t").append(input.getSpeciQuantil(0.95)).append("<br><br>");
        builder.append(">Coverage       \t").append(input.getCoverMean()).append("\t").append(input.getCoverMin()).append("\t")
                .append(input.getCoverMax()).append("\t").append(input.getCoverQuantil(0.5)).append("\t")
                .append(input.getCoverQuantil(0.05)).append("\t").append(input.getCoverQuantil(0.95)).append("<br><br>");
        builder.append(">MeanShiftError \t").append(input.getMeansMean()).append("\t").append(input.getMeansMin()).append("\t")
                .append(input.getMeansMax()).append("\t").append(input.getMeansQuantil(0.5)).append("\t")
                .append(input.getMeansQuantil(0.05)).append("\t").append(input.getMeansQuantil(0.95)).append("<br><br>");
        builder.append(">InverseMSE     \t").append(input.getInverMean()).append("\t").append(input.getInverMin()).append("\t")
                .append(input.getInverMax()).append("\t").append(input.getInverQuantil(0.5)).append("\t")
                .append(input.getInverQuantil(0.05)).append("\t").append(input.getInverQuantil(0.95)).append("<br><br>");

        builder.append("</body>");
        builder.append("</html>");
        return builder.toString();

    }

    public void print(Detailed input, File detailed) {
        try {
            FileWriter out = new FileWriter(detailed);
            BufferedWriter output = new BufferedWriter(out);
            output.write(format(input));
            output.close();

        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public void print(Summary input, File summary) {
        try {
            FileWriter out = new FileWriter(summary);
            BufferedWriter output = new BufferedWriter(out);
            output.write(format(input));
            output.close();

        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
