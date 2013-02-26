/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformaikmuenchen.pg4.common.alignment;

/**
 *
 * @author koehleru
 */
public class AlignmentResult {

    private double score;
    private SequencePairAlignment alignment;

    public AlignmentResult() {
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

    public SequencePairAlignment getAlignment() {
        return alignment;
    }

    public void setAlignment(SequencePairAlignment alignment) {
        this.alignment = alignment;
    }
}
