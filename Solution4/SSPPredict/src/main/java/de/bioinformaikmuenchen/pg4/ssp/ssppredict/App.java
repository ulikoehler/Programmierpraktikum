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
 * java -jar predict.jar
 *  --probabilities
 *  --model <model file>                    trained model file
 *  --format <txt|html>                     txt: output of sequence,secondary structure as plaintext
 *                                           html: html output --probabilities include the probabilities in the output (0-9, coloring in html)
 *  --seq <fasta file>                      input file in Fasta format (GOR I-IV)
 *  --maf <multiple-alignment-folder>     path to directory with .aln files (GOR V)
 * 
 * For GORV, a multiple alignment must be given by the parameter --maf. If called with
 * no (or wrong) parameters, your command line tools must output these help texts.
 * The output of predict.jar must be written to stdout in all cases.
 */
package de.bioinformaikmuenchen.pg4.ssp.ssppredict;

import de.bioinformatikmuenchen.pg4.common.AminoAcid;

/**
 * SSP Prediction
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
    }
}
