/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformaikmuenchen.pg4.common.alignment;

import de.bioinformaikmuenchen.pg4.common.util.CollectionUtil;
import java.util.ArrayList;
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
        alignments = new ArrayList<SequencePairAlignment>(100);
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
