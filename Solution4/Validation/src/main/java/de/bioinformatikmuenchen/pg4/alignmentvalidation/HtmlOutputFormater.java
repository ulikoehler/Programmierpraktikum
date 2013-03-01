/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.alignmentvalidation;

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

        return builder.toString();
    }

    public String format(Summary input) {
        StringBuilder builder = new StringBuilder();

        return builder.toString();
    }

    public void print(Detailed input, File detailed) {
        try {
            FileWriter out = new FileWriter(detailed);
            out.write(format(input));

        } catch (IOException e) {
            System.err.println(e);
        }
    }

    public void print(Summary input, File summary) {
        try {
            FileWriter out = new FileWriter(summary);
            out.write(format(input));

        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
