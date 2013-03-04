/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.alignmentvalidation;

import java.io.File;
import java.io.IOException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

/**
 *
 * @author schoeffel
 */
public class ValidateAli {

    private ValidateOutputFormat format = null;

    public ValidateAli(String[] args) {
        //available options
        final Options opts = new Options();
        opts.addOption("a", "alignment", true, "output file of align");
        opts.addOption("r", "reference", true, "file containing paths to HOMSTRAD files as reference");
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
            formatter.printHelp("validateAli.jar", opts);
            System.out.println("--help supplied, exiting...");
            System.exit(1);
        }
        //check --alignment
        File alignmentfile = null;
        if (commandLine.hasOption("alignment")) {
            alignmentfile = new File(commandLine.getOptionValue("alignment"));
            if (!alignmentfile.exists() || alignmentfile.isDirectory()) {
                System.err.println("Error: --alignment argument is not a file or does not exist!");
                System.exit(1);
            }
        } else {
            System.err.println("--alignment is mandatory");
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("validateAli.jar", opts);
            System.exit(1);
        }
        //check --reference
        File reference = null;
        if (commandLine.hasOption("reference")) {
            reference = new File(commandLine.getOptionValue("reference"));
            if (!reference.exists() || reference.isDirectory()) {
                System.err.println("Error: --reference argument is not a file or does not exist!");
                System.exit(1);
            }
        } else {
            System.err.println("--reference is mandatory");
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("validateAli.jar", opts);
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
                formatter.printHelp("validateAli.jar", opts);
                System.exit(1);
            }
        } else {
            System.err.println("--format is mandatory");
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("validateAli.jar", opts);
            System.exit(1);
        }
        //check --summary
        File summary = null;
        if (commandLine.hasOption("summaryfile")) {
            summary = new File(commandLine.getOptionValue("summaryfile"));
        } else {
            System.err.println("Error: --summaryfile is mandatory");
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("validateAli.jar", opts);
            System.exit(1);
        }
        //check --detailed    
        File detailed = null;
        if (commandLine.hasOption("detailedfile")) {
            detailed = new File(commandLine.getOptionValue("detailedfile"));
        } else {
            System.err.println("Error: --detailedfile is mandatory");
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("validateAli.jar", opts);
            System.exit(1);
        }
        //main part
        System.out.println("Finished parsing input ");
        FactorizeValidation factory = new FactorizeValidation(alignmentfile, reference);
        System.out.println("Finished factorising input ");
        ValidationOutputFormater out = new ValidationOutputFormater(factory.getDetail(),factory.getSummary(),format,detailed,summary);
        System.out.println("Finished formating output ");
        out.print();

    }

    public static void main(String[] args) throws IOException {
        ValidateAli ValiAli = new ValidateAli(args);
    }
}
