package de.bioinformatikmuenchen.pg4.alignment;

import de.bioinformatikmuenchen.pg4.alignment.gap.ConstantGapCost;
import de.bioinformatikmuenchen.pg4.alignment.gap.IGapCost;
import de.bioinformatikmuenchen.pg4.alignment.io.IAlignmentOutputFormatter;
import de.bioinformatikmuenchen.pg4.common.Sequence;
import de.bioinformatikmuenchen.pg4.common.alignment.AlignmentResult;
import de.bioinformatikmuenchen.pg4.common.alignment.SequencePairAlignment;
import de.bioinformatikmuenchen.pg4.common.distance.IDistanceMatrix;
import java.util.Collections;

/**
 *
 * @author tobias
 */
public class Gotoh extends AlignmentProcessor {

    private double[][] matrixA;
    private double[][] matrixIn;
    private double[][] matrixDel;
    //Matrices that save whether any given field got its value from the specified direction
//    private boolean[][] leftTopArrows;
//    private boolean[][] leftArrows;
//    private boolean[][] topArrows;
    private int xSize = -1;
    private int ySize = -1;
    private String seq1;
    private String seq2;
    private boolean freeshift;
    private boolean local;

    public Gotoh(AlignmentMode mode, AlignmentAlgorithm algorithm, IDistanceMatrix distanceMatrix, IGapCost gapCost) {
        super(mode, algorithm, distanceMatrix, gapCost);
        assert gapCost instanceof ConstantGapCost;
        //AlignmentResult result = new AlignmentResult();
    }

    public Gotoh(AlignmentMode mode, AlignmentAlgorithm algorithm, IDistanceMatrix distanceMatrix, IGapCost gapCost, IAlignmentOutputFormatter outputFormatter) {
        super(mode, algorithm, distanceMatrix, gapCost, outputFormatter);
        assert gapCost instanceof ConstantGapCost : "Classic Needleman Wunsch can't use affine gap cost";
        assert algorithm == AlignmentAlgorithm.NEEDLEMAN_WUNSCH;
    }

    @Override
    public AlignmentResult align(Sequence seq1, Sequence seq2) {
        assert seq1 != null && seq2 != null;
        assert seq1.getSequence().length() > 0;
        assert seq2.getSequence().length() > 0;
        this.seq1 = seq1.getSequence();
        this.seq2 = seq2.getSequence();
        initMatrix(seq1.getSequence().length(), seq2.getSequence().length());
        fillMatrix(seq1.getSequence(), seq2.getSequence());
        AlignmentResult result = new AlignmentResult();
        //Calculate the alignment and add it to the result
        result.setAlignments(Collections.singletonList(backTracking()));
//        result.setScore(matrix[xSize - 1][ySize - 1]);
//        result.setQuerySequenceId(seq1.getId());
//        result.setTargetSequenceId(seq2.getId());
        return result;
    }

    public void initMatrix(int xSize, int ySize) {
        matrixDel[0][0] = Double.NaN;
        matrixIn[0][0] = Double.NaN;
        for (int i = 1; i < xSize; i++) {
            matrixA[i][0] = gapCost.getGapCost(i);
            matrixIn[i][0] = Double.NEGATIVE_INFINITY;
            matrixDel[i][0] = Double.NaN;
        }
        for (int i = 1; i < ySize; i++) {
            matrixA[0][i] = gapCost.getGapCost(i);
            matrixIn[0][i] = Double.NaN;
            matrixDel[0][i] = Double.NEGATIVE_INFINITY;
        }
    }

    public void fillMatrix(String seq1, String seq2) {
    }

    public SequencePairAlignment backTracking() {
        return null;
    }

    @Override
    public double[][] getMatrix() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean setFreeshift(boolean freeshift) {
        this.freeshift = freeshift;
        return this.freeshift;
    }

    public boolean setLocal(boolean local) {
        this.local = local;
        return this.local;
    }
}
