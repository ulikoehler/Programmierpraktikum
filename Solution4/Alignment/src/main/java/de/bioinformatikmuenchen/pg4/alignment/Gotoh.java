package de.bioinformatikmuenchen.pg4.alignment;

import de.bioinformatikmuenchen.pg4.alignment.gap.ConstantGapCost;
import de.bioinformatikmuenchen.pg4.alignment.gap.IGapCost;
import de.bioinformatikmuenchen.pg4.alignment.io.DPMatrixExporter;
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
    private double score;
    //Matrices that save whether any given field got its value from the specified direction
    private boolean[][] leftTopArrows;
    private boolean[][] leftArrows;
    private boolean[][] topArrows;
    private int xSize = -1;
    private int ySize = -1;
    private String querySequence;
    private String targetSequence;
    private String querySequenceId;
    private String targetSequenceId;
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
        this.querySequence = seq1.getSequence();
        this.targetSequence = seq2.getSequence();
        this.querySequenceId = seq1.getId();
        this.targetSequenceId = seq2.getId();
        this.xSize = querySequence.length();
        this.ySize = targetSequence.length();
        initMatrix(seq1.getSequence().length(), seq2.getSequence().length());
        fillMatrix(seq1.getSequence(), seq2.getSequence());
        this.score = matrixA[xSize-1][ySize-1];
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
        //init the three boolean matrices, which "store" the alignment arrows
        for (int x = 0; x < xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                leftArrows[x][y] = false;
                leftTopArrows[x][y] = false;
                topArrows[x][y] = false;
            }
        }
    }

    public void fillMatrix(String seq1, String seq2) {
        int inGaps = 0;
        int delGaps = 0;
        for (int x = 1; x < xSize; x++) {
            for (int y = 1; y < ySize; y++) {
                matrixIn[x][y] = Math.max(matrixA[x][y - 1] + gapCost.getGapCost(1), matrixIn[x][y - 1] + gapCost.getGapExtensionPenalty(0, 1));
                matrixDel[x][y] = Math.max(matrixA[x - 1][y] + gapCost.getGapCost(1), matrixDel[x - 1][y] + gapCost.getGapExtensionPenalty(0, 1));
                matrixA[x][y] = Math.max(Math.max(matrixIn[x][y], matrixDel[x][y]), matrixA[x - 1][y - 1] + distanceMatrix.distance(seq1.charAt(x - 1), seq2.charAt(y - 1)));
            }
            //######!!!!!!!set boolean matrices like in NW
        }
    }

    public SequencePairAlignment backTracking() {
        return null;
    }

    public boolean setFreeshift(boolean freeshift) {
        this.freeshift = freeshift;
        return this.freeshift;
    }

    public boolean setLocal(boolean local) {
        this.local = local;
        return this.local;
    }

    @Override
    public void writeMatrices(DPMatrixExporter exporter) {
        DPMatrixExporter.DPMatrixInfo info = new DPMatrixExporter.DPMatrixInfo();
        //Set sequences
        info.query = querySequence;
        info.target = targetSequence;
        //Set IDs
        info.queryId = querySequenceId;
        info.targetId = targetSequenceId;
        info.matrixA = this.matrixA;
        info.matrixAPostfix = "Gotoh alignment matrix";
        info.matrixIn = this.matrixIn;
        info.matrixInPostfix = "Gotoh insertions matrix";
        info.matrixDel = this.matrixDel;
        info.matrixDelPostfix = "Gotoh deletions matrix";
        info.leftArrows = leftArrows;
        info.topArrows = topArrows;
        info.topLeftArrows = leftTopArrows;
        info.score = score;
    }
}
