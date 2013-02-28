/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import de.bioinformatikmuenchen.pg4.alignment.AlignmentAlgorithm;
import de.bioinformatikmuenchen.pg4.alignment.AlignmentMode;
import de.bioinformatikmuenchen.pg4.alignment.NeedlemanWunsch;
import de.bioinformatikmuenchen.pg4.alignment.gap.ConstantGapCost;
import de.bioinformatikmuenchen.pg4.alignment.gap.IGapCost;
import de.bioinformatikmuenchen.pg4.alignment.recursive.RecursiveNWAlignmentProcessor;
import de.bioinformatikmuenchen.pg4.common.Sequence;
import de.bioinformatikmuenchen.pg4.common.distance.IDistanceMatrix;
import de.bioinformatikmuenchen.pg4.common.distance.QUASARDistanceMatrixFactory;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author koehleru
 */
public class Benchmark {

    public static void main(String[] args) throws IOException {
        String longS1 = "EEKRNRAITARNKGEEKRNRAITARNKGEEKRNRAITARNKGEEKRNRAITARNKGEEKRNRAITARNKG";
        String longS2 = "GERRRSQLDRTAEEGERRRSQLDRTAEEGERRRSQLDRTAEEGERRRSQLDRTAEEGERRRSQLDRTAEE";
        int size = Integer.parseInt(args[1]);
        Sequence seq1Obj = new Sequence(longS1.substring(0,size));
        Sequence seq2Obj = new Sequence(longS1.substring(0,size));
        IDistanceMatrix matrix = QUASARDistanceMatrixFactory.factorize(new InputStreamReader(Benchmark.class.getResourceAsStream("/matrices/dayhoff.mat")));
        IGapCost gapCost = new ConstantGapCost(1.0);
        //Comment or uncomment depending on desired usage to prevent overhead
//        RecursiveNWAlignmentProcessor instance = new RecursiveNWAlignmentProcessor(AlignmentMode.GLOBAL, AlignmentAlgorithm.NEEDLEMAN_WUNSCH, matrix, gapCost);
        NeedlemanWunsch instance = new NeedlemanWunsch(AlignmentMode.GLOBAL, AlignmentAlgorithm.NEEDLEMAN_WUNSCH, matrix, gapCost);
        System.out.println("Finished");
    }
}
