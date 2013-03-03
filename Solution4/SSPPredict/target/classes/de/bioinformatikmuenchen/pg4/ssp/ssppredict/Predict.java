/**
 * Implement the GOR secondary structure prediction method as discussed in the
 * papers/slides on the homepage. Start with GOR I and advance to GOR III&IV,
 * finally implement GOR V. The secondary structure elements must be predicted
 * in three states H=Helix, E=Sheet and C=Coil. For every sequence position, you
 * should also: print out the probability for the position to be Helix, Sheet,
 * and Coil. The GOR algorithm splits into two parts: - training and prediction
 * Thus implement two binaries, one for each task (see Specifications).
 *
 *
 * Your secondary structure prediction program must be executable from command
 * line. Exact specifications are given in section 5 of this task sheet. For our
 * evaluations, it is necessary for you to precisely follow these
 * specifications!
 *
 *
 * Postprocessing Certain secondary structures do not make sense (e.g. CCCHCCC).
 * You may want to implement a postprocessing to remove such occurences from
 * your predictions. It may be interesting to evaluate GOR with postprocessing
 * against GOR without postprocessing.
 *
 *
 * java -jar predict.jar --probabilities --model <model file> trained model file
 * --format <txt|html> txt: output of sequence,secondary structure as plaintext
 * html: html output --probabilities include the probabilities in the output
 * (0-9, coloring in html) --seq <fasta file> input file in Fasta format (GOR
 * I-IV) --maf <multiple-alignment-folder> path to directory with .aln files
 * (GOR V)
 *
 * For GORV, a multiple alignment must be given by the parameter --maf. If
 * called with no (or wrong) parameters, your command line tools must output
 * these help texts. The output of predict.jar must be written to stdout in all
 * cases.
 */
package de.bioinformatikmuenchen.pg4.ssp.ssppredict;

import java.io.*;
import java.io.IOException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.LinkedList;
import de.bioinformatikmuenchen.pg4.common.util.IO;

/**
 * SSP Prediction
 *
 */
public class Predict {

    public static boolean debug = false;

    public enum simpleGorMethods {

        gor1, gor3, gor4
    }

    public enum gorMethods {

        gor1, gor3, gor4, gor5
    }

    public enum outputMethod {

        txt, html
    }

