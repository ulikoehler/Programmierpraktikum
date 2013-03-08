/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.btronik.convdir;

import java.io.*;

/**
 *
 * @author quant
 */
public class ConvertDirectory {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        File from = new File(args[0]);System.out.println(from.getAbsolutePath());
        String to = args[1];

        File[] toConvert = from.listFiles();

        for (File t : toConvert) {
            if (!t.isDirectory()) {
                // convert
                BufferedReader w = new BufferedReader(new FileReader(t));
                String line = null;
                StringBuilder out = new StringBuilder("Pos\tMean\tSD\tMin\tMax\tQuan50\tQuan5\tQuan95\n");
                int i = 0;
                while ((line = w.readLine()) != null) {
                    if (line.startsWith(">")) {
                        // convert
                        String[] x = line.split("\\t");
                        String mean    = x[4],
                                sd     = x[3],
                                min    = x[5],
                                max    = x[6],
                                quan50 = x[7],
                                quan5  = x[8],
                                quan95 = x[9];
                        out.append(i)
                                .append("\t")
                                .append(mean)
                                .append("\t")
                                .append(sd)
                                .append("\t")
                                .append(min)
                                .append("\t")
                                .append(max)
                                .append("\t")
                                .append(quan50)
                                .append("\t")
                                .append(quan5)
                                .append("\t")
                                .append(quan95)
                                .append("\n");
                        i++;
                    }
                }
                w.close();
                File u = new File(to + "/" + t.getName());
                FileWriter o = new FileWriter(u);
                o.append(out);
                o.close();
            }
        }
    }
}
