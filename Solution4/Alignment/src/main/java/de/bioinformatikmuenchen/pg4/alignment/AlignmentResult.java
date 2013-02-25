/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package de.bioinformatikmuenchen.pg4.alignment;

/**
 *
 * @author koehleru
 */
public class AlignmentResult {
    double score;

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
    
    
}
