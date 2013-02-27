package de.bioinformatikmuenchen.pg4.alignment;

import de.bioinformatikmuenchen.pg4.alignment.gap.ConstantGapCost;
import de.bioinformatikmuenchen.pg4.alignment.gap.IGapCost;
import de.bioinformatikmuenchen.pg4.common.Sequence;
import de.bioinformatikmuenchen.pg4.common.alignment.AlignmentResult;
import de.bioinformatikmuenchen.pg4.common.alignment.SequencePairAlignment;
import de.bioinformatikmuenchen.pg4.common.distance.IDistanceMatrix;
import java.util.Collections;

/**
 *
 * @author tobias
 */
public class Gotoh extends AlignmentProcessor{

    private double[][] matrix;
    //Matrices that save whether any given field got its value from the specified direction
    private boolean[][] leftTopArrows;
    private boolean[][] leftArrows;
    private boolean[][] topArrows;
    private int xSize = -1;
    private int ySize = -1;
    private String seq1;
    private String seq2;
    
    public Gotoh(AlignmentMode mode, AlignmentAlgorithm algorithm, IDistanceMatrix distanceMatrix, IGapCost gapCost){
        super(mode, algorithm, distanceMatrix, gapCost);
        assert gapCost instanceof ConstantGapCost;
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
        result.setScore(matrix[xSize - 1][ySize - 1]);
        result.setQuerySequenceId(seq1.getId());
        result.setTargetSequenceId(seq2.getId());
        return result;
    }
    
    public void initMatrix(int xSize, int ySize){
        
    }
    
    public void fillMatrix(String seq1, String seq2){
        
    }
    
    public SequencePairAlignment backTracking(){
        return null;
    }
}
