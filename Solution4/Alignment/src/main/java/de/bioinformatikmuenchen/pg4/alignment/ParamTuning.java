package de.bioinformatikmuenchen.pg4.alignment;

import de.bioinformatikmuenchen.pg4.alignment.gap.AffineGapCost;
import de.bioinformatikmuenchen.pg4.alignment.gap.ConstantGapCost;
import de.bioinformatikmuenchen.pg4.alignment.gap.IGapCost;
import de.bioinformatikmuenchen.pg4.common.Sequence;
import de.bioinformatikmuenchen.pg4.common.alignment.AlignmentResult;
import de.bioinformatikmuenchen.pg4.common.distance.IDistanceMatrix;
import de.bioinformatikmuenchen.pg4.common.distance.QUASARDistanceMatrixFactory;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

/**
 *
 * @author harrert
 */
public class ParamTuning {
    
    public double[] runParamTest(Sequence seq1, Sequence seq2, int[] gapOpenRange, int[] gapExtendRange, AlignmentAlgorithm algo, AlignmentMode mode) throws IOException{
        IDistanceMatrix matrix = QUASARDistanceMatrixFactory.factorize(new InputStreamReader(ParamTuning.class.getResourceAsStream("/matrices/dayhoff.mat")));
        IGapCost gapCost = new AffineGapCost(-12, -1);
        //check for illegal usage
        assert !(algo == AlignmentAlgorithm.NEEDLEMAN_WUNSCH && mode == AlignmentMode.LOCAL) : "Use AlignmentAlgorithm.SMITH_WATERMAN for local Needleman-Wunsch Alignment";
        assert (gapExtendRange.length == 2 && gapOpenRange.length == 2);
        LinkedList<Double> list = new LinkedList<Double>();
        if(algo == AlignmentAlgorithm.NEEDLEMAN_WUNSCH){// -> N/W, S/W
            for (int i = gapOpenRange[0]; i <= gapOpenRange[1]; i++) {
                ConstantGapCost gap = new ConstantGapCost(i);
                NeedlemanWunsch instance = new NeedlemanWunsch(mode, algo, matrix, gap);
                AlignmentResult result = instance.align(seq1, seq2);
                list.add(result.getScore());
            }
        }
        else if(algo == AlignmentAlgorithm.SMITH_WATERMAN){
            for (int i = gapOpenRange[0]; i < gapOpenRange[1]; i++) {
                ConstantGapCost gap = new ConstantGapCost(i);
                SmithWaterman instance = new SmithWaterman(mode, algo, matrix, gap);
                AlignmentResult result = instance.align(seq1, seq2);
                list.add(result.getScore());
            }
        }
        else if(algo == AlignmentAlgorithm.GOTOH){
            for (int i = gapOpenRange[0]; i < gapOpenRange[1]; i++) {
                for (int j = gapExtendRange[0]; j < gapExtendRange[1]; j++) {
                    AffineGapCost gap = new AffineGapCost(i, j);
                    Gotoh instance = new Gotoh(mode, algo, matrix, gap);
                    AlignmentResult result = instance.align(seq1, seq2);
                    list.add(result.getScore());
                }
            }
        }
        double[] d = new double[list.size()];
        for (int i = 0; i < list.size(); i++) {
            d[i] = list.get(i);
        }
        return d;
    }
    
}