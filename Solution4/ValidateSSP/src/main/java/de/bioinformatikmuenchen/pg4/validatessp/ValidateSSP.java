package de.bioinformatikmuenchen.pg4.validatessp;

import java.io.File;
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
public class ValidateSSP {
    
    private ValidateOutputFormat format = null;

    public ValidateSSP(String[] args) {
        //available options
        final Options opts = new Options();
        opts.addOption("p", "predictions", true, "prediction file");
        opts.addOption("r", "dssp-file", true, "DSSP file");
        opts.addOption("f", "format", true, "output format txt : output as plaintext html: html output");
        opts.addOption("s", "summaryfile", true, "file to write summary outputs to");
        opts.addOption("d", "detailedfile", true, "file to write detailed information to");
        opts.addOption("h", "help", false, "print help");
        //parse Options
        final CommandLineParser cmdLinePosixParser = new PosixParser();
        CommandLine commandLine = null;
        try {
            commandLine = cmdLinePosixParser.parse(opts, args);
        } catch (ParseException parseException) {    // checked exception  
            System.err.println(
                    "Encountered exception while parsing using PosixParser:\n"
                    + parseException.getMessage());
            System.exit(1);
        }
        //check --help
        if (commandLine.hasOption("help")) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("validateGor.jar", opts);
            System.out.println("--help supplied, exiting...");
            System.exit(1);
        }
        //check --predictions
        File predictionsfile = null;
        if (commandLine.hasOption("predictions")) {
            predictionsfile = new File(commandLine.getOptionValue("predictions"));
            if (!predictionsfile.exists() || predictionsfile.isDirectory()) {
                System.err.println("Error: --predictions argument is not a file or does not exist!");
                System.exit(1);
            }
        } else {
            System.err.println("--predictions is mandatory");
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("validateGor.jar", opts);
            System.exit(1);
        }
        //check --reference
        File reference = null;
        if (commandLine.hasOption("dssp-file")) {
            reference = new File(commandLine.getOptionValue("dssp-file"));
            if (!reference.exists() || reference.isDirectory()) {
                System.err.println("Error: --dssp-file argument is not a file or does not exist!");
                System.exit(1);
            }
        } else {
            System.err.println("--dssp-file is mandatory");
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("validateGor.jar", opts);
            System.exit(1);
        }
        //check --format
        if (commandLine.hasOption("format")) {
            String outputFormatString = commandLine.getOptionValue("format").toLowerCase();
            if (outputFormatString != null) {
                if (outputFormatString.equalsIgnoreCase("txt")) {
                    format = ValidateOutputFormat.TXT;
                } else if (outputFormatString.equalsIgnoreCase("html")) {
                    format = ValidateOutputFormat.HTML;
                } else {
                    System.err.println("Error: --format argument " + outputFormatString + " is invalid!");
                    System.exit(1);
                }
            } else {
                System.err.println("Error: --format is mandatory");
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("validateGor.jar", opts);
                System.exit(1);
            }
        } else {
            System.err.println("--format is mandatory");
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("validateGor.jar", opts);
            System.exit(1);
        }
        //check --summary
        File summary = null;
        if (commandLine.hasOption("summaryfile")) {
            summary = new File(commandLine.getOptionValue("summaryfile"));
            if (!summary.exists() || summary.isDirectory()) {
                System.err.println("Error: --summaryfile argument is not a file!");
                System.exit(1);
            }
        } else {
            System.err.println("Error: --summaryfile is mandatory");
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("validateGor.jar", opts);
            System.exit(1);
        }
        //check --detailed    
        File detailed = null;
        if (commandLine.hasOption("detailedfile")) {
            detailed = new File(commandLine.getOptionValue("detailedfile"));
            if (!detailed.exists() || detailed.isDirectory()) {
                System.err.println("Error: --detailedfile argument is not a file!");
                System.exit(1);
            }
        } else {
            System.err.println("Error: --detailedfile is mandatory");
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("validateGor.jar", opts);
            System.exit(1);
        }

        FactorizeValidation factory = new FactorizeValidation(predictionsfile, reference);
        
    }

    public static void main(String[] args) {
        ValidateSSP ValiSSP = new ValidateSSP(args);
    }
}
