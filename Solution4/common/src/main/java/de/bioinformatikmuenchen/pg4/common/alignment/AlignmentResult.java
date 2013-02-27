/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.common.alignment;

<<<<<<< HEAD:Solution4/common/src/main/java/de/bioinformaikmuenchen/pg4/common/alignment/AlignmentResult.java
import de.bioinformaikmuenchen.pg4.common.util.CollectionUtil;
=======
import de.bioinformatikmuenchen.pg4.common.util.CollectionUtil;
import java.util.ArrayList;
>>>>>>> a4cbc51f9f9c4a9e716599b4cb86ece990442636:Solution4/common/src/main/java/de/bioinformatikmuenchen/pg4/common/alignment/AlignmentResult.java
import java.util.Collection;


/**
 *
 * @author koehleru
 */
public class AlignmentResult {

    private double score;
    private String querySequenceId;
    private String targetSequence;
    private Collection<SequencePairAlignment> alignments;

    public AlignmentResult() {
        //alignments = new Collection<SequencePairAlignment>(100);
    }

    public AlignmentResult(double score) {
        this.score = score;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
    public String getQuerySequenceId() {
        return querySequenceId;
    }

    public void setQuerySequenceId(String seq1Id) {
        this.querySequenceId = seq1Id;
    }

    public String getTargetSequenceId() {
        return targetSequence;
    }

    public void setTargetSequenceId(String seq2Id) {
        this.targetSequence = seq2Id;
    }

    public AlignmentResult(double score, Collection<SequencePairAlignment> alignments) {
        this.score = score;
        this.alignments = alignments;
    }

    public Collection<SequencePairAlignment> getAlignments() {
        return alignments;
    }

    public void setAlignments(Collection<SequencePairAlignment> alignments) {
        this.alignments = alignments;
    }
    
    public SequencePairAlignment getFirstAlignment() {
        return CollectionUtil.getFirst(alignments);
    }
}
