package de.bioinformatikmuenchen.pg4.alignment;

import java.io.OutputStream;
import java.io.PrintWriter;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

/**
 * Hello world!
 *
 */
public class AlignmentMain {

    public static void main(String[] args) {
        //Which opts are available
        final Options opts = new Options();
        opts.addOption("g", "go", true, "Gap open")
                .addOption("h", "help", false, "Print help")
                .addOption("e", "ge", true, "gapextend")
                .addOption("d", "dpmatrices", true, "dpmatrices")
                .addOption("p", "pair", true, "pairfile")
                .addOption("s", "seqlib", true, "seqlibfile")
                .addOption("n", "matrixname", true, "matrixname")
                .addOption("m", "mode", true, "mode")
                .addOption("u", "nw", true, "Use Needleman-Wunsch")
                .addOption("c", "check", true, "Calculate checkscores")
                .addOption("f", "format", true, "format");
        //Parse the opts
        final CommandLineParser cmdLinePosixParser = new PosixParser();
        CommandLine commandLine;
        try {
            commandLine = cmdLinePosixParser.parse(opts, args);
            if (commandLine.hasOption("display")) {
                System.out.println("You want a display!");
            }
        } catch (ParseException parseException) {    // checked exception  
            System.err.println(
                    "Encountered exception while parsing using PosixParser:\n"
                    + parseException.getMessage());
        }
        //Check if the options are valid
        
    }

    public static void printUsage(
            final String applicationName,
            final Options options,
            final OutputStream out) {
        final PrintWriter writer = new PrintWriter(out);
        final HelpFormatter usageFormatter = new HelpFormatter();
        usageFormatter.printUsage(writer, 80, applicationName, options);
        writer.close();
    }
}
