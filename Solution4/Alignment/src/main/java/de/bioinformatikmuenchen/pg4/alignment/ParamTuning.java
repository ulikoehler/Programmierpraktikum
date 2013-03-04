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
    
    public double[] runParamTest(Sequence seq1, Sequence seq2, IDistanceMatrix matrix, int[] gapOpenRange, int[] gapExtendRange, AlignmentAlgorithm algo, AlignmentMode mode) throws IOException{
        IGapCost gapCost = new AffineGapCost(-12, -1);
        //check for illegal usage
        assert !(algo == AlignmentAlgorithm.NEEDLEMAN_WUNSCH && mode == AlignmentMode.LOCAL) : "Use AlignmentAlgorithm.SMITH_WATERMAN for local Needleman-Wunsch Alignment";
        assert !(algo == AlignmentAlgorithm.SMITH_WATERMAN) || mode == AlignmentMode.LOCAL : "S/W => local";
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
            for (int i = gapOpenRange[0]; i <= gapOpenRange[1]; i++) {
                ConstantGapCost gap = new ConstantGapCost(i);
                SmithWaterman instance = new SmithWaterman(mode, algo, matrix, gap);
                AlignmentResult result = instance.align(seq1, seq2);
                list.add(result.getScore());
            }
        }
        else if(algo == AlignmentAlgorithm.GOTOH){
            for (int i = gapOpenRange[0]; i <= gapOpenRange[1]; i++) {
                for (int j = gapExtendRange[0]; j <= gapExtendRange[1]; j++) {
                    AffineGapCost gap = new AffineGapCost(i, j);
                    Gotoh instance = new Gotoh(mode, algo, matrix, gap);
                    AlignmentResult result = instance.align(seq1, seq2);
                    list.add(result.getScore());
                    System.out.println(result.getFirstAlignment().queryAlignment+"\n"+result.getFirstAlignment().targetAlignment+"\n");
                }
            }
        }
        double[] d = new double[list.size()];
        for (int i = 0; i < list.size(); i++) {
            d[i] = list.get(i);
        }
        return d;
    }
    
    public static void main(String[] args) throws IOException {
        ParamTuning test = new ParamTuning();
        Sequence seq1 = new Sequence("AGCATCGTTTTTGTGTGAGTCATCG");
        Sequence seq2 = new Sequence("ATTTAGCCGATCGATGATCGA");
        int[] gapOpenRange = new int[]{-6,0};//{-15,-13};
        int[] gapExtendRange = new int[]{-3,-1};
        IDistanceMatrix matrix = QUASARDistanceMatrixFactory.factorize(new InputStreamReader(ParamTuning.class.getResourceAsStream("/matrices/dayhoff.mat")));
        double[] results = test.runParamTest(seq1, seq2, matrix, gapOpenRange, gapExtendRange, AlignmentAlgorithm.NEEDLEMAN_WUNSCH, AlignmentMode.FREESHIFT);
        System.out.println("result length: "+results.length);
//        for (int i = gapOpenRange[0], j=0; i <= gapOpenRange[1]; i++) {
//            for (int k = gapExtendRange[0]; k <= gapExtendRange[1]; k++,j++) {
//                System.out.println(i+", "+k+": "+results[j]);
//            }
//        }
        for (int i = gapOpenRange[0], j=0; i <= gapOpenRange[1]; i++, j++) {
            System.out.println(i+": "+results[j]);
        }
    }
}