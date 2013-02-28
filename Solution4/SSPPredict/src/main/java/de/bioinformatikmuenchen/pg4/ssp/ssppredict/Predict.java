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

import java.io.File;
import java.io.IOException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * SSP Prediction
 *
 */
public class Predict {

    public enum gorMethods {

        gor1, gor3, gor4, gor5
    }

    public enum outputMethod {

        txt, html
    }

    public static void main(String[] args) {
        // get command options
        final Options opts = new Options();
        opts.addOption("m", "model", true, "path to the model file")
                .addOption("s", "seq", true, "path to the fasta file to predict")
                .addOption("a", "maf", true, "path to .aln file")
                .addOption("f", "format", true, "output sequence in html or txt format")
                .addOption("p", "probabilities", false, "include the probabilities in the output (0-9, coloring in html)")
                .addOption("t", "postprocessing", false, "whether to postprocess results");

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

        String model = "";



        String seq = "";



        String maf = "";


        outputMethod format = outputMethod.txt;


        boolean probabilities = false;



        boolean postprocessing = false;
        
        

        System.out.println(model + "-" + seq + "-" + maf + "-" + format + "-" + probabilities + "-" + postprocessing);
    }

    public static void printUsageAndQuit() {
        System.err.println(
                "Usage: java -jar predict.jar [--probabilities][--postprocessing]--model <model file> --format <text|html>{--seq <fasta file> --format <multiple-alignment-folder>}\n"
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