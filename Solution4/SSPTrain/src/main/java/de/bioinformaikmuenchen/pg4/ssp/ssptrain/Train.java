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
 * java -jar train.jar --db <dssp-file> path to training file --method
 * <gor1|gor3|gor4> method --model <model-file> model file output
 *
 * For GORV, a multiple alignment must be given by the parameter --maf. If
 * called with no (or wrong) parameters, your command line tools must output
 * these help texts. The output of predict.jar must be written to stdout in all
 * cases.
 */
package de.bioinformaikmuenchen.pg4.ssp.ssptrain;

import java.io.File;
import java.io.IOException;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.PosixParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * SSP Train
 *
 */
public class Train {

    /**
     * enum for possible training methods
     */
    public enum TrainingMethods {

        GOR1, GOR3, GOR4
    };

    /**
     *
     * @param args
     */
    public static void main(String[] args) {

        //
        // get params from args to start a new training campain
        //

        // get command options
        final Options opts = new Options();
        opts.addOption("d", "db", true, "dssp path to train method (database)")
                .addOption("m", "method", true, "method to train <gor1|gor3|gor4>")
                .addOption("l", "model", true, "output file");
        // 
        // Parse these options with PosixParser
        // 
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

        // 
        // check for valid options
        // 

        // first the database
        String db = "";
        File dbFile = new File("");
        if (commandLine.hasOption("db")) {
            // option db has been declared => check if valid
            db = commandLine.getOptionValue("db");
            // check if db is valid path
            dbFile = new File(db);
            try {
                dbFile.getCanonicalPath();
            } catch (IOException e) {
                System.err.println("ERROR INVALID PATH @OPTION DB!");
                printUsageAndQuit();
            }
            // file exists
            if (!dbFile.exists()) {
                System.err.println("ERROR DB FILE DOESN'T EXISTS!");
                printUsageAndQuit();
            }
            // file readable
            if (!dbFile.canRead()) {
                System.err.println("ERROR CAN'T READ FROM DB FILE!");
                printUsageAndQuit();
            }
        } else {
            System.err.println("ERROR DB FILE NOT SPEC!");
            printUsageAndQuit();
        }

        String model = "";
        File modelFile = new File("");
        // second check if method is valid
        if (commandLine.hasOption("model")) {
            // option model has been declared => check if valid
            model = commandLine.getOptionValue("model");
            // check if db is valid path
            modelFile = new File(model);
            try {
                modelFile.getCanonicalPath();
            } catch (IOException e) {
                System.err.println("ERROR INVALID PATH @OPTION MODEL!");
                printUsageAndQuit();
            }
            // check if accessable
            if (modelFile.exists() && !modelFile.canWrite()) {
                System.err.println("ERROR CAN'T WRITE TO MODEL FILE!");
                printUsageAndQuit();
            }
        } else {
            System.err.println("ERROR MODEL FILE NOT SPEC!");
            printUsageAndQuit();
        }

        String method = "";
        TrainingMethods tmethod = TrainingMethods.GOR1;
        // third check if model is valid
        if (commandLine.hasOption("method")) {
            // option method has been declared => check if valid
            method = commandLine.getOptionValue("method");
            // method to TrainingMethods
            if ("GOR1".equalsIgnoreCase(method)) {
                tmethod = TrainingMethods.GOR1;
            } else if ("GOR3".equalsIgnoreCase(method)) {
                tmethod = TrainingMethods.GOR3;
            } else if ("GOR4".equalsIgnoreCase(method)) {
                tmethod = TrainingMethods.GOR4;
            } else {
                System.err.println("ERROR MISSING METHOD SPEC!");
                printUsageAndQuit();
            }
        } else {
            System.err.println("ERROR METHOD NOT SPEC!");
            printUsageAndQuit();
        }

        // create a new trainer and let him do his job
        Trainer myTrainer;
        if (tmethod == Train.TrainingMethods.GOR1) {
            myTrainer = new TrainerGor1();
        } else if (tmethod == Train.TrainingMethods.GOR3) {
            myTrainer = new TrainerGor3();
        } else {
            myTrainer = new TrainerGor4();
        }

        // give the trainer the data he requires
        myTrainer.inputFile = dbFile;
        myTrainer.outputFile = modelFile;

        // let the Trainer train
        myTrainer.trainMatrix();
        myTrainer.printMatrixToBash();

    }

    public static void printUsageAndQuit() {
        System.err.println(
                "Usage: java -jar train.jar --db <dssp-file> --method <gor1|gor3|gor4> --model <model file>\n"
                + " Options:\n"
                + "  --db <dssp-file>\tpath to training file\n"
                + "  --method <gor1|gor3|gor4>\tmethod\n"
                + "  --model < model file>\tmodel file output\n");
        System.exit(1);
    }
}