    public static void main(String[] args) {
        System.gc();

        // get command options
        final Options opts = new Options();
        opts.addOption("m", "model", true, "path to the model file")
                .addOption("s", "seq", true, "path to the fasta file to predict")
                .addOption("a", "maf", true, "path to .aln file")
                .addOption("f", "format", true, "output sequence in html or txt format")
                .addOption("p", "probabilities", false, "include the probabilities in the output (0-9, coloring in html)")
                .addOption("t", "postprocessing", false, "whether to postprocess results")
                .addOption("d", "debug", false, "output debug informations");

        final CommandLineParser cmdLinePosixParser = new PosixParser();
        CommandLine commandLine = null;
        try {
            commandLine = cmdLinePosixParser.parse(opts, args);
        } catch (ParseException parseException) {    // checked exception
            System.err.println(
                    "Encountered exception while parsing using PosixParser:\n"
                    + parseException.getMessage());
            printUsageAndQuit();
        }

        // GOR 1-4

        String model = "";
        File f = null;
        simpleGorMethods method = simpleGorMethods.gor1;
        if (commandLine.hasOption("m")) {
            model = commandLine.getOptionValue("model");
            IO.isExistingReadableFileOrQuit(model, "Can't read file: " + model + "!");
            f = new File(model);
            method = GORPredicter.getGorFromFile(f);
        } else {
            System.err.println("missing model file!");
            printUsageAndQuit();
        }

        if (commandLine.hasOption("s") && commandLine.hasOption("a")) {
            System.err.println("Please spec --seq *or* --maf!");
            printUsageAndQuit();
        }

        String seq = "";
        File g = null;
        if (commandLine.hasOption("s")) {
            seq = commandLine.getOptionValue("s");
            seq = IO.isExistingReadableFileOrQuit(seq, "Can't read file " + seq + "!");
            g = new File(seq);
        } else {
            if (!commandLine.hasOption("a")) {
                System.err.println("@s: Please spec --seq or --maf");
                printUsageAndQuit();
            }
        }

        outputMethod format = outputMethod.txt;
        if (commandLine.hasOption("f")) {
            String s = commandLine.getOptionValue("f");
            if (s.equalsIgnoreCase("txt")) {
                format = outputMethod.txt;
            } else if (s.equalsIgnoreCase("html")) {
                format = outputMethod.html;
            } else {
                System.err.println("Invalid --format argument!");
                printUsageAndQuit();
            }
        }

        // generell

        boolean probabilities = false;
        if (commandLine.hasOption("p")) {
            probabilities = true;
        }

        boolean postprocessing = false;
        if (commandLine.hasOption("t")) {
            postprocessing = true;
        }

        if (commandLine.hasOption("d")) {
            debug = true;
        }

        // GOR 5

        String maf = "";
        File h = null;
        if (commandLine.hasOption("a")) {
            maf = commandLine.getOptionValue("a");
            maf = IO.isExistingReadableFileOrQuit(maf, "Can't read file " + maf + "!");
            h = new File(maf);
            // GOR 5 Alignment
            if (debug) {
                System.out.println("Preprocessing ...");
            }
            Data.secStruct = GORPredicter.getStatesFromFile(f, method);
            Data.trainingWindowSize = GORPredicter.getWindowSizeFromFile(f);
            Data.prevInWindow = Data.trainingWindowSize / 2;
            if (debug) {
                System.out.println("Init gor5 ...");
            }
            GOR5Predicter gor5 = new GOR5Predicter(method);
            if (debug) {
                System.out.println("Parse gor5 model file ...");
            }
            gor5.readModelFile(f);
            if (debug) {
                System.out.println("Predict ...");
            }
            PredictionResult res = gor5.predict(h);
            if (postprocessing) {
                if (debug) {
                    System.out.println("Postprocessing ...");
                }
                res = GORPredicter.postprocess(res);
            }
            if (debug) {
                System.out.println("Format and write results (" + format.name() + "," + ((probabilities) ? "" : " no") + " probabilities) ...");
            }
            if (format.name().equals(outputMethod.html.name())) {
                res.getHTMLRepresentation(probabilities, System.out);
            } else {
                res.getTXTRepresentation(probabilities, System.out);
            }
            if (debug) {
                System.out.println("programdata: ");
                System.out.print("AA: ");
                for (int i = 0; i < Data.aaTable.length; i++) {
                    System.out.print(Data.aaTable[i]);
                }
                System.out.println();
                System.out.print("ST: ");
                for (int i = 0; i < Data.secStruct.length; i++) {
                    System.out.print(Data.secStruct[i]);
                }
                System.out.println();
                System.out.println("window size: " + Data.trainingWindowSize + " - " + Data.prevInWindow);
            }
            // quit
            System.exit(0);
        } else {
            if (!commandLine.hasOption("s")) {
                System.err.println("@a: Please spec --seq or --maf");
                printUsageAndQuit();
            }
        }

        // GOR 1-4
        
        GORPredicter predicter =
                (method.name().equals(simpleGorMethods.gor1.name()))
                ? new GOR1Predicter()
                : (method.name().equals(simpleGorMethods.gor3.name()))
                ? new GOR3Predicter() : new GOR4Predicter();// Gor3, Gor4

        try {
            if (debug) {
                System.out.println("Preprocessing ...");
            }
            Data.secStruct = GORPredicter.getStatesFromFile(f, method);
            Data.trainingWindowSize = GORPredicter.getWindowSizeFromFile(f);
            Data.prevInWindow = Data.trainingWindowSize / 2;
            if (debug) {
                System.out.println("Init ...");
            }
            predicter.init();
            if (debug) {
                System.out.println("Parse ...");
            }
            predicter.readModelFile(f);
            if (debug) {
                System.out.println("Init prediction ...");
            }
            predicter.initPrediction();
            if (debug) {
                System.out.println("Predict ...");
            }
            PredictionResult res = predicter.predictFileSequences(g);
            if (postprocessing) {
                if (debug) {
                    System.out.println("Postprocessing ...");
                }
                res = GORPredicter.postprocess(res);
            }
            if (debug) {
                System.out.println("Format and write results (" + format.name() + "," + ((probabilities) ? "" : " no") + " probabilities) ...");
            }
            if (format.name().equals(outputMethod.html.name())) {
                res.getHTMLRepresentation(probabilities, System.out);
            } else {
                res.getTXTRepresentation(probabilities, System.out);
            }
            if (debug) {
                System.out.println("programdata: ");
                System.out.print("AA: ");
                for (int i = 0; i < Data.aaTable.length; i++) {
                    System.out.print(Data.aaTable[i]);
                }
                System.out.println();
                System.out.print("ST: ");
                for (int i = 0; i < Data.secStruct.length; i++) {
                    System.out.print(Data.secStruct[i]);
                }
                System.out.println();
                System.out.println("window size: " + Data.trainingWindowSize + " - " + Data.prevInWindow);
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
            System.err.println("Error predicting sequence! " + e.toString() + " See stacktrace above!");
            System.exit(1);
        }
    }

    public static void printUsageAndQuit() {
        System.err.println(
                "Usage: java -jar predict.jar [--probabilities][--postprocessing]--model <model file>--format <text|html>{--seq <fasta file>--format <multiple-alignment-folder>}\n"
                + " Options:\n"
                + "  --model <model file> trained model file\n"
                + "  --seq <fasta file>   input file in Fasta format (GOR I-IV)\n"
                + "  --maf <MA path>      path to directory with .aln files (GOR V)\n"
                + "  --format <txt|html>\n"
                + "     txt:  output of sequence, secondary structure as plaintext\n"
                + "     html: html output"
                + "  --probabilities      include the probabilities in the output (0-9, coloring in html)"
                + "  --postprocessing     postprocess prediction");
        System.exit(1);
    }
}
