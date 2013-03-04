/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.validatessp;

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
        builder.append("<head><title> AlignmentValidationSummary </title></head>");
        builder.append("<body>");

        for (int i = 0; i < input.results.size(); i++) {
            builder.append(input.results.get(i).att1).append("<br>");
            builder.append("AS ").append(input.results.get(i).att2).append("<br>");
            builder.append("PS ").append(input.results.get(i).att3).append("<br>");
            builder.append("SS ").append(input.results.get(i).att4).append("<br>");
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
        builder.append("<body><pre>");

        builder.append("Summary:<br>");
        builder.append("Validation Value\tSD\tMean\tMin\tMax\tQuan50\tQuan5\tQuan95<br><br>");
        builder.append("> Q3\t\t\t").append(input.getQ3StanDevi()).append("\t").append(input.getQ3Mean()).append("\t").append(input.getQ3Min()).append("\t")
                .append(input.getQ3Max()).append("\t").append(input.getQ3Quantil(0.5)).append("\t")
                .append(input.getQ3Quantil(0.05)).append("\t").append(input.getQ3Quantil(0.95)).append("<br><br>");
        builder.append("> SOV\t\t\t").append(input.getQ3StanDevi()).append("\t").append(input.getQ3Mean()).append("\t").append(input.getQ3Min()).append("\t")
                .append(input.getQ3Max()).append("\t").append(input.getQ3Quantil(0.5)).append("\t")
                .append(input.getQ3Quantil(0.05)).append("\t").append(input.getQ3Quantil(0.95)).append("<br><br>");
        builder.append("> QH\t\t\t").append(input.getQ3StanDevi()).append("\t").append(input.getQ3Mean()).append("\t").append(input.getQ3Min()).append("\t")
                .append(input.getQ3Max()).append("\t").append(input.getQ3Quantil(0.5)).append("\t")
                .append(input.getQ3Quantil(0.05)).append("\t").append(input.getQ3Quantil(0.95)).append("<br><br>");
        builder.append("> QE\t\t\t").append(input.getQ3StanDevi()).append("\t").append(input.getQ3Mean()).append("\t").append(input.getQ3Min()).append("\t")
                .append(input.getQ3Max()).append("\t").append(input.getQ3Quantil(0.5)).append("\t")
                .append(input.getQ3Quantil(0.05)).append("\t").append(input.getQ3Quantil(0.95)).append("<br><br>");
        builder.append("> QC\t\t\t").append(input.getQ3StanDevi()).append("\t").append(input.getQ3Mean()).append("\t").append(input.getQ3Min()).append("\t")
                .append(input.getQ3Max()).append("\t").append(input.getQ3Quantil(0.5)).append("\t")
                .append(input.getQ3Quantil(0.05)).append("\t").append(input.getQ3Quantil(0.95)).append("<br><br>");
        builder.append("> SOVH\t\t\t").append(input.getQ3StanDevi()).append("\t").append(input.getQ3Mean()).append("\t").append(input.getQ3Min()).append("\t")
                .append(input.getQ3Max()).append("\t").append(input.getQ3Quantil(0.5)).append("\t")
                .append(input.getQ3Quantil(0.05)).append("\t").append(input.getQ3Quantil(0.95)).append("<br><br>");
        builder.append("> SOVE\t\t\t").append(input.getQ3StanDevi()).append("\t").append(input.getQ3Mean()).append("\t").append(input.getQ3Min()).append("\t")
                .append(input.getQ3Max()).append("\t").append(input.getQ3Quantil(0.5)).append("\t")
                .append(input.getQ3Quantil(0.05)).append("\t").append(input.getQ3Quantil(0.95)).append("<br><br>");
        builder.append("> SOVC\t\t\t").append(input.getQ3StanDevi()).append("\t").append(input.getQ3Mean()).append("\t").append(input.getQ3Min()).append("\t")
                .append(input.getQ3Max()).append("\t").append(input.getQ3Quantil(0.5)).append("\t")
                .append(input.getQ3Quantil(0.05)).append("\t").append(input.getQ3Quantil(0.95)).append("<br><br>");

        builder.append("</pre></body>");
        builder.append("</html>");
        return builder.toString();
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

    public void print(Detailed input, File detailfile) {
        try {
            FileWriter out = new FileWriter(detailfile);
            BufferedWriter output = new BufferedWriter(out);
            output.write(format(input));
            output.close();

        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
