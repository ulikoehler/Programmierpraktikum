package de.bioinformatikmuenchen.pg4.alignment;

import java.io.File;
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
                .addOption("d", "dpmatrices", true, "Output dynamic programming matrices to directory")
                .addOption("p", "pairs", true, "Path to pairs file")
                .addOption("s", "seqlib", true, "seqlibfile")
                .addOption("n", "matrixname", true, "matrixname")
                .addOption("m", "mode", true, "mode")
                .addOption("u", "nw", true, "Use Needleman-Wunsch")
                .addOption("c", "check", true, "Calculate checkscores")
                .addOption("f", "format", true, "format");
        //Parse the opts
        final CommandLineParser cmdLinePosixParser = new PosixParser();
        CommandLine commandLine = null;
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
        //
        //Check if the options are valid
        //
        if (opts.hasOption("help")){
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("alignment.jar", opts);
        }
        //check --dpmatrices
        File dpMatrixDir = null;
        if (commandLine.hasOption("dpmatrices")){
            dpMatrixDir = new File(commandLine.getOptionValue("dpmatrices"));
            if(dpMatrixDir.exists() && !dpMatrixDir.isDirectory()) {
                System.err.println("Error: --dpmatrices directory exists and is not a directory");
                System.exit(1);
            }
            if(!dpMatrixDir.exists()) {
                dpMatrixDir.mkdirs();
            }
        }
        //check --pairs
        File pairsFile = null;
        if (commandLine.hasOption("pairs")){
            dpMatrixDir = new File(commandLine.getOptionValue("pairs"));
            if(!dpMatrixDir.exists() || !dpMatrixDir.isDirectory()) {
                System.err.println("Error: --pairs directory is not a file!");
                System.exit(1);
            }
            if(!dpMatrixDir.exists()) {
                dpMatrixDir.mkdirs();
            }
        }
    }
}
