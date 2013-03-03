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

        builder.append("</body>");
        builder.append("</html>");
        return builder.toString();
    }

    public String format(Summary input) {
        StringBuilder builder = new StringBuilder();
        builder.append("<html>");
        builder.append("<head><title> AlignmentValidationSummary </title></head>");
        builder.append("<body>");

        builder.append("</body>");
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
