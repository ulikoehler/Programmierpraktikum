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
    private String seq1Id;
    private String seq2Id;
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

    public String getSeq1Id() {
        return seq1Id;
    }

    public void setSeq1Id(String seq1Id) {
        this.seq1Id = seq1Id;
    }

    public String getSeq2Id() {
        return seq2Id;
    }

    public void setSeq2Id(String seq2Id) {
        this.seq2Id = seq2Id;
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
