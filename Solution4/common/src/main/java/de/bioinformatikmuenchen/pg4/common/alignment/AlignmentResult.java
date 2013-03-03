/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.common.alignment;


import de.bioinformatikmuenchen.pg4.common.util.CollectionUtil;
import java.util.Collection;



/**
 *
 * @author koehleru
 */
public class AlignmentResult {

    private double score;
    private String querySequenceId;
    private String targetSequenceId;
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
        return targetSequenceId;
    }

    public void setTargetSequenceId(String seq2Id) {
        this.targetSequenceId = seq2Id;
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
