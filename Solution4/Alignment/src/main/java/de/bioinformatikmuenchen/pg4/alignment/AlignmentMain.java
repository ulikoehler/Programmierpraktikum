package de.bioinformatikmuenchen.pg4.alignment;

import java.io.OutputStream;
import java.io.PrintWriter;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.PosixParser;

/**
 * Hello world!
 *
 */
public class AlignmentMain {

    public static void main(String[] args) {
        //Which opts are available
        final Options gnuOptions = new Options();
        gnuOptions.addOption("p", "print", false, "Option for printing")
                .addOption("g", "gui", false, "HMI option")
                .addOption("n", true, "Number of copies");
        //Parse the opts
        final CommandLineParser cmdLinePosixParser = new PosixParser();
        CommandLine commandLine;
        try {
            commandLine = cmdLinePosixParser.parse(posixOptions, commandLineArguments);
            if (commandLine.hasOption("display")) {
                System.out.println("You want a display!");
            }
        } catch (ParseException parseException) // checked exception  
        {
            System.err.println(
                    "Encountered exception while parsing using PosixParser:\n"
                    + parseException.getMessage());
        }
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
