/**
 * Implement the GOR secondary structure prediction method as discussed in the
 * papers/slides on the homepage. Start with GOR I and advance to GOR III&IV, finally
 * implement GOR V. The secondary structure elements must be predicted in three states
 * H=Helix, E=Sheet and C=Coil. For every sequence position, you should also:
 * print out the probability for the position to be Helix, Sheet, and Coil.
 * The GOR algorithm splits into two parts:
 *  - training and prediction
 * Thus implement two binaries, one for each task (see Specifications).
 * 
 * 
 * Your secondary structure prediction program must be executable from command
 * line. Exact specifications are given in section 5 of this task sheet. For our
 * evaluations, it is necessary for you to precisely follow these specifications!
 * 
 * 
 * Postprocessing
 * Certain secondary structures do not make sense (e.g. CCCHCCC). You may want to
 * implement a postprocessing to remove such occurences from your predictions. It
 * may be interesting to evaluate GOR with postprocessing against GOR without
 * postprocessing.
 * 
 * 
 * java -jar train.jar
 *  --db <dssp-file>                        path to training file
 *  --method <gor1|gor3|gor4>              method
 *  --model <model-file>                    model file output
 * 
 * For GORV, a multiple alignment must be given by the parameter --maf. If called with
 * no (or wrong) parameters, your command line tools must output these help texts.
 * The output of predict.jar must be written to stdout in all cases.
 */
package de.bioinformaikmuenchen.pg4.ssp.ssptrain;

import de.bioinformatikmuenchen.pg4.common.AminoAcid;
import java.io.File;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;

/**
 * SSP Train
 *
 */
public class Train 
{
    // {{{ default settings
    
    /**
     * the default train file
     */
    public static final String defaultTrainFilePath = "test.db";
    
    /**
     * default trainings method
     */
    public static final TrainingMethods defaultTrainingMethod = TrainingMethods.GOR1;
    
    /**
     * the default output file
     */
    public static final String defaultOutputFilePath = "model.txt";
    
    // }}}
    // {{{ param argument settings
    
    /**
     * database for training
     */
    public File trainFile;
    
    /**
     * enum for possible training methods
     */
    public enum TrainingMethods {
        GOR1, GOR3, GOR4
    };
    
    /**
     * Currently selected Training method
     */
    public TrainingMethods actTrainingMethod = TrainingMethods.GOR1;
    
    /**
     * result of training output files
     */
    public File trainOutputFile;
    
    // }}}
    // {{{ functions of the trainer methods
    
    /**
     * Init a new trainer with the specific parameters
     * 
     * @param inputFile  The dssp file for reading test data
     * @param outputFile The file to output all the train test data
     * @param method     The method to train
     */
    public Train(File inputFile, File outputFile, TrainingMethods method) {
        this.trainFile = inputFile;
        this.trainOutputFile = outputFile;
        this.actTrainingMethod = method;
    }
    
    /**
     * start a new training campain with specified options
     */
    public void startTraining() {
        
    }
    
    // }}}
    // {{{ main
    
    /**
     * 
     * @param args 
     */
    public static void main( String[] args )
    {
        //
        // get params from args to start a new training campain
        //
        
        // get command options
        final Options opts = new Options();
        opts.addOption("d", "db",     true,  "dssp path to train method (database)")
            .addOption("m", "method", true,  "method to train <gor1|gor3|gor4>")
            .addOption("l", "model",  true,  "output file");
        // 
        // Parse these options
        // 
        final CommandLineParser cmdLinePosixParser = new PosixParser();
        CommandLine commandLine = null;
        try {
            commandLine = cmdLinePosixParser.parse(opts, args);
        } catch (ParseException parseException) {    // checked exception  
            System.err.println(
                    "Encountered exception while parsing using PosixParser:\n"
                    + parseException.getMessage()
            );
        }
        
        // 
        // check for valid options
        // 
        
        // first the database
        if(opts.hasOption("db")) {
            // check if database file exists
            File db = new File(commandLine.getOptionValue("db"));
            if(db.exists()) { /* do something */ }
        } else {
            
        }
        
        // second check if model is valid
        if(opts.hasOption("method")) {
            
        } else {
            
        }
        
        // third check if method is valid
        if(opts.hasOption("model")) {
            
        } else {
            
        }
        
        
        // do a new trainer object and train
        Train trainer = new Train();
        trainer.paramReader(args);
    }
    
    // }}}
    // {{{
    
    public static String getNewPath(String errorMessage, String ) {
    
    }
    
    // }}}
}
