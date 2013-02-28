package de.bioinformatikmuenchen.pg4.alignment;

import de.bioinformatikmuenchen.pg4.alignment.gap.ConstantGapCost;
import de.bioinformatikmuenchen.pg4.alignment.gap.IGapCost;
import de.bioinformatikmuenchen.pg4.alignment.io.DPMatrixExporter;
import de.bioinformatikmuenchen.pg4.alignment.io.IAlignmentOutputFormatter;
import de.bioinformatikmuenchen.pg4.alignment.io.IDPMatrixExporter;
import de.bioinformatikmuenchen.pg4.common.Sequence;
import de.bioinformatikmuenchen.pg4.common.alignment.AlignmentResult;
import de.bioinformatikmuenchen.pg4.common.alignment.SequencePairAlignment;
import de.bioinformatikmuenchen.pg4.common.distance.IDistanceMatrix;
import java.io.IOException;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tobias
 */
public class Gotoh extends AlignmentProcessor {

    private double[][] matrixA;
    private double[][] matrixIn;
    private double[][] matrixDel;
    private double score;
    private int xSize = -1;
    private int ySize = -1;
    private String querySequence;
    private String targetSequence;
    private String querySequenceId;
    private String targetSequenceId;
    private boolean freeshift = false;
    private boolean local = false;

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
        result.setAlignments(Collections.singletonList(backTrackingGlobal()));
//        result.setScore(matrix[xSize - 1][ySize - 1]);
//        result.setQuerySequenceId(seq1.getId());
//        result.setTargetSequenceId(seq2.getId());
        return result;
    }

    public void initMatrix(int xSize, int ySize) {
        matrixDel[0][0] = Double.NaN;
        matrixIn[0][0] = Double.NaN;
        if(!(this.local || this.freeshift)){// " == if(global)"
            for (int i = 1; i < xSize; i++) {
            matrixA[i][0] = gapCost.getGapCost(i);
        }
            for (int i = 1; i < ySize; i++) {
                matrixA[0][i] = gapCost.getGapCost(i);
            }
        }
        for (int i = 1; i < xSize; i++) {
            matrixIn[i][0] = Double.NEGATIVE_INFINITY;
            matrixDel[i][0] = Double.NaN;
        }
        for (int i = 1  ; i < ySize; i++) {
            matrixIn[0][i] = Double.NaN;
            matrixDel[0][i] = Double.NEGATIVE_INFINITY;
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
        }
    }
    
    public SequencePairAlignment backTrackingLocal(){
        StringBuffer queryLine = new StringBuffer(); StringBuffer targetLine = new StringBuffer();
        //find the cell with the greatest entry:
        int x = -1; int y = -1; double maxCell = Double.NEGATIVE_INFINITY;
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                if(matrixA[i][j] >= maxCell){
                    x = i; y = j;
                    maxCell = matrixA[i][j];
                }
            }
        }
        assert (x>=0 && y>=0 && maxCell > Double.NEGATIVE_INFINITY);
        while(matrixA[x][y] != 0){
            char A = querySequence.charAt(x - 1);
            char B = targetSequence.charAt(y - 1);
            if(matrixA[x][y] == matrixA[x-1][y-1] + distanceMatrix.distance(A, B)){
                queryLine.append(A);
                targetLine.append(B);
                x--; y--;
            }
            else if(matrixA[x][y] == matrixIn[x][y]){
                int shift = findK(matrixA[x][y], x, y, true);
                for (int i = x; i >= (x-shift); i--) {
                    queryLine.append(querySequence.charAt(i-1));
                    targetLine.append('-');
                }
                x -= shift;
            }
            else if(matrixA[x][y] == matrixDel[x][y]){
                int shift = findK(matrixA[x][y], x, y, false);
                for (int i = y; i >= (y-shift); i--) {
                    queryLine.append(querySequence.charAt(i));
                    targetLine.append('-');
                }
                y -= shift;
            }
            else{
                System.out.println("No possibility found to move on (indicates a sure failure)");
            }
        }
        return new SequencePairAlignment(queryLine.reverse().toString(), targetLine.reverse().toString());
    }

    public SequencePairAlignment backTrackingGlobal() {
        int x = xSize; int y = ySize;
        StringBuffer queryLine = new StringBuffer(); StringBuffer targetLine = new StringBuffer();
        while(x!=0 || y!=0){//while the rim of the matrix or its left upper corner is not reached
            char A = querySequence.charAt(x - 1);
            char B = targetSequence.charAt(y - 1);
            if(matrixA[x][y] == matrixA[x-1][y-1] + distanceMatrix.distance(A, B)){
                queryLine.append(A);
                targetLine.append(B);
                x--; y--;
            }
            else if(matrixA[x][y] == matrixIn[x][y]){
                int shift = findK(matrixA[x][y], x, y, true);
                for (int i = x; i >= (x-shift); i--) {
                    queryLine.append(querySequence.charAt(i-1));
                    targetLine.append('-');
                }
                x -= shift;
            }
            else if(matrixA[x][y] == matrixDel[x][y]){
                int shift = findK(matrixA[x][y], x, y, false);
                for (int i = y; i >= (y-shift); i--) {
                    queryLine.append(querySequence.charAt(i));
                    targetLine.append('-');
                }
                y -= shift;
            }
            else{
                System.out.println("No possibility found to move on (indicates a sure failure)");
            }
        }
        return new SequencePairAlignment(queryLine.reverse().toString(), targetLine.reverse().toString());
    }
    
    private int findK(double entry, int x, int y, boolean insertion){
        int shift = 0;
        if(insertion){
            while(x != 0){
                if((matrixA[x-1][y] + gapCost.getGapCost(shift+1)) == entry){
                    shift++;
                    break;
                }
                else{
                    shift++; x--;
                }
            }
        }
        else{//Deletion
            while(x != 0){
                if((matrixA[x][y-1] + gapCost.getGapCost(shift+1)) == entry){
                    shift++; break;
                }
                else{
                    shift++; y--;
                }
            }
        }
        assert shift > 0;
        return shift;
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
    public void writeMatrices(IDPMatrixExporter exporter) {
        DPMatrixExporter.DPMatrixInfo info = new DPMatrixExporter.DPMatrixInfo();
        //Set sequences
        info.query = querySequence;
        info.target = targetSequence;
        //Set IDs
        info.queryId = querySequenceId;
        info.targetId = targetSequenceId;
        info.matrix = this.matrixA;
        info.matrixPostfix = "Gotoh alignment matrix";
        try {
            exporter.write(info);
        } catch (IOException ex) {
            Logger.getLogger(Gotoh.class.getName()).log(Level.SEVERE, null, ex);
        }
        info.matrix = this.matrixIn;
        info.matrixPostfix = "Gotoh insertions matrix";
        try {
            exporter.write(info);
        } catch (IOException ex) {
            Logger.getLogger(Gotoh.class.getName()).log(Level.SEVERE, null, ex);
        }
        info.matrix = this.matrixDel;
        info.matrixPostfix = "Gotoh deletions matrix";
        try {
            exporter.write(info);
        } catch (IOException ex) {
            Logger.getLogger(Gotoh.class.getName()).log(Level.SEVERE, null, ex);
        }
        info.score = score;
    }
}
